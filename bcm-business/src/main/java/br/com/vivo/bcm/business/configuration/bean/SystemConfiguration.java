package br.com.vivo.bcm.business.configuration.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import br.com.vivo.bcm.business.annotation.qualifiers.SystemConfigurationQualifier;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Configuration;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.configurationutils.IConfiguration;
import br.com.vivo.configurationutils.IConfigurationType;
import br.com.vivo.rubeus.client.annotation.RubeusConfiguration;

@RubeusConfiguration
@Singleton
@Component
@SystemConfigurationQualifier
@Named("systemConfiguration")
public class SystemConfiguration implements IConfiguration {

	private static Logger logger = Logger.getLogger(SystemConfiguration.class);
	
	/** Separador dos itens de uma lista. */
	public static final String SEPARATOR = ",";

	@Inject
	@Named("listConfigurationsBusinessOperation")
	private IBusinessOperation<Void, List<Configuration>> configurationBO;
	
	/** Propriedades do sistema **/
	private Properties properties = new Properties();

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.communicator.business.util.IConfiguration#getProperty(java.lang.String)
	 */
	public String getProperty(String name) {
		return this.properties.getProperty(name);
	}

	@Override
	public String getProperty(IConfigurationType configurationType) {
		return this.properties.getProperty(configurationType.getKey());
	}

	@Override
	@PostConstruct
	public void init() {
		logger.info("Carregando configurações");

		properties = new Properties();

		List<Configuration> configs = new ArrayList<Configuration>();
		try {
			configs = configurationBO.execute(null);
		} catch (BusinessException e) {
			logger.error(e);
		}

		for (Configuration config : configs) {
			properties.put(config.getKey(), config.getValue());
		}
		logger.info("Configurações carregadas.");

	}

	@Override
	public void reload() {
		init();
	}
}
