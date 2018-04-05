package br.com.vivo.bcm.rest.resources;

import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.dto.CountEsteiraGroupDTO;
import br.com.vivo.bcm.business.dto.CountTasksDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.EsteiraCountType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.rest.base.BaseRestService;

@Controller
@RequestMapping(value = BaseRestService.PATH_COUNT)
public class CountRestService {

	@Inject
	@Named("countTasksBusinessOperation")
	private IBusinessOperation<FilterDTO, CountTasksDTO> countTasksBusinessOperation;

	@Inject
	@Named("countEsteiraGroupBusinessOperation")
	private IBusinessOperation<FilterDTO, Map<EsteiraCountType, CountEsteiraGroupDTO>> countEsteiraGroupBusinessOperation;
	
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_TASK, method = RequestMethod.POST)
	public CountTasksDTO countTasks(@RequestBody FilterDTO filterDTO) throws BusinessException {
		return this.countTasksBusinessOperation.execute(filterDTO);
	}

	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_GROUPS, method = RequestMethod.POST)
	public Map<EsteiraCountType, CountEsteiraGroupDTO> countEsteiraGroup(@RequestBody FilterDTO filterDTO) throws BusinessException {
		return this.countEsteiraGroupBusinessOperation.execute(filterDTO);
	}
}
