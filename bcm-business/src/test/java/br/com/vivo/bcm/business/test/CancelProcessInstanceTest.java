package br.com.vivo.bcm.business.test;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

import org.junit.Test;

import br.com.vivo.bcm.business.dto.CancelProcessDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.EmptyFieldException;
import br.com.vivo.bcm.business.exception.NotAllowedException;
import br.com.vivo.bcm.business.operation.bean.CancelProcessInstanceBusinessOperation;

/**
 * 
 * Testa Cancel de process Instance
 * 
 * @author Jean Bacan
 * @since 15/05
 *
 */
public class CancelProcessInstanceTest extends BaseUnitTest {

	/**
	 * Testa cancel de process Instance com sucesso
	 * 
	 * @throws BusinessException
	 */
	@Test
	public void testCancelProcessInstance() throws BusinessException {

		CancelProcessInstanceBusinessOperation completeBO = new CancelProcessInstanceBusinessOperation();
		completeBO.setSecurityHelper(super.helper);
		completeBO.setProcessEngineConfiguration(super.processEngineConfiguration);
		
		//Setting expectations
		given(helper.isLoggedUserAdmin()).willReturn(true);
		
		completeBO.execute(new CancelProcessDTO("2773", "Just because i Can"));

		then(runtimeService).should(times(1)).deleteProcessInstance("2773", "Just because i Can");
	}
	
	/**
	 * Testa cancel de process Instance com sucesso
	 * 
	 * @throws BusinessException
	 */
	@Test(expected=NotAllowedException.class)
	public void testCancelProcessInstanceNotSAllowed() throws BusinessException {

		CancelProcessInstanceBusinessOperation completeBO = new CancelProcessInstanceBusinessOperation();
		completeBO.setSecurityHelper(super.helper);
		completeBO.setProcessEngineConfiguration(super.processEngineConfiguration);
		
		//Setting expectations
		given(helper.isLoggedUserAdmin()).willReturn(false);
		
		completeBO.execute(new CancelProcessDTO("2773", "Just because i Can"));

		then(runtimeService).should(times(0)).deleteProcessInstance(any(String.class), any(String.class));
	}
	
	/**
	 * Testa cancel de process Instance com sucesso
	 * 
	 * @throws BusinessException
	 */
	@Test(expected=EmptyFieldException.class)
	public void testCancelProcessInstanceNoParameters() throws BusinessException {

		CancelProcessInstanceBusinessOperation completeBO = new CancelProcessInstanceBusinessOperation();
		completeBO.setSecurityHelper(super.helper);
		completeBO.setProcessEngineConfiguration(super.processEngineConfiguration);
		
		//Setting expectations
		given(helper.isLoggedUserAdmin()).willReturn(true);
		
		completeBO.execute(null);

		then(runtimeService).should(times(0)).deleteProcessInstance(any(String.class), any(String.class));
	}
}
