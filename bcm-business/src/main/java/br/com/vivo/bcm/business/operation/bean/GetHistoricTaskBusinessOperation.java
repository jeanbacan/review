package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.enums.TaskStatusType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;
import br.com.vivo.rubeus.client.vo.GroupVO;
import br.com.vivo.rubeus.client.vo.UserVO;

/**
 * Pega uma tarefa ja concluida.
 * 
 * @author Jean Bacan
 * @snce 08/05/2017
 *
 */
@Named("getHistoricTaskBusinessOperation")
public class GetHistoricTaskBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String, VivoTask> {

	private static final Logger logger = Logger.getLogger(GetHistoricTaskBusinessOperation.class);

	@Override
	public VivoTask execute(String taskId) throws BusinessException {

		logger.info("execute do GetTaskBusinessOperation .");

		VivoTask taskVO = null;

		try {

			UserVO userVO = null;

			HistoricTaskInstance historicTaskInstance = getHistoryService().createHistoricTaskInstanceQuery().includeTaskLocalVariables().finished().taskId(taskId).singleResult();
			HistoricProcessInstance processInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).includeProcessVariables().singleResult();
			
			// buscando nome do Projeto
			if (StringUtils.isNotBlank(historicTaskInstance.getAssignee())) {
				userVO = this.securityHelper.findUserById(Long.parseLong(historicTaskInstance.getAssignee()));
			}
			taskVO = new VivoTask(historicTaskInstance, TaskStatusType.CLOSED, userVO);

			// buscando dados da task. Form e CandidateGroup
			BpmnModel bpmnModel = getRepositoryService().getBpmnModel(historicTaskInstance.getProcessDefinitionId());
			List<HistoricIdentityLink> identityLinks = getHistoryService().getHistoricIdentityLinksForTask(taskId);
			
			GroupVO group = this.securityHelper.getGroupByCandidateGroup(identityLinks.get(0).getGroupId());
			
			Process mainProcess = bpmnModel.getProcesses().get(0);
			UserTask task = (UserTask) mainProcess.getFlowElement(historicTaskInstance.getTaskDefinitionKey());

			//Prepara transformer dos fields
			List<VivoTaskFormItem> headers = new ArrayList<VivoTaskFormItem>();
			List<VivoTaskFormItem> formFields = new ArrayList<VivoTaskFormItem>();

			for (FormProperty formProperty : task.getFormProperties()) {
				String value = (String) historicTaskInstance.getTaskLocalVariables().get(formProperty.getId());
				if (value == null){
					value = (String) processInstance.getProcessVariables().get(formProperty.getId());
				}
				
				VivoTaskFormItem formField = transformerFactory.createTransformer(formProperty.getType()).transform(formProperty, value);
				
				if (formField.isHeader()){				
					headers.add(formField);
				} else {
					formFields.add(formField);
				}
			}			
			
			taskVO.setHeaders(headers);
			taskVO.setFormItens(formFields);
			taskVO.setCandidateGroup(group.getName());
			taskVO.setCreateProcessTime(processInstance.getStartTime());
			taskVO.setCandidateGroupDescription(group.getDescription());
			taskVO.setBusinessKey(processInstance.getBusinessKey());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
		return taskVO;
	}
}
