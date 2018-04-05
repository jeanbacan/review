package br.com.vivo.bcm.rest.resources;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.rest.base.BaseRestService;
import br.com.vivo.configurationutils.IConfiguration;

@Controller
@RequestMapping(value = BaseRestService.PATH_CONFIGURATION)
public class ConfigurationRestService {

	@Inject
	@Named("systemConfiguration")
	private IConfiguration systemConfiguration;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public void refreshConfiguration() {
		this.systemConfiguration.reload();
	}
}