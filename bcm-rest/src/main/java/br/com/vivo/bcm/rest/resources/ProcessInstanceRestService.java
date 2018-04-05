package br.com.vivo.bcm.rest.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.dto.CancelProcessDTO;
import br.com.vivo.bcm.business.dto.ProcessInstanceStartDTO;
import br.com.vivo.bcm.business.dto.ResultPageDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;
import br.com.vivo.bcm.rest.base.BaseRestService;

@Controller
@RequestMapping(value = BaseRestService.PATH_PROCESS_INSTANCE)
public class ProcessInstanceRestService {

	@Inject
	@Named("listProcessInstancesBusinessOperation")
	private IBusinessOperation<FilterDTO, ResultPageDTO> listProcessInstancesBusinessOperation;

	@Inject
	@Named("cancelProcessInstanceBusinessOperation")
	private IBusinessOperation<CancelProcessDTO, Void> cancelProcessInstanceBusinessOperation;

	@Inject
	@Named("startProcessInstanceBusinessOperation")
	private IBusinessOperation<ProcessInstanceStartDTO, Void> startProcessInstanceBusinessOperation;

	@Inject
	@Named("getProcessInstanceDetail")
	private IBusinessOperation<String, List<VivoTask>> getProcessInstanceDetail;

	@PermitAll
	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_PROCESS_INSTANCE_LIST, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResultPageDTO list(@RequestBody FilterDTO filterDTO) throws BusinessException {
		return this.listProcessInstancesBusinessOperation.execute(filterDTO);
	}

	@PermitAll
	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_CANCEL, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public Void cancel(@RequestBody CancelProcessDTO cancelProcessDTO) throws BusinessException {
		return this.cancelProcessInstanceBusinessOperation.execute(cancelProcessDTO);
	}

	@PermitAll
	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_SAVE_INSTANCE, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public Void saveInstance(@RequestBody ProcessInstanceStartDTO processInstanceStartDTO) throws BusinessException {
		return this.startProcessInstanceBusinessOperation.execute(processInstanceStartDTO);
	}

	
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_PARAM_PROCESS_INSTANCE_ID, method = RequestMethod.GET)
	public List<VivoTask> getProcessInstanceDetail(@PathParam(BaseRestService.PARAM_PROCESS_INSTANCE_ID) String processInstanceId) throws BusinessException {
		return this.getProcessInstanceDetail.execute(processInstanceId);
	}
	

}
