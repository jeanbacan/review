/**
 * 
 */
package br.com.vivo.bcm.rest.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IDataExtractor;
import br.com.vivo.bcm.rest.base.BaseRestService;

/**
 * 
 * Interface de solicitação de extração de arquivos externos.
 * @author Jean Bacan
 *
 */
@Controller
@RequestMapping(value = BaseRestService.PATH_EXTRACT)
public class ExtractFileRestService {

	@Inject
	@Named("armarioBusinessExtractor")
	private IDataExtractor armarioBusinessExtractor;

	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_ARMARIO, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public Void extractArmario(@QueryParam(BaseRestService.PARAM_TOKEN) String token) throws BusinessException {
		return this.armarioBusinessExtractor.execute(null);
	}
}
