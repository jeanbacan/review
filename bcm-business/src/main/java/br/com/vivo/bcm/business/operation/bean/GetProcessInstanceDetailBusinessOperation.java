/**
 * 
 */
package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.enums.TaskStatusType;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

/**
 * 
 * @author Jean Bacan
 * @since 09/06/2017
 *
 */
@Named("getProcessInstanceDetail")
public class GetProcessInstanceDetailBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String, List<VivoTask>> {

	private static final Logger logger = Logger.getLogger(GetProcessInstanceDetailBusinessOperation.class);

	@Override
	public List<VivoTask> execute(String businessKey) throws BusinessException {
		logger.info("execute do GetProcessInstanceDetailBusinessOperation.");
		logger.debug("GetProcessInstanceDetailBusinessOperation businessKey: " + businessKey);

		List<VivoTask> result = new ArrayList<VivoTask>();

		try {

			// Consulta tasks atual e do historico para um businessKey
			List<Task> tasks = getTaskService().createTaskQuery().includeTaskLocalVariables()
					.processInstanceBusinessKey(businessKey).list();
			List<HistoricTaskInstance> historicTasks = getHistoryService().createHistoricTaskInstanceQuery()
					.includeProcessVariables().includeTaskLocalVariables().finished()
					.processInstanceBusinessKey(businessKey).orderByHistoricTaskInstanceEndTime().desc().list();			

			List<VivoTaskFormItem> headers = new ArrayList<VivoTaskFormItem>();
			List<VivoTaskFormItem> formFields = new ArrayList<VivoTaskFormItem>();

			HistoricTaskInstance historicTaskInstance = null;
			HistoricProcessInstance processInstance = null;

			// Monta tasks do historico
			for (HistoricTaskInstance historicTask : historicTasks) {

				VivoTask taskVO = null;

				try {

					historicTaskInstance = getHistoryService().createHistoricTaskInstanceQuery().includeTaskLocalVariables().finished().taskId(historicTask.getId()).singleResult();
					processInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).includeProcessVariables().singleResult();

					taskVO = new VivoTask(historicTaskInstance, TaskStatusType.CLOSED, null);

					// buscando dados da task. Form e CandidateGroup
					BpmnModel bpmnModel = getRepositoryService().getBpmnModel(historicTaskInstance.getProcessDefinitionId());

					Process mainProcess = bpmnModel.getProcesses().get(0);
					UserTask task = (UserTask) mainProcess.getFlowElement(historicTaskInstance.getTaskDefinitionKey());

					// Prepara transformer dos fields
					headers = new ArrayList<VivoTaskFormItem>();
					formFields = new ArrayList<VivoTaskFormItem>();

					for (org.activiti.bpmn.model.FormProperty formProperty : task.getFormProperties()) {
						
						String value = "";
						try {
							Object objValue = historicTaskInstance.getTaskLocalVariables().get(formProperty.getId());
							
							if (objValue instanceof Long){
								
								logger.info("FIeld : " + formProperty.getName() + " / " + formProperty.getId());
								logger.info(formProperty.toString());
								
								value = ((Long) objValue).toString();
							} else {
								value = (String) objValue;
							}
							
							if (value == null) {
								value = (String) processInstance.getProcessVariables().get(formProperty.getId());
							}	
						} catch (Exception e) {
							logger.error("FIeld : " + formProperty.getName() + " / " + formProperty.getId(), e);
							logger.error(formProperty.toString());
							
							value = "";
						}

						VivoTaskFormItem formField = transformerFactory.createTransformer(formProperty.getType()).transform(formProperty, value);

						if (formField.isHeader()) {
							headers.add(formField);
						} else {
							formFields.add(formField);
						}
					}

					taskVO.setHeaders(headers);
					taskVO.setFormItens(formFields);
					taskVO.setBusinessKey(businessKey);
				} catch (Exception e) {
					logger.error(e);
					throw new BusinessException(e);
				}

				result.add(taskVO);
			}

			headers = new ArrayList<VivoTaskFormItem>();
			formFields = new ArrayList<VivoTaskFormItem>();
			for (Task task : tasks) {

				// Monta task's atuais quando mais de uma
				VivoTask taskVO = new VivoTask(task, TaskStatusType.OPENED, null);

				TaskFormData taskFormData = getFormService().getTaskFormData(task.getId());

				for (FormProperty formProperty : taskFormData.getFormProperties()) {
					VivoTaskFormItem formField = transformerFactory.createTransformer(formProperty.getType()).transform(formProperty);

					if (VivoTaskFormType.DATAGRID.equals(formField.getType())) {

						for (VivoTaskFormItem vivoTaskFormItem : formField.getDataGridDTO().getValues()) {
							vivoTaskFormItem.setWritable(false);
						}
					}

					if (formField.isHeader()) {
						headers.add(formField);
					} else {
						formFields.add(formField);
					}
				}

				taskVO.setHeaders(headers);
				taskVO.setFormItens(formFields);
				taskVO.setBusinessKey(businessKey);

				// Adicionando tarefas abertas
				result.add(0, taskVO);
			}

		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}

		logger.info("FIM do GetProcessInstanceDetailBusinessOperation.");
		return result;
	}

}
