/**
 * 
 */
package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dto.ProcessInstanceStartDTO;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.exception.ActivitiErrorException;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;
import br.com.vivo.configurationutils.IConfiguration;

/**
 * Retorna formulário de para start de uma instancia
 * @author Jean Bacan
 * @since 18/05/2017
 *
 */
@Named("getProcessStartFormBusinessOperation")
public class GetProcessStartFormBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String, ProcessInstanceStartDTO> {

	private static final Logger logger = Logger.getLogger(GetProcessStartFormBusinessOperation.class);

	@Inject
	@Named("systemConfiguration")
	private IConfiguration systemConfiguration;
	
	@Override
	public ProcessInstanceStartDTO execute(String processDefinitionId) throws BusinessException {
		logger.info("execute do GetProcessStarterFormBusinessOperation.");
		logger.debug("GetProcessStarterFormBusinessOperation processDefId: " + processDefinitionId);

		String bpmnKey = this.systemConfiguration.getProperty(ConfigurationType.BPMN_FORM_STARTER_KEY.getKey());
		logger.debug("bpmnKey: " + bpmnKey);
		
		//No momento a aplicação trabalha com apenas um processo BPMN. 
		//Remover trecho e utilizar parâmetro quando tela solicitar especifico
		List<ProcessDefinition> definitions = getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(bpmnKey).latestVersion().active().list();
		for (ProcessDefinition pd : definitions) {
			processDefinitionId = pd.getId();
			logger.debug("bpmnKey encontrado: " + processDefinitionId);
			break;
		}
		
		StartFormData formData = null;
		try {
			formData = getFormService().getStartFormData(processDefinitionId);	
		} catch (ActivitiObjectNotFoundException e) {
			logger.error("Definição de Processo não encontrado", e);			
			throw new ActivitiErrorException(ErrorCode.PROCESS_DEFINITION_EMPTY.getCode(), ErrorCode.PROCESS_DEFINITION_EMPTY.getDescription());
		}

		ProcessInstanceStartDTO dto = new ProcessInstanceStartDTO();
		List<VivoTaskFormItem> formItems = new ArrayList<VivoTaskFormItem>();
		
		for (FormProperty proper : formData.getFormProperties()) {
			VivoTaskFormItem item = transformerFactory.createTransformer(proper.getType()).transform(proper);
			formItems.add(item);
		}		
		
		dto.setProcessDefinitionId(processDefinitionId);
		dto.setFormItems(formItems);

		logger.info("FIM do GetProcessStarterFormBusinessOperation.");
		return dto;
	}

}
