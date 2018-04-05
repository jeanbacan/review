/**
 * 
 */
package br.com.vivo.bcm.rest.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxTaskValueDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation;
import br.com.vivo.bcm.rest.base.BaseRestService;

/**
 * @author A0051460
 *
 */
@Controller
@RequestMapping(value = BaseRestService.PATH_COMBO)
public class ComboBoxRestService {

	@Autowired
	private ApplicationContext applicationContext;

	@Inject
	@Named("listUFDSBusinessOperation")
	private IBusinessOperation<ComboBoxDTO, List<ComboBoxValueDTO>>  listUFDSBusinessOperation;
	
	@Inject
	@Named("listTasksDSBusinessOperation")
	private IBusinessOperation<ComboBoxDTO, List<ComboBoxTaskValueDTO>>  listTasksDSBusinessOperation;
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(method = RequestMethod.GET)
	public List<ComboBoxValueDTO> listAllUfs()throws BusinessException {
		return this.listUFDSBusinessOperation.execute(null);
	}
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_PROGRESS, method = RequestMethod.GET)
	public List<ComboBoxTaskValueDTO> listAllProgress()throws BusinessException {
		return this.listTasksDSBusinessOperation.execute(null);
	}
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(method = RequestMethod.POST)
	public List<ComboBoxValueDTO> list(@RequestBody ComboBoxDTO comboBoxDTO)throws BusinessException {
		
		IDataSourceBusinessOperation dsBO = (IDataSourceBusinessOperation) applicationContext.getBean(comboBoxDTO.getDataSourceName());
		return dsBO.execute(comboBoxDTO);
	}
	
}
