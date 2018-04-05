package br.com.vivo.bcm.business.test.integration;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.CountTasksBusinessOperation;
import br.com.vivo.rubeus.client.vo.GroupVO;

/**
 * 
 * Teste para Count de Tasks
 * 
 * @author Jean Bacan
 * @since 08/06
 *
 */
public class CountTasksTest extends BaseIntegrationTest {

	/**
	 * Lista instancias de processo
	 * 
	 * @throws BusinessException
	 */
	public void testProcessInstances() throws BusinessException {	
		
		//Injeta processEngine a ser utilizado pelo BO
		CountTasksBusinessOperation countBO = new CountTasksBusinessOperation();
		countBO.setSecurityHelper(helper);
		countBO.setTransformer(transformer);
		countBO.setProcessEngineConfiguration(processEngineConfig);
		
		//Mockito.when(helper.getLoggedGroups()).thenReturn(groups);
		
		long time = GregorianCalendar.getInstance().getTimeInMillis();
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
		
		countBO.execute(null);
		
		System.out.println(GregorianCalendar.getInstance().getTimeInMillis() - time);
	}	
}
