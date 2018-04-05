package br.com.vivo.bcm.business.operation.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.mchange.rmi.NotAuthorizedException;

import br.com.vivo.bcm.business.dto.AssignTaskToUserDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.NotFoundUserInGroupException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.rubeus.client.security.Principal;
import br.com.vivo.rubeus.client.vo.GroupVO;
import br.com.vivo.rubeus.client.vo.UserVO;

@Named("assignTaskToUserBusinessOperation")
public class AssignTaskToUserBusinessOperation extends BaseBusinessOperation
		implements IBusinessOperation<AssignTaskToUserDTO, Void> {

	private static final Logger logger = Logger.getLogger(AssignTaskToUserBusinessOperation.class);

	@Inject
	@Named("unassignTaskBusinessOperation")
	private IBusinessOperation<String, Void> unassignTaskBusinessOperation;

	@Override
	@Transactional
	public Void execute(AssignTaskToUserDTO assignTaskToUserDTO) throws BusinessException {

		logger.info("execute do AssignTaskToUserBusinessOperation.");

		try {

			Principal principal = (Principal) this.securityHelper.getLoggedPrincipal();

			UserVO userVO = this.securityHelper.findUserById(Long.valueOf(assignTaskToUserDTO.getUserId()));

			List<GroupVO> assigneeGroups = this.securityHelper.getGroupsByUserId(userVO.getUid());

			Task task = getTaskService().createTaskQuery().taskId(assignTaskToUserDTO.getTaskId().trim()).singleResult();

			if (task.getAssignee() != null) {

				boolean hasTaskPermition = task.getAssignee().equals(String.valueOf(principal.getUid()))
						|| this.securityHelper.isLoggedUserAdmin();

				/*
				 * Valida se o operador é dono ou tem permissão para realizar o
				 * unassign
				 */
				if (hasTaskPermition) {
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
			GroupVO groupVO = this.securityHelper.getLoggedGroupByCadidateGroup(candidateGroup);
			for (GroupVO assigneeGroup : assigneeGroups) {
				
				if (assigneeGroup.getUid().equals(groupVO.getUid())) {
					isGroupMember = true;
					break;
				}
			}

			if (isGroupMember) {
				getTaskService().claim(assignTaskToUserDTO.getTaskId().trim(), assignTaskToUserDTO.getUserId());

				logger.debug("User:" + "Assigned Task: " + task.getId() + " - " + task.getName() + " | Group: "
						+ candidateGroup + " to user: " + assignTaskToUserDTO.getUserId());
			} else {
				logger.error("User:" + "Assigned Task: " + task.getId() + " - " + task.getName() + " | Group: "
						+ candidateGroup + " to user: " + assignTaskToUserDTO.getUserId());
				throw new NotFoundUserInGroupException();
			}

		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
		return null;
	}
}
