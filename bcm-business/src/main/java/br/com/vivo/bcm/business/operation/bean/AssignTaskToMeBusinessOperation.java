package br.com.vivo.bcm.business.operation.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.mchange.rmi.NotAuthorizedException;

import br.com.vivo.bcm.business.dto.AssignTaskToUserDTO;
import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.NotFoundUserInGroupException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.rubeus.client.security.Principal;
import br.com.vivo.rubeus.client.vo.GroupVO;

@Named("assignTaskToMeBusinessOperation")
public class AssignTaskToMeBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String[], Void> {

	private static final Logger logger = Logger.getLogger(AssignTaskToMeBusinessOperation.class);

	@Inject
	@Named("assignTaskToUserBusinessOperation")
	private IBusinessOperation<AssignTaskToUserDTO, Void> assignTaskToUserBusinessOperation;	
	
	@Override
	public Void execute(String[] taskIds) throws BusinessException {

		if (taskIds == null || taskIds.length < 1){
			throw new BusinessException(ErrorCode.MISSING_INFORMATION);
		}
		
		logger.info("execute do AssignTaskToMeBusinessOperation - Inicio.");
				
				
		String stmt = "select distinct RES.* from ACT_RU_TASK RES WHERE RES.ID_ IN (" + StringUtils.join(taskIds, ",") + ") order by RES.ID_ asc";
		
		List<Task> tasks = getTaskService().createNativeTaskQuery().sql(stmt).list();
		for (Task task : tasks){

			List<GroupVO> loggedGroups = this.securityHelper.getLoggedGroups();
			Principal principal = (Principal) securityHelper.getLoggedPrincipal();
	
			try {
				if (task.getAssignee() != null) {
	
					if (this.securityHelper.isLoggedUserAdmin()) {
						getTaskService().unclaim(task.getId());
	
					} else {
						throw new NotAuthorizedException();
					}
				}
	
				List<IdentityLink> identityLinks = getTaskService().getIdentityLinksForTask(task.getId());
				String candidateGroup = identityLinks.get(0).getGroupId();
	
				/*
				 * Garante que o usuário de destino está no grupo da task
				 */
				boolean isGroupMember = false;
	
				for (GroupVO loggedGroup : loggedGroups) {
					if (loggedGroup.getName().equals(candidateGroup)) {
						isGroupMember = true;
						break;
					}
				}
	
				if (isGroupMember) {
					getTaskService().claim(task.getId(), principal.getUid().toString());
	
					logger.debug("User:" + "Assigned Task: " + task.getId() + " - " + task.getName() + " | Group: "
							+ candidateGroup + " to user: " + principal.getUid().toString());
				} else {
					logger.error("User:" + "Assigned Task: " + task.getId() + " - " + task.getName() + " | Group: "
							+ candidateGroup + " to user: " + principal.getUid().toString());
					throw new NotFoundUserInGroupException();
				}
	
			} catch (Exception e) {
				logger.error(e);
				throw new BusinessException(e);
			}
		}

		logger.info("execute do AssignTaskToMeBusinessOperation - Fim.");
		return null;
	}
}