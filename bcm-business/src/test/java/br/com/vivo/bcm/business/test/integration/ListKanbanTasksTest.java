package br.com.vivo.bcm.business.test.integration;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.vivo.bcm.business.dto.filter.Constraint;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.ConstraintType;
import br.com.vivo.bcm.business.enums.KanbanTaskType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.ListKanbanTasksBusinessOperation;
import br.com.vivo.bcm.business.util.ApplicationConstants;
import br.com.vivo.bcm.business.vo.VivoTask;

/**
 * 
 * Testa List de Task's
 * 
 * @author Jean Bacan
 * @since 16/05
 *
 */
public class ListKanbanTasksTest extends BaseIntegrationTest {

	/**
	 * Testa resgate de candidate group no getTask
	 * 
	 * @throws BusinessException
	 */
	public void testGetAllTasks() throws BusinessException {			
		//Injeta processEngine a ser utilizado pelo BO
		ListKanbanTasksBusinessOperation listBO = new ListKanbanTasksBusinessOperation();
		listBO.setProcessEngineConfiguration(processEngineConfig);
		listBO.setSecurityHelper(helper);
		listBO.setTransformer(transformer);
		
		Map<String, Object> maps = new HashMap<String, Object>();

		maps.put("idUserGroup", "B2C");
		maps.put("diretoria", "idSul");
		maps.put("uf", "idParana");
		maps.put("cidade", "idCuritiba");
		maps.put("tipoTecnologia", "idFibra");
		maps.put("tipoInvestimento", "idAlivio");
		maps.put("analiseMercadologicaId", "ANALISE_" + Long.toString(RandomUtils.nextLong(1, 100)));
		
//		processEngineConfig.getProcessEngine().getRuntimeService().startProcessInstanceByKey("analise_mercadologica_process", maps);
		
		//Para associar alguem quando nao existir.
//		List<Task> pis = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().list();
//		for (Task pi : pis) {
//			processEngineConfig.getProcessEngine().getTaskService().claim(pi.getId(), "12345");
//			break;
//		}
		
		List<String> groups = new ArrayList<String>();
		groups.add("B2C");
		groups.add("B2B");
		groups.add("ARRANJO_FISICO");
		groups.add("ANALISE_MERCADOLOGICA");
		groups.add("LEVANTAMENTO_MERCADO");
		groups.add("SEGURANCA");
		groups.add("CADASTRO_MERCADO");
		groups.add("PDD_CHURN");
		groups.add("OBRA");
		groups.add("RESULTADO_FINANCEIRO");
		
		Mockito.when(helper.getLoggedCadidateGroups()).thenReturn(groups);
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
		
		Map<KanbanTaskType, List<VivoTask>> result = listBO.execute(new FilterDTO());
		System.out.println(result);	
		
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
	}
	
	public void testGetByUF() throws BusinessException {
		ListKanbanTasksBusinessOperation listBO = new ListKanbanTasksBusinessOperation();
		listBO.setProcessEngineConfiguration(processEngineConfig);
		listBO.setSecurityHelper(helper);
		listBO.setTransformer(transformer);
		
		List<String> groups = new ArrayList<String>();
		groups.add("B2C");
		groups.add("B2B");
		groups.add("ARRANJO_FISICO");
		groups.add("ANALISE_MERCADOLOGICA");
		groups.add("LEVANTAMENTO_MERCADO");
		groups.add("SEGURANCA");
		groups.add("CADASTRO_MERCADO");
		groups.add("PDD_CHURN");
		groups.add("OBRA");
		groups.add("RESULTADO_FINANCEIRO");
		
		Mockito.when(helper.getLoggedCadidateGroups()).thenReturn(groups);
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
		
		FilterDTO filter = new FilterDTO();
		Constraint constraint = new Constraint();
		constraint.setType(ConstraintType.EQUAL);
		constraint.setValue("2");
		
		Map<String, Constraint> map = new HashMap<String, Constraint>();
		map.put(ApplicationConstants.FILTER_SELECTED_UF, constraint);
		
		filter.setConstraints(map);
		
		Map<KanbanTaskType, List<VivoTask>> result = listBO.execute(filter);
		System.out.println(result);	
		
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
	}
}
