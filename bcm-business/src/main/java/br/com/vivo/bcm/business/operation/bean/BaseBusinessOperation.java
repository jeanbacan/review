package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import br.com.vivo.bcm.business.configuration.bean.ProcessEngineConfiguration;
import br.com.vivo.bcm.business.dto.form.transformer.FormTransformerFactory;
import br.com.vivo.bcm.business.helper.ISecurityHelper;
import br.com.vivo.bcm.business.helper.bean.SecurityHelper;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.MyDocumentVO;
import br.com.vivo.configurationutils.IConfiguration;

/**
 * Base para BO
 * 
 * @author Jean Bacan
 * @snce 08/05/2017
 *
 */
public class BaseBusinessOperation {

	@Inject
	@Named("processEngineConfiguration")
	private ProcessEngineConfiguration processEngineConfiguration;

	@Inject
	@Named("securityHelper")
	protected ISecurityHelper securityHelper;

	@Inject
	@Named("activitiFormTransformer")
	protected FormTransformerFactory transformerFactory;

	@Inject
	@Named("getMyDocumentByIdBusinessOperation")
	protected IBusinessOperation<String, MyDocumentVO> getMyDocumentByIdBusinessOperation;
	
	@Inject
	@Named("systemConfiguration")
	protected IConfiguration systemConfiguration;

	protected ManagementService getManagementService() {
		return processEngineConfiguration.getProcessEngine().getManagementService();
	}

	protected IdentityService getIdentityService() {
		return processEngineConfiguration.getProcessEngine().getIdentityService();
	}

	protected TaskService getTaskService() {
		return processEngineConfiguration.getProcessEngine().getTaskService();
	}

	/**
	 * @return the formService
	 */
	protected FormService getFormService() {
		return processEngineConfiguration.getProcessEngine().getFormService();
	}

	/**
	 * @return the runtimeService
	 */
	public RuntimeService getRuntimeService() {
		return processEngineConfiguration.getProcessEngine().getRuntimeService();
	}
	
	/**
	 * @return the historyService
	 */
	protected HistoryService getHistoryService() {
		return processEngineConfiguration.getProcessEngine().getHistoryService();
	}

	/**
	 * @return the repositoryService
	 */
	protected RepositoryService getRepositoryService() {
		return processEngineConfiguration.getProcessEngine().getRepositoryService();
	}

	/**
	 * Seta process engine para test unit
	 * 
	 * @param processEngineConfiguration the processEngineConfiguration to set
	 */
	public ProcessEngineConfiguration getProcessEngineConfiguration() {
		return processEngineConfiguration;
	}

	/**
	 * @param processEngineConfiguration the processEngineConfiguration to set
	 */
	public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
		this.processEngineConfiguration = processEngineConfiguration;
	}

	/**
	 * @param processEngineConfiguration the processEngineConfiguration to set
	 */
	public void setSecurityHelper(SecurityHelper securityHelper) {
		this.securityHelper = securityHelper;
	}

	/**
	 * @param transformer the transformer to set
	 */
	public void setTransformer(FormTransformerFactory transformer) {
		this.transformerFactory = transformer;
	}

	/**
	 * @param runtimeService
	 */
	public void setProcessEngine(ProcessEngine processEngine){
		processEngineConfiguration.setProcessEngine(processEngine);
	}	

}
