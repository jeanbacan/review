package br.com.vivo.bcm.business.test.integration;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.vivo.bcm.business.dto.ResultPageDTO;
import br.com.vivo.bcm.business.dto.filter.Constraint;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.ConstraintType;
import br.com.vivo.bcm.business.enums.ProcessInstanceStatusType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.ListProcessInstancesBusinessOperation;
import br.com.vivo.bcm.business.search.SortType;
import br.com.vivo.bcm.business.util.ApplicationConstants;
import br.com.vivo.bcm.business.vo.ProcessInstanceVO;

/**
 * 
 * Testa List de ProcessInstances
 * 
 * @author Jean Bacan
 * @since 22/05
 *
 */
public class ListProcessInstancesTest extends BaseIntegrationTest {

	/**
	 * Lista instancias de processo
	 * 
	 * @throws BusinessException
	 */
	public void testProcessInstances() throws BusinessException {	
		
		//Injeta processEngine a ser utilizado pelo BO
		ListProcessInstancesBusinessOperation listBO = new ListProcessInstancesBusinessOperation();
		listBO.setProcessEngineConfiguration(processEngineConfig);
		listBO.setSecurityHelper(helper);
		
		Mockito.when(helper.isLoggedUserAdmin()).thenReturn(true);
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
		
		FilterDTO dto = new FilterDTO();
		
		Constraint cons = new Constraint();
		cons.setType(ConstraintType.EQUAL);
		cons.setValue(ProcessInstanceStatusType.CANCELED.toString());
		
		Map<String, Constraint> filterMap = new HashMap<String, Constraint>();
		filterMap.put(ApplicationConstants.FILTER_PROCESS_STATUS.toString(), cons);

		dto.setSortType(SortType.DESC);
		dto.setConstraints(filterMap);
		
		ResultPageDTO result = listBO.execute(dto);
		
		Assert.assertTrue(result.getResultCount() > 0);
		
		@SuppressWarnings("unchecked") 
		List<ProcessInstanceVO> vos = (List<ProcessInstanceVO>) result.getListResult();
		
		for (ProcessInstanceVO vo : vos){
			System.out.println(vo);
		}
		
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
	}
}
