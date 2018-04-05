package br.com.vivo.bcm.business.test.integration;

import java.util.List;

import org.junit.Test;

import br.com.vivo.bcm.business.dto.form.transformer.FormTransformerFactory;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.GetProcessInstanceDetailBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;

/**
 * 
 * Testa Form de start
 * 
 * @author Jean Bacan
 * @since 29/05
 *
 */
public class GetProcessDetailTest extends BaseIntegrationTest {

	public void testGetProcessInstanceDetail() throws BusinessException {

		GetProcessInstanceDetailBusinessOperation bo = new GetProcessInstanceDetailBusinessOperation();
		bo.setProcessEngineConfiguration(processEngineConfig);
		bo.setSecurityHelper(helper);
		bo.setTransformer(new FormTransformerFactory());

		List<VivoTask> task = bo.execute("ANALISE_92_52017");
		System.out.println(task);
	}
}
