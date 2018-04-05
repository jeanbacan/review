package br.com.vivo.bcm.business.test.integration;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * Classe de teste para abrir processo de analise mercadologica e validar cenarios.
 * 
 * @author Jean Marcel
 *
 */
public class FluxoBCTest extends BaseIntegrationTest {

	private final String B2C = "B2C";
	private final String B2B = "B2B";
	
	private final String NAO = "idNao";
	private final String SIM = "idSim";

	private final String OUTROS = "idOutros";

	/**
	 * Testa processo selecionando a opção alivio para tipo de investimento
	 * 
	 * @param idUserGroup
	 * @param tipoInvestimento
	 * @throws ParseException
	 */
	public void testFluxoBCNormal() throws ParseException {

		
		ProcessInstance processInstance = null;
		
		try {

			//super.importModel();
					
			Map<String, Object> maps = new HashMap<String, Object>();
	
			maps.put("idUserGroup", B2B);
			maps.put("diretoria", "1");
			maps.put("uf", "2");
			maps.put("cidade", "1");
			maps.put("sugerirInclusaoArea", SIM);
			maps.put("tipoTecnologia", "idFibra");
			maps.put("tipoInvestimentoOld", "idCentral");
			
			processInstance = processEngineConfig.getProcessEngine().getRuntimeService().startProcessInstanceByKey("execucaoBC_process", maps);
			
			//Gerar demanda
			Task task = getTask(processInstance.getProcessInstanceId());
			
			Map<String, String> formMap = new HashMap<String, String>();
			formMap.put("tipoInvestimento", "idAlivioLogradouro");
			
			completeTask(task, formMap);
			
			//Abrir BC
			task = getTask(processInstance.getProcessInstanceId());
			
			formMap = new HashMap<String, String>();
			formMap.put("tipoInvestimento", "idAlivioLogradouro");
			
			completeTask(task, formMap);

			//Abrir BC
			task = getTask(processInstance.getProcessInstanceId());
			
			formMap = new HashMap<String, String>();
			formMap.put("tipoInvestimento", "idAlivioLogradouro");
			
			completeTask(task, formMap);			
			
			List<Task> tasks = getMultipleTask(processInstance.getProcessInstanceId());			
			
			formMap = new HashMap<String, String>();
			
			//Custos RE e PDD
			completeTask(tasks.get(0), formMap);
			completeTask(tasks.get(1), formMap);
							
			//Custos RI
			task = getTask(processInstance.getProcessInstanceId());
			
			formMap = new HashMap<String, String>();			
			
			completeTask(task, formMap);
			
			//Resultado financeiro
			task = getTask(processInstance.getProcessInstanceId());
			
			formMap = new HashMap<String, String>();
			formMap.put("idRecomendacao", "idNaoRecomendado");			
			
			completeTask(task, formMap);
			
			//Obra
			task = getTask(processInstance.getProcessInstanceId());			

			System.out.println(task);
			
		} catch (Exception ex){
			System.out.println(ex);
		} finally {
			processEngineConfig.getProcessEngine().getRuntimeService().deleteProcessInstance(processInstance.getProcessInstanceId(), "Deleted by a great force!");
		}
	}
	
	/**
	 * Testa processo selecionando a opção alivio para tipo de investimento
	 * 
	 * @param idUserGroup
	 * @param tipoInvestimento
	 * @throws ParseException
	 */
	//@Test
	public void testFluxoBCPequenasObras() throws ParseException {

		ProcessInstance processInstance = null;
		
		try {
					
			Map<String, Object> maps = new HashMap<String, Object>();
	
			maps.put("idUserGroup", B2C);
			maps.put("diretoria", "1");
			maps.put("uf", "2");
			maps.put("cidade", "1");
			maps.put("sugerirInclusaoArea", NAO);
			maps.put("tipoTecnologia", "idFibra");
			maps.put("tipoInvestimentoOld", OUTROS);
			
			processInstance = processEngineConfig.getProcessEngine().getRuntimeService().startProcessInstanceByKey("execucaoBC_process", maps);
			
			Task task = getTask(processInstance.getProcessInstanceId());
			
			Map<String, String> formMap = new HashMap<String, String>();
			formMap.put("tipoInvestimento", "idAlivioLogradouro");
			
			completeTask(task, formMap);		
			
			List<Task> tasks = getMultipleTask(processInstance.getProcessInstanceId());			
			
			formMap = new HashMap<String, String>();
			
			completeTask(tasks.get(0), formMap);
			completeTask(tasks.get(1), formMap);
							
			task = getTask(processInstance.getProcessInstanceId());
			
			formMap = new HashMap<String, String>();			
			
			completeTask(task, formMap);
			
			task = getTask(processInstance.getProcessInstanceId());
			
			formMap = new HashMap<String, String>();
			formMap.put("idRecomendacao", "idRecomendado");			
			
			completeTask(task, formMap);
			
			task = getTask(processInstance.getProcessInstanceId());			

		} catch (Exception ex){
			System.out.println(ex);
		} finally {
			processEngineConfig.getProcessEngine().getRuntimeService().deleteProcessInstance(processInstance.getProcessInstanceId(), "Deleted by a great force!");
		}
	}

	private List<Task> getMultipleTask(String processId) {
		return processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processId).list();
	}
	
	/**
	 * @param group
	 * @param processId
	 * @return Task
	 */
	private Task getTask(String processId) {
		Task task = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().includeTaskLocalVariables().includeProcessVariables().processInstanceId(processId).singleResult();
		return task;
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

	public void listAllTasks() {
		List<Task> tasks = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().list();
		for (Task t : tasks) {
			System.out.println("ID: " + t.getId() + " | NOME: " + t.getName() + " | PROCESS INSTANCE: " + t.getProcessInstanceId());
		}
	}

}
