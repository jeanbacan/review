package br.com.vivo.bcm.business.test.integration;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import br.com.vivo.bcm.business.vo.VivoTask;

/**
 * 
 * Testa resgate de Task's
 * 
 * @author Jean Bacan
 * @since 08/05
 *
 */
public class GetTaskTest extends BaseIntegrationTest {

	private final String B2C = "B2C";

	private final String ALIVIO = "idAlivio";

	/**
	 * Testa resgate de candidate group no getTask
	 */
	public void testGetTaskCandidateGroup() {

		Map<String, Object> maps = new HashMap<String, Object>();

		maps.put("idUserGroup", B2C);
		maps.put("diretoria", "idSul");
		maps.put("uf", "idParana");
		maps.put("cidade", "idCuritiba");
		maps.put("tipoTecnologia", "idFibra");
		maps.put("tipoInvestimento", ALIVIO);
		maps.put("analiseMercadologicaId", "ANALISE_" + Long.toString(RandomUtils.nextLong(1, 100)));

		ProcessInstance processInstance = processEngineConfig.getProcessEngine().getRuntimeService().startProcessInstanceByKey("analise_mercadologica_process", maps);

		// taskAbertura
		Task task = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();

		VivoTask vivoTask = new VivoTask();
		
		Calendar date = GregorianCalendar.getInstance();
		date.add(Calendar.DATE, 5);
		
		vivoTask.setDueDate(date.getTime());
		System.out.println(vivoTask.getLateDays());
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		//List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);

		time = GregorianCalendar.getInstance().getTimeInMillis();
		BpmnModel model = processEngineConfig.getProcessEngine().getRepositoryService().getBpmnModel(task.getProcessDefinitionId());
		for (org.activiti.bpmn.model.Process process : model.getProcesses()) {
			for (FlowElement flowElement : process.getFlowElements()) {
				if (flowElement instanceof UserTask) {
					UserTask userTask = (UserTask) flowElement;
					if (userTask.getId().equals(task.getTaskDefinitionKey())) {
						if (userTask.getCandidateGroups() != null && userTask.getCandidateGroups().size() > 0) {
							System.out.println(userTask.getCandidateGroups().get(0));
						}
					}
				}
			}
		}
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
	}
}
