package br.com.vivo.bcm.business.configuration.bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author G0029875
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = { 
		"br.com.vivo.bcm.business.operation.bean",
		"br.com.vivo.bcm.business.operation.datasource.bean",
		"br.com.vivo.bcm.business.operation.extractor.bean",
		"br.com.vivo.bcm.business.transformer.bean",
		"br.com.vivo.bcm.business.builder.bean",
		"br.com.vivo.bcm.business.dao.bean",
		"br.com.vivo.bcm.business.configuration.bean",
		"br.com.vivo.bcm.business.dto.form.transformer",
		"br.com.vivo.rubeus.client.config",
		"br.com.vivo.bcm.business.helper.bean"})
public class JDBConfig {
}