package br.com.vivo.bcm.business.test.integration;

import org.junit.Test;
import org.mockito.Mock;

import br.com.vivo.bcm.business.dao.IActivitiFormVariablesDAO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.GetOpenedTaskBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;

/**
 * 
 * Testa resgate de Task's
 * 
 * @author Jean Bacan
 * @since 08/05
 *
 */
public class GetOpenedTaskTest extends BaseIntegrationTest {

	@Mock
	IActivitiFormVariablesDAO formDAO;
	
	/**
	 * Testa resgate de candidate group no getTask
	 * @throws BusinessException 
	 */
	public void testGetTaskCandidateGroup() throws BusinessException {

		GetOpenedTaskBusinessOperation bo = new GetOpenedTaskBusinessOperation();
		bo.setProcessEngineConfiguration(processEngineConfig);
		bo.setSecurityHelper(helper);
		bo.setTransformer(transformer);
		
		VivoTask task = bo.execute("12682");

		System.out.println(task.getFormItens());
				
		
	}
}
