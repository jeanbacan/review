package br.com.vivo.bcm.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configurações para endpoints
 * 
 * @author G0029875
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { 
		"br.com.vivo.bcm.rest.resources", 
		"br.com.vivo.bcm.rest.exception",
		"br.com.vivo.rubeus.client.resources"})
public class WebConfig extends WebMvcConfigurerAdapter {
}