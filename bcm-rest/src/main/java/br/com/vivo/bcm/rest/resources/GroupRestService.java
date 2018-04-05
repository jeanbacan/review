/**
 * 
 */
package br.com.vivo.bcm.rest.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.rest.base.BaseRestService;
import br.com.vivo.rubeus.client.vo.GroupVO;
import br.com.vivo.rubeus.client.vo.UserVO;

/**
 * @author A0051460
 *
 */
@Controller
@RequestMapping(value = BaseRestService.PATH_GROUPS)
public class GroupRestService {

	private static final Logger logger = Logger.getLogger(GroupRestService.class);

	@Inject
	@Named("listGroupBusinessOperation")
	private IBusinessOperation<Void, List<GroupVO>> listGroupBusinessOperation;

	@Inject
	@Named("listUserByGroupBusinessOperation")
	private IBusinessOperation<String, List<UserVO>> listUserByGroupBusinessOperation;
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(method = RequestMethod.GET)
	public List<GroupVO> listGroup()throws BusinessException {
		logger.info("listGroup do ListInvestimentGroupRestService - Inicio");

		List<GroupVO> result = this.listGroupBusinessOperation.execute(null);

		logger.info("listGroup do ListInvestimentGroupRestService - FIM");
		return result;
	}

	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_USERS, method = RequestMethod.GET)
	public List<UserVO> listByGroupId(@PathParam(BaseRestService.PARAM_CANDIDATE_GRUP) String candidateGroup)throws BusinessException {
		logger.info("listByGroupId do ListUserGroupsRestService - Inicio");

		List<UserVO> result = this.listUserByGroupBusinessOperation.execute(candidateGroup);

		logger.info("listByGroupId do ListUserGroupsRestService - FIM");
		return result;
	}
}

	
	
