package br.com.vivo.bcm.business.configuration.bean;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import br.com.vivo.bcm.business.annotation.qualifiers.FileConfigurationQualifier;
import br.com.vivo.configurationutils.IConfiguration;

@Configuration
@EnableTransactionManagement
public class JPAConfig {

	public static final String JDBC_URL = "jdbc.url";
	public static final String JDBC_USER = "jdbc.user";
	public static final String JDBC_PASSWORD = "jdbc.password";
	public static final String JDBC_DRIVER = "jdbc.driver";
	public static final String JDBC_POOLSIZE_MIN = "jdbc.poolsize.min";
	public static final String JDBC_POOLSIZE_MAX = "jdbc.poolsize.max";

	@Inject
	@FileConfigurationQualifier
	private IConfiguration configuration;

	@Bean
	public JpaVendorAdapter createJpaVendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setShowSql(true);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
		return vendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "br.com.vivo.bcm.business.model" });

		JpaVendorAdapter vendorAdapter = createJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Bean
	public DataSource dataSource() throws NamingException {
		final ComboPooledDataSource dataSource = new ComboPooledDataSource();

		try {
			dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}

		dataSource.setJdbcUrl(configuration.getProperty(JDBC_URL));
		dataSource.setUser(configuration.getProperty(JDBC_USER));
		dataSource.setPassword(configuration.getProperty(JDBC_PASSWORD));
		dataSource.setMinPoolSize(Integer.valueOf(configuration.getProperty(JDBC_POOLSIZE_MIN)));
		dataSource.setMaxPoolSize(Integer.valueOf(configuration.getProperty(JDBC_POOLSIZE_MAX)));
		dataSource.setTestConnectionOnCheckin(true);
		dataSource.setAcquireIncrement(1);
		dataSource.setNumHelperThreads(10);
		dataSource.setIdleConnectionTestPeriod(300);
		dataSource.setMaxIdleTimeExcessConnections(300);
		dataSource.setPreferredTestQuery("SELECT 1 FROM DUAL");
		dataSource.setDebugUnreturnedConnectionStackTraces(true);

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "false");
		properties.setProperty("hibernate.format_sql", "false");
		// properties.setProperty("hibernate.hql.bulk_id_strategy", "br.com.vivo.logtracker.business.config.hibernate.CTEMultiTableBulkIdStrategy");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		return properties;
	}

}