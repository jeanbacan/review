package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Named;

import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.exception.UnassignNotAllowedException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;

@Named("unassignTaskBusinessOperation")
public class UnassignTaskBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String, Void> {

	private static final Logger logger = Logger.getLogger(UnassignTaskBusinessOperation.class);

	@Override
	public Void execute(String taskId) throws UnassignNotAllowedException {
		logger.info("execute do UnassignTaskBusinessOperation .");

		Long userId = super.securityHelper.getLoggedPrincipal().getUid();

		Task task = getTaskService().createTaskQuery().taskId(taskId).singleResult();

		// Valida se usuario logado é owner da tarefa
		if ((task != null && userId.toString().equals(task.getAssignee())) || this.securityHelper.isLoggedUserAdmin()) {

			getTaskService().unclaim(taskId.trim());
		} else {
			throw new UnassignNotAllowedException();
		}

		logger.debug("execute do UnassignTaskBusinessOperation . user / task: " + userId + " / " + task.getId());

		return null;

	}
}
