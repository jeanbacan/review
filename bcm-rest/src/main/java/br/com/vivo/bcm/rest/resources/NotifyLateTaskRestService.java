package br.com.vivo.bcm.rest.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.gvt.chronus.client.vo.bean.Execution;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.rest.base.BaseRestService;

@Controller
@RequestMapping(value = BaseRestService.PATH_NOTIFY)
public class NotifyLateTaskRestService {

	@Inject
	@Named("notifyLateTasksBusinessOperation")
	private IBusinessOperation<Execution, Void> notifyLateTasksBusinessOperation;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public Void list(@RequestBody Execution execution) throws BusinessException {
		return this.notifyLateTasksBusinessOperation.execute(execution);
	}


}