package br.com.vivo.bcm.business.configuration.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.springframework.stereotype.Component;

import br.com.vivo.bcm.business.annotation.qualifiers.FileConfigurationQualifier;
import br.com.vivo.configurationutils.IConfiguration;
import br.com.vivo.configurationutils.IConfigurationType;

/**
 * Implementacao para configuracoes com base em arquivo de propriedades
 * 
 */
@Component
@Singleton
@FileConfigurationQualifier
public class FileConfiguration implements IConfiguration {
	
	private final String PROPERTIES_FILE_PATH = "/home/app/bcm/config/config.properties";

	private Properties properties;

	@Override
	@PostConstruct
	public void init() {
		this.properties = new Properties();
		try {
			this.properties.load(new FileInputStream(PROPERTIES_FILE_PATH));
		} catch (IOException e) {
			throw new RuntimeException("Nao foi possivel carregar os dados do arquivo de propriedade.", e);
		}
	}

	@Override
	public String getProperty(String name) {
		return this.properties.getProperty(name);
	}

	@Override
	public String getProperty(IConfigurationType configurationType) {
		return this.properties.getProperty(configurationType.getKey());
	}

	@Override
	public void reload() {
		this.init();
	}
}