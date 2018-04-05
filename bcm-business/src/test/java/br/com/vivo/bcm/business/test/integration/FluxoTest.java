package br.com.vivo.bcm.business.test.integration;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * Classe de teste para abrir processo de analise mercadologica e validar cenarios.
 * 
 * @author Jean Marcel
 *
 */
public class FluxoTest extends BaseIntegrationTest {

	private final String B2C = "B2C";

	private final String NAO = "idNao";
	private final String SIM = "idSim";
	
	/**
	 * Testa processo selecionando a opção alivio para tipo de investimento
	 * 
	 * @param idUserGroup
	 * @param tipoInvestimento
	 * @throws ParseException
	 */
	public void testAlivio() throws ParseException {

		ProcessInstance processInstance = null;
		
//		cleanup();		
//		completeTask(null, null);
		
		try {
			
			super.importModel();
			
			Map<String, Object> maps = new HashMap<String, Object>();

			maps.put("idUserGroup", B2C);
			maps.put("diretoria", "1");
			maps.put("uf", "1");
			maps.put("cidade", "1");
			maps.put("tipoTecnologia", "idFibra");
			maps.put("tipoInvestimento", "idCentral");
			
			processInstance = processEngineConfig.getProcessEngine().getRuntimeService().startProcessInstanceByKey("analise_mercadologica_process", "ANALISE_TESTE_" + Long.toString(RandomUtils.nextLong(1, 100)), maps);
		
			// taskAbertura
			Task task = getTask(processInstance.getProcessInstanceId());
			Map<String, String> formMap = new HashMap<String, String>();
	
			formMap.put("facilidade", "1");
	
			completeTask(task, formMap);
			
			// B2B preenche interesse
			task = getTask(processInstance.getProcessInstanceId());
			formMap = new HashMap<String, String>();
	
			formMap.put("possuiInteresse", SIM);
			formMap.put("sugerirInclusaoArea", SIM);
			
			completeTask(task, formMap);
			
			// taskAbertura
			task = getTask(processInstance.getProcessInstanceId());
			formMap = new HashMap<String, String>();
	
			formMap.put("facilidade", "1");
	
			completeTask(task, formMap);			
					
			// Revisa investimento
			task = getTask(processInstance.getProcessInstanceId());
			formMap = new HashMap<String, String>();
			
			completeTask(task, formMap);
			
			// Levantamento - Solicita levantamento
			task = getTask(processInstance.getProcessInstanceId());
			formMap = new HashMap<String, String>();
	
			formMap.put("levantamentoValido", SIM);
		
			completeTask(task, formMap);
			
			// Cadastro Mercado
			task = getTask(processInstance.getProcessInstanceId());
			formMap = new HashMap<String, String>();
	
			completeTask(task, formMap);
					
			//B2C 
			task = getTask(processInstance.getProcessInstanceId()); 
			
			formMap = new HashMap<String, String>();
			formMap.put("facilidade", "1");
			formMap.put("idMercadoCompletoB2C", SIM); 
			
			completeTask(task, formMap);
			
			// B2B			
			task = getTask(processInstance.getProcessInstanceId());
			
			formMap = new HashMap<String, String>();			
			formMap.put("facilidade", "1");
			formMap.put("idMercadoCompletoB2B", NAO); 
			
			completeTask(task, formMap);
			
			// Cadastro Mercado
			task = getTask(processInstance.getProcessInstanceId());
			formMap = new HashMap<String, String>();
	
			completeTask(task, formMap);			
		
			// B2C			
			task = getTask(processInstance.getProcessInstanceId());		

			formMap = new HashMap<String, String>();
			formMap.put("facilidade", "1");
			formMap.put("idMercadoCompletoB2C", SIM); 
			
			completeTask(task, formMap);
			
			// B2B
			task = getTask(processInstance.getProcessInstanceId());		

			formMap = new HashMap<String, String>();
			formMap.put("facilidade", "1");
			formMap.put("idMercadoCompletoB2B", SIM); 
			
			completeTask(task, formMap);

			//Arranjo Fisico
			task = getTask(processInstance.getProcessInstanceId());		

			formMap = new HashMap<String, String>();			
			formMap.put("facilidade", "1");
			formMap.put("idMercadoCompletoB2C", SIM);
			
			completeTask(task, formMap);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			processEngineConfig.getProcessEngine().getRuntimeService().deleteProcessInstance(processInstance.getId(), "Only tests");
		}
	}

	/**
	 * @param task
	 * @return TaskFormData
	 */
	private TaskFormData getTaskFormData(Task task) {
		return processEngineConfig.getProcessEngine().getFormService().getTaskFormData(task.getId());
	}
	
	public void cleanup() {
		List<ProcessInstance> pis = processEngineConfig.getProcessEngine().getRuntimeService().createProcessInstanceQuery().active().list();
		for (ProcessInstance pi : pis) {
			processEngineConfig.getProcessEngine().getRuntimeService().deleteProcessInstance(pi.getId(), "Only tests");
		}
	}

	/**
	 * @param group
	 * @param processId
	 * @return Task
	 */
	private Task getTask(String processId) {
		Task task = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processId).singleResult();
		return task;
	}
	
	private List<Task> getMultipleTask(String processId) {
		return processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().processDefinitionId(processId).list();
	}

	private HistoricTaskInstance getTaskHistoric(String group, String processId) {
		return processEngineConfig.getProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().taskCandidateGroup(group).processDefinitionId(processId).singleResult();
	}

	/**
	 * @param taskId
	 * @param map
	 */
	public void saveFormData(String taskId, HashMap<String, String> map) {
		processEngineConfig.getProcessEngine().getFormService().saveFormData(taskId, map);
	}

	/**
	 * @param task
	 * @param parameters
	 */
	public void completeTask(Task task, Map<String, String> parameters) {
		processEngineConfig.getProcessEngine().getFormService().submitTaskFormData(task.getId(), parameters);
	}
}
