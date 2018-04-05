package br.com.vivo.bcm.business.test.integration;

import org.junit.Test;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.GetHistoricTaskBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;

/**
 * 
 * Testa resgate de Task's
 * 
 * @author Jean Bacan
 * @since 08/05
 *
 */
public class GetHistoricTaskTest extends BaseIntegrationTest {

	/**
	 * Testa resgate de candidate group no getTask
	 * @throws BusinessException 
	 */
	public void testGetTaskCandidateGroup() throws BusinessException {

		GetHistoricTaskBusinessOperation listBO = new GetHistoricTaskBusinessOperation();
		listBO.setProcessEngineConfiguration(processEngineConfig);
		listBO.setSecurityHelper(helper);
		listBO.setTransformer(transformer);
		
		VivoTask task = listBO.execute("10063");
		System.out.println(task.getFormItens());
	}
}
