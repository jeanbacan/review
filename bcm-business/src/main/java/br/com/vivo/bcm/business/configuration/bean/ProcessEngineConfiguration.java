package br.com.vivo.bcm.business.configuration.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import br.com.vivo.bcm.activiti.formType.CheckListFormType;
import br.com.vivo.bcm.activiti.formType.ComboBoxFormType;
import br.com.vivo.bcm.activiti.formType.DocumentFormType;
import br.com.vivo.configurationutils.IConfiguration;

@Singleton
@Named("processEngineConfiguration")
public class ProcessEngineConfiguration {

	private ProcessEngine processEngine = null;

	private DataSource dataSource = null;

	@Inject
	@Named("systemConfiguration")
	private IConfiguration systemConfiguration;

	@PostConstruct
	public void load() {
		this.dataSource = this.createActivitiDataSource();
		DataSourceTransactionManager dataSourceTransactionManager = this.createProcessEngineDataSourceTransactionManager(this.dataSource);
		this.processEngine = this.createProcessEngine(this.dataSource, dataSourceTransactionManager);
	}

	private DataSource createActivitiDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(this.systemConfiguration.getProperty("activiti.jdbc.driver"));
		dataSource.setUrl(this.systemConfiguration.getProperty("activiti.jdbc.url"));
		dataSource.setUsername(this.systemConfiguration.getProperty("activiti.jdbc.username"));
		dataSource.setPassword(this.systemConfiguration.getProperty("activiti.jdbc.password"));
		dataSource.setDefaultAutoCommit(true);

		return dataSource;
	}

	private DataSourceTransactionManager createProcessEngineDataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}

	private ProcessEngine createProcessEngine(DataSource dataSource, DataSourceTransactionManager dataSourceTransactionManager) {
		SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
		springProcessEngineConfiguration.setDataSource(dataSource);
		springProcessEngineConfiguration.setTransactionManager(dataSourceTransactionManager);
		springProcessEngineConfiguration.setDatabaseSchemaUpdate("false");
		springProcessEngineConfiguration.setJobExecutorActivate(false);
		springProcessEngineConfiguration.setBulkInsertEnabled(false);

		List<AbstractFormType> customForms = new ArrayList<AbstractFormType>();
		customForms.add(new DocumentFormType());
		customForms.add(new ComboBoxFormType());
		customForms.add(new CheckListFormType());
		
		List<String> customMybatis = new ArrayList<String>();
		customMybatis.add("br.com.vivo.bcm.activiti.mapper.ReportCompleteMapper");
		customMybatis.add("br.com.vivo.bcm.activiti.mapper.NewTaskMailMapper");
		customMybatis.add("br.com.vivo.bcm.activiti.mapper.HistoricTasksMapper");
		customMybatis.add("br.com.vivo.bcm.activiti.mapper.RuntimeTasksMapper");
		customMybatis.add("br.com.vivo.bcm.activiti.mapper.CountEsteiraGroupMapper");

		springProcessEngineConfiguration.setCustomFormTypes(customForms);
		springProcessEngineConfiguration.setCustomMybatisMappers(getCustomMybatisMapperClasses(customMybatis));

		return springProcessEngineConfiguration.buildProcessEngine();
	}

	@SuppressWarnings("rawtypes")
	private Set<Class<?>> getCustomMybatisMapperClasses(List<String> customMyBatisMappers) {
		Set<Class<?>> mybatisMappers = new HashSet<Class<?>>();
		for (String customMybatisMapperClassName : customMyBatisMappers) {
			try {
				Class customMybatisClass = Class.forName(customMybatisMapperClassName);
				mybatisMappers.add(customMybatisClass);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Class " + customMybatisMapperClassName + " has not been found.", e);
			}
		}
		return mybatisMappers;
	}

	public ProcessEngine getProcessEngine() {
		return this.processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
}
