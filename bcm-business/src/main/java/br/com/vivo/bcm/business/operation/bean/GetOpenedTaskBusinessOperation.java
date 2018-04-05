package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
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
 * Pega uma tarefa Aberta.
 * 
 * @author Jean Bacan
 * @snce 08/05/2017
 *
 */
@Named("getOpenedTaskBusinessOperation")
public class GetOpenedTaskBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String, VivoTask> {

	private static final Logger logger = Logger.getLogger(GetOpenedTaskBusinessOperation.class);

	@Override
	public VivoTask execute(String taskId) throws BusinessException {

		logger.info("execute do GetOpenedTaskBusinessOperation .");

		VivoTask taskVO = null;

		try {

			UserVO userVO = null;

			Task task = getTaskService().createTaskQuery().includeTaskLocalVariables().taskId(taskId).singleResult();
			HistoricProcessInstance processInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult(); 
			
			if (StringUtils.isNotBlank(task.getAssignee())) {
				userVO = securityHelper.findUserById(Long.parseLong(task.getAssignee()));
			}
			taskVO = new VivoTask(task, TaskStatusType.OPENED, userVO);
			
			// buscando dados da task. Form e CandidateGroup
			TaskFormData taskFormData = getFormService().getTaskFormData(taskId);
			
			List<IdentityLink> identityLinks = getTaskService().getIdentityLinksForTask(taskVO.getTaskId());
			GroupVO group = this.securityHelper.getGroupByCandidateGroup(identityLinks.get(0).getGroupId());
			
			//Prepara transformer dos fields
			List<VivoTaskFormItem> headers = new ArrayList<VivoTaskFormItem>();
			List<VivoTaskFormItem> formFields = new ArrayList<VivoTaskFormItem>();

			for (FormProperty formProperty : taskFormData.getFormProperties()) {
				VivoTaskFormItem formField = transformerFactory.createTransformer(formProperty.getType()).transform(formProperty);
				
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
