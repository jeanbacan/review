package br.com.vivo.bcm.business.test.integration;

import org.junit.Test;

import br.com.vivo.bcm.business.dto.ProcessInstanceStartDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.GetProcessStartFormBusinessOperation;

/**
 * 
 * Testa Form de start
 * 
 * @author Jean Bacan
 * @since 29/05
 *
 */
public class GetProcessStarterFormTest extends BaseIntegrationTest {

	public void testGetProcessStarterForm() throws BusinessException {

		GetProcessStartFormBusinessOperation bo = new GetProcessStartFormBusinessOperation();
		bo.setProcessEngineConfiguration(processEngineConfig);
		bo.setSecurityHelper(helper);
		
		ProcessInstanceStartDTO task = bo.execute("");
		System.out.println(task.getFormItems());
	}
}
