package br.com.vivo.bcm.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.vivo.rubeus.client.auth.JWTInterceptor;
import br.com.vivo.rubeus.client.auth.RolesInterceptor;

/**
 * Classe para configurações
 * 
 * @author G0029875
 */
@Configuration()
@ComponentScan(basePackages = { "br.com.vivo.bcm.rest.config", "br.com.vivo.bcm.business.configuration.bean", "br.com.vivo.bcm.rest.filter"})
public class RootConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private JWTInterceptor jwtInterceptor;

	@Autowired
	private RolesInterceptor rolesInterceptor;

	@Bean
	public JWTInterceptor createJWTInterceptor() {
		return new JWTInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor);
		registry.addInterceptor(rolesInterceptor);
	}
}