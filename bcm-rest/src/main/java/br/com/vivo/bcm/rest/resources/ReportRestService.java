package br.com.vivo.bcm.rest.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.rest.base.BaseRestService;

@Controller
@RequestMapping(value = BaseRestService.PATH_REPORT)
public class ReportRestService {

	@Inject
	@Named("downloadReportBusinessOperation")
	private IBusinessOperation<FilterDTO, String> downloadReportBusinessOperation;

	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@RequestMapping(method = RequestMethod.POST)
	public Response downloadReportComplete(@RequestBody FilterDTO filterDTO) throws BusinessException {
		String result = this.downloadReportBusinessOperation.execute(filterDTO);
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

}