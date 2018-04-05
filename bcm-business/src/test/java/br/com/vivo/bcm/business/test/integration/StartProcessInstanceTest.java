package br.com.vivo.bcm.business.test.integration;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.vivo.bcm.business.dto.ProcessInstanceStartDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.bean.SaveActivitiUserBusinessOperation;
import br.com.vivo.bcm.business.operation.bean.StartProcessInstanceBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;
import br.com.vivo.rubeus.client.security.IPrincipal;
import br.com.vivo.rubeus.client.security.PrincipalAuthorization;

/**
 * 
 * Testa abrir form para process
 * 
 * @author Jean Bacan
 * @since 18/05
 *
 */
public class StartProcessInstanceTest extends BaseIntegrationTest {

	@Mock
	SaveActivitiUserBusinessOperation saveUserBO;
	
	public void testGetProcessStarterForm() throws BusinessException {
		StartProcessInstanceBusinessOperation bo = new StartProcessInstanceBusinessOperation();
		bo.setProcessEngineConfiguration(processEngineConfig);
		bo.setSecurityHelper(helper);
		bo.setSaveActivitiUser(saveUserBO);

		Mockito.when(helper.getLoggedStartProcessCandidateGroup()).thenReturn("B2C");
		Mockito.when(helper.getLoggedPrincipal()).thenReturn(new IPrincipal() {
			@Override
			public String getUsername() {
				return "jeanbacan";
			}
			@Override
			public Long getUid() {
				return 1L;
			}
			@Override
			public List<PrincipalAuthorization> getAuthorizations() {
				return null;
			}
		});
		
		ProcessDefinitionQuery query = processEngineConfig.getProcessEngine().getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> definitions = query.list();

		String processDef = "";
		for (ProcessDefinition pd : definitions) {
			processDef = pd.getId();
			break;
		}
		List<VivoTaskFormItem> formitems = new ArrayList<VivoTaskFormItem>();
		formitems.add(new VivoTaskFormItem("diretoria", "idSul"));
		formitems.add(new VivoTaskFormItem("uf", "idParana"));
		formitems.add(new VivoTaskFormItem("cidade", "idCuritiba"));
		formitems.add(new VivoTaskFormItem("tipoTecnologia", "idFibra"));
		formitems.add(new VivoTaskFormItem("tipoInvestimento", "idAlivio"));
		
		ProcessInstanceStartDTO dto = new ProcessInstanceStartDTO();
		dto.setProcessDefinitionId(processDef);
		dto.setFormItems(formitems);
		bo.execute(dto);
		
	}
}
