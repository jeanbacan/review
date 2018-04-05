/**
 * 
 */
package br.com.vivo.bcm.business.operation.bean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.vivo.bcm.business.dto.ProcessInstanceStartDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.util.ConverterUtil;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;
import br.com.vivo.rubeus.client.security.IPrincipal;

/**
 * Retorna formulário de para start de uma instancia
 * 
 * @author Jean Bacan
 * @since 18/05/2017
 *
 */
@Named("startProcessInstanceBusinessOperation")
public class StartProcessInstanceBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<ProcessInstanceStartDTO, Void> {

	private static final String ACTIVITY_USER_GROUP_VAR = "idUserGroup";
	
	private static final Logger logger = Logger.getLogger(StartProcessInstanceBusinessOperation.class);

	@Inject
	@Named("saveActivitiUserBusinessOperation")
	private IBusinessOperation<Void, Void> saveActivitiUser;
	
	@Inject
	@Named("generateBusinessKeyBusinessOperation")
	private IBusinessOperation<List<VivoTaskFormItem>, String> generateBusinessKeyBusinessOperation;
	
	@Override
	@Transactional
	public Void execute(ProcessInstanceStartDTO dto) throws BusinessException {
		logger.info("execute do StartProcessInstanceBusinessOperation.");

		IPrincipal principal = super.securityHelper.getLoggedPrincipal();
		String startProcessCandidateGroup = this.securityHelper.getLoggedStartProcessCandidateGroup();
		
		//Criando usuário no identity para associar ao iniciar o processo
		saveActivitiUser.execute(null);
		getIdentityService().setAuthenticatedUserId(principal.getUid().toString());
		
		String generatedBusinessKey = generateBusinessKeyBusinessOperation.execute(dto.getFormItems());		
		
		Map<String, Object> map = ConverterUtil.transformFormItemsToObjectMap(dto.getFormItems());

		//Definindo grupo para abertura do Projeto (B2C ou B2B)		
		map.put(ACTIVITY_USER_GROUP_VAR, startProcessCandidateGroup);

		getRuntimeService().startProcessInstanceById(dto.getProcessDefinitionId(), generatedBusinessKey, map);

		getIdentityService().setAuthenticatedUserId(null);
		
		logger.info("FIM do StartProcessInstanceBusinessOperation.");
		return null;
	}
	
	/**
	 * @param saveActivitiUser the saveActivitiUser to set
	 */
	public void setSaveActivitiUser(SaveActivitiUserBusinessOperation saveActivitiUser) {
		this.saveActivitiUser = saveActivitiUser;
	}
}
