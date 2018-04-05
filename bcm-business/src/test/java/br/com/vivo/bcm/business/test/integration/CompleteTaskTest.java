package br.com.vivo.bcm.business.test.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.CompleteTaskBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

/**
 * 
 * Testa Complete de Task's
 * 
 * @author Jean Bacan
 * @since 15/05
 *
 */
public class CompleteTaskTest extends BaseIntegrationTest {

	private final String B2C = "B2C";

	private final String ALIVIO = "idAlivio";

	/**
	 * Testa resgate de candidate group no getTask
	 * 
	 * @throws BusinessException
	 */
	public void testGetTaskCandidateGroup() throws BusinessException {

		//Injeta processEngine a ser utilizado pelo BO
		CompleteTaskBusinessOperation completeBO = new CompleteTaskBusinessOperation();
		completeBO.setProcessEngineConfiguration(processEngineConfig);
		completeBO.setSecurityHelper(helper);
		
		//Starta processo
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("idUserGroup", B2C);
		maps.put("diretoria", "1");
		maps.put("uf", "2");
		maps.put("cidade", "1");
		maps.put("tipoTecnologia", "idFibra");
		maps.put("tipoInvestimento", ALIVIO);
		maps.put("analiseMercadologicaId", "ANALISE_" + Long.toString(RandomUtils.nextLong(1, 100)));

		ProcessInstance processInstance = processEngineConfig.getProcessEngine().getRuntimeService().startProcessInstanceByKey("analise_mercadologica_process", maps);

		// taskAbertura
		Task task = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

		Map<String, String> formMap = new HashMap<String, String>();
		List<VivoTaskFormItem> formItens = new ArrayList<VivoTaskFormItem>();

		formMap.put("idNomeNovaArea", "1");
		formMap.put("idMercadoRes", "1");
		formMap.put("idMediaLinhasRes", "1");
		formMap.put("idTxPenetracaoRes", "1");
		formMap.put("idMercadoNeg", "1");
		formMap.put("idMediaLinhasNeg", "1");
		formMap.put("idTxPenetracaoNeg", "1");
		formMap.put("idMercadoResPred", "1");
		formMap.put("idMediaLinhasResPred", "1");
		formMap.put("idTxPenetracaoResPred", "1");
		formMap.put("idMercadoNegPred", "1");
		formMap.put("idMediaLinhasNegPred", "1");
		formMap.put("idTxPenetracaoNegPred", "1");
		formMap.put("idNovaArea", "1");
		formMap.put("idMapaCobertura", "1");
		formMap.put("idLotFace", "1");

		for (Map.Entry<String, String> entry : formMap.entrySet()) {
			VivoTaskFormItem item = new VivoTaskFormItem();
			item.setId(entry.getKey());
			item.setValue(entry.getValue());

			formItens.add(item);
		}
		VivoTask vivoTask = new VivoTask();
		vivoTask.setFormItens(formItens);
		vivoTask.setTaskId(task.getId());

		completeBO.execute(vivoTask);
		
		HistoricTaskInstance historicTaskInstance = processEngineConfig.getProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().finished().includeTaskLocalVariables().taskId(task.getId()).singleResult();
		Assert.assertTrue(historicTaskInstance.getTaskLocalVariables().size() > 0);		
		Assert.assertTrue(historicTaskInstance.getId().equals(task.getId()));
		
		processEngineConfig.getProcessEngine().getRuntimeService().deleteProcessInstance(processInstance.getProcessInstanceId(), "Deleted by a great force!");
	}
}
