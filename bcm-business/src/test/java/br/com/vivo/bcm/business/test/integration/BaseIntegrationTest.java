package br.com.vivo.bcm.business.test.integration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import br.com.vivo.bcm.activiti.formType.CheckListFormType;
import br.com.vivo.bcm.activiti.formType.ComboBoxFormType;
import br.com.vivo.bcm.activiti.formType.DocumentFormType;
import br.com.vivo.bcm.business.configuration.bean.ProcessEngineConfiguration;
import br.com.vivo.bcm.business.dto.form.transformer.FormTransformerFactory;
import br.com.vivo.bcm.business.helper.bean.SecurityHelper;

/**
 * Categorizing integration tests to run at integration phase (mvn verify) 
 * @author G0054687
 *
 */
@Category(IIntegrationTest.class)
public class BaseIntegrationTest {

	@Mock
	SecurityHelper helper;
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	protected FormTransformerFactory transformer = new FormTransformerFactory();
	
	protected ProcessEngineConfiguration processEngineConfig = new ProcessEngineConfiguration();

	private ProcessEngine processEngine;
	
	@Before
	public void createMocks() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);

		processEngine = createProcessEngine();
		
		processEngineConfig.setProcessEngine(processEngine);
	}

	public ProcessEngine createProcessEngine() {
		DataSource dataSource = createActivitiDataSource();
		DataSourceTransactionManager dataSourceTransactionManager = createProcessEngineDataSourceTransactionManager(dataSource);
		return createProcessEngine(dataSource, dataSourceTransactionManager);
	}

	public DataSourceTransactionManager createProcessEngineDataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource);
		return txManager;
	}

	public ProcessEngine createProcessEngine(DataSource dataSource, DataSourceTransactionManager txManager) {
		SpringProcessEngineConfiguration speConfiguration = new SpringProcessEngineConfiguration();
		speConfiguration.setDataSource(dataSource);
		speConfiguration.setTransactionManager(txManager);
		speConfiguration.setDatabaseSchemaUpdate("false");
		speConfiguration.setJobExecutorActivate(false);

		List<AbstractFormType> customForms = new ArrayList<AbstractFormType>();
		customForms.add(new DocumentFormType());
		customForms.add(new ComboBoxFormType());
		customForms.add(new CheckListFormType());
		
		speConfiguration.setCustomFormTypes(customForms);
		return speConfiguration.buildProcessEngine();
	}

	public void importModel() throws FileNotFoundException {
		processEngine.getRepositoryService().createDeployment().addClasspathResource("ExecucaoBC.bpmn").deploy();
	}

	public DataSource createActivitiDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setDefaultAutoCommit(false);

		// HOMOLOGACAO
		// dataSource.setUrl("jdbc:oracle:thin:@svuxhpop1:1521:dpop");
		// dataSource.setUsername("RACK_FLOW_OWNER");
		// dataSource.setPassword("BF7727#I");

		// DEV_USER_OWNER_14
		dataSource.setUrl("jdbc:oracle:thin:@pvoracle1-1dev.popvono:1521:dpopt");
		dataSource.setUsername("DEV_USER_OWNER_07");
		dataSource.setPassword("UU5544_B");

		// DESENVOLVIMENTO
		// dataSource.setUrl("jdbc:oracle:thin:@pvoracle1dev:1521:endvono");
		// dataSource.setUsername("RACK_FLOW_OWNER");
		// dataSource.setPassword("CR2223_I");

		return dataSource;
	}
}
