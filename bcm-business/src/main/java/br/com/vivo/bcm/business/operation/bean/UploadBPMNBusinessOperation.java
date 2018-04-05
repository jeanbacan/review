package br.com.vivo.bcm.business.operation.bean;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.inject.Named;

import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cmd.SetProcessDefinitionVersionCmd;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.UploadBPMNVO;

@Named("uploadBPMNBusinessOperation")
public class UploadBPMNBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<UploadBPMNVO, Void> {

	private static final Logger logger = Logger.getLogger(UploadBPMNBusinessOperation.class);

	@Override
	@Transactional
	public Void execute(UploadBPMNVO uploadBPMNVO) throws BusinessException {
		logger.info("execute do UploadBPMNBusinessOperation - inicio");

		File fileBpmn = new File(uploadBPMNVO.getFilePath());
		fileBpmn.deleteOnExit();
		Deployment deployment = null;
		
		try {
			FileInputStream fileInputStream = new FileInputStream(fileBpmn);
			deployment = getRepositoryService().createDeployment().addInputStream(uploadBPMNVO.getFileName(), fileInputStream).deploy();
			
			fileInputStream.close();
			
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
		
		//Get deployed ProcessDefinition 
		ProcessDefinition definition = getRepositoryService().createProcessDefinitionQuery().active().deploymentId(deployment.getId()).singleResult();
		
		//Get all active processInstance
		List<ProcessInstance> processList = super.getRuntimeService().createProcessInstanceQuery().active().list();
		for(ProcessInstance processInstance : processList){
			SetProcessDefinitionVersionCmd command = new SetProcessDefinitionVersionCmd(processInstance.getId(), definition.getVersion());
			
			try {
				ProcessEngineImpl impl = (ProcessEngineImpl)getProcessEngineConfiguration().getProcessEngine();
				impl.getProcessEngineConfiguration().getCommandExecutor().execute(command);				
			} catch (Exception e) {
				logger.debug("Não foi possível migrar: " + processInstance.getBusinessKey());
				logger.warn(e);				
			}			
		}
		logger.info("execute do UploadBPMNBusinessOperation - Fim");
		return null;
	}
}