package br.com.vivo.bcm.business.test;

import static org.mockito.Mockito.when;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.vivo.bcm.business.configuration.bean.ProcessEngineConfiguration;
import br.com.vivo.bcm.business.helper.bean.SecurityHelper;

/**
 * Pre configuration for Activiti unit tests
 * @author Jean Bacan
 *
 */
public class BaseUnitTest {

	@Mock
	protected SecurityHelper helper;

	@Mock
	protected FormService formService;

	@Mock
	protected TaskService taskService;
	
	@Mock
	protected HistoryService historyService;
	
	@Mock
	protected RuntimeService runtimeService;
	
	@Mock
	protected IdentityService identityService;

	@Mock
	protected ManagementService managementService;
	
	protected ProcessEngineConfiguration processEngineConfiguration = new ProcessEngineConfiguration();
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Before
	public void before(){
		
		//Defining a mocked processEngine to our app Config
		ProcessEngine processEngine = Mockito.mock(ProcessEngine.class);
		
		processEngineConfiguration.setProcessEngine(processEngine);
		
		//defining mocking return for each service avaiable in activiti engine
		when(processEngine.getTaskService()).thenReturn(taskService);
		when(processEngine.getFormService()).thenReturn(formService);
		when(processEngine.getRuntimeService()).thenReturn(runtimeService);
		when(processEngine.getHistoryService()).thenReturn(historyService);
		when(processEngine.getIdentityService()).thenReturn(identityService);
		when(processEngine.getManagementService()).thenReturn(managementService);
	}
	
	
}
