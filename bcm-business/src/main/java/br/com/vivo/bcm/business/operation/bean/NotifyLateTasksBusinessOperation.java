package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

import br.com.gvt.chronus.client.ChronusJobClient;
import br.com.gvt.chronus.client.vo.bean.Execution;
import br.com.vivo.bcm.business.dto.SenderObject;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.enums.JaimailTemplateType;
import br.com.vivo.bcm.business.enums.TaskStatusType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.ErrorSendEmailException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.operation.IJaimailSenderBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;
import br.com.vivo.configurationutils.IConfiguration;
import br.com.vivo.rubeus.client.vo.UserVO;

@Named("notifyLateTasksBusinessOperation")
public class NotifyLateTasksBusinessOperation extends BaseBusinessOperation
		implements IBusinessOperation<Execution, Void> {
	private static final Logger logger = Logger.getLogger(NotifyLateTasksBusinessOperation.class);

	@Inject
	@Named("systemConfiguration")
	private IConfiguration systemConfiguration;

	@Inject
	@Named("jaimailSender")
	private IJaimailSenderBusinessOperation jaimailSender;
	
	@Override
	@Async
	public Void execute(Execution execution) throws BusinessException {

		List<UserVO> uniqueUsers = new ArrayList<UserVO>();
		List<VivoTask> vivoTasks = this.getTasksFromTaskService();
		
		for (VivoTask vivoTask : vivoTasks) {
			List<UserVO> userVOs = this.securityHelper.getUsersByGroupName(vivoTask.getCandidateGroup());
			for (UserVO user : userVOs){
				if (uniqueUsers.indexOf(user) < 0){
					uniqueUsers.add(user);
				}
			}
		}

		if (uniqueUsers != null && uniqueUsers.size() > 0){
			this.sendEmail(uniqueUsers);
			execution.setAnswerMessage("Disparado(s) " + uniqueUsers.size() + " email(s) de tarefa que vão vencer amanhã");
			ChronusJobClient.callBackSuccess(execution);
			
			logger.info("Enviando emails para " + uniqueUsers.size() + " usuários.");
			logger.debug("Enviando emails para:" + uniqueUsers);			
		}		
		logger.info("execute do NotifyLateTasksBusinessOperation - end");

		return null;
	}

	private void sendEmail(List<UserVO> userVOs) throws BusinessException {
		logger.info("Iniciando sendEmail do NotifyLateTasksBusinessOperation");

		String assunto = this.systemConfiguration.getProperty(ConfigurationType.LATE_TASK_SUBJECT.getKey());
		String emailOrigem = this.systemConfiguration.getProperty(ConfigurationType.LATE_TASK_EMAIL.getKey());
		String jaimailTemplateCode = this.systemConfiguration.getProperty(ConfigurationType.JAIMAIL_TEMPLATE_LATE_TASK.getKey());

		Map<String, Object> map = new HashMap<String, Object>();

		for (UserVO userVO : userVOs) {

			map = new HashMap<String, Object>();

			map.put(JaimailTemplateType.DESTINO.getValue(), userVO.getEmail());
			map.put(JaimailTemplateType.ORIGEM.getValue(), emailOrigem);
			map.put(JaimailTemplateType.ASSUNTO.getValue(), assunto);
			map.put(JaimailTemplateType.USER_NAME.getValue(), userVO.getName());
			logger.debug(jaimailTemplateCode);

			SenderObject senderObject = new SenderObject();
			senderObject.setParams(map);
			senderObject.setTemplateCode(jaimailTemplateCode);

			try {
				logger.info("Iniciando o  envio de Email do NotifyLateTasksBusinessOperation .");
				this.jaimailSender.execute(senderObject);
			} catch (BusinessException e) {
				logger.error("Não foi possível enviar email", e);
				throw new ErrorSendEmailException();
			}
		}

		logger.info("Término do  envio de Email do NotifyLateTasksBusinessOperation .");
	}

	/**
	 * Resgata tasks que estão á vencer amanhã
	 * 
	 * @param Void
	 * @return List<VivoTask> Tasks encontradas
	 */
	private List<VivoTask> getTasksFromTaskService() {
		List<VivoTask> taskList = new ArrayList<VivoTask>();

		String stmt = "SELECT DISTINCT I.GROUP_ID_   " + "  AS CATEGORY_   FROM ACT_RU_TASK RES  "
				+ " INNER JOIN ACT_RU_IDENTITYLINK I " + "  ON I.TASK_ID_ = RES.ID_  "
				+ " INNER JOIN ACT_HI_PROCINST P " + " ON RES.PROC_INST_ID_ = P.ID_ "
				+ "  WHERE trunc( RES.DUE_DATE_) = trunc(sysdate + 1)";

		NativeTaskQuery taskQuery = getTaskService().createNativeTaskQuery().sql(stmt);
		List<Task> tasks = taskQuery.list();

		for (Task task : tasks) {
			UserVO userVO = null;
			if (StringUtils.isNotEmpty(task.getAssignee())) {
				userVO = new UserVO();
				userVO.setUid(Long.valueOf(task.getAssignee()));
			}
			VivoTask taskVO = new VivoTask(task, TaskStatusType.OPENED, userVO);
			// Alterados campos no SELECT para reaproveitar mapeamento Task
			taskVO.setBusinessKey(task.getFormKey());
			taskVO.setCandidateGroup(task.getCategory());
			taskList.add(taskVO);
		}
		return taskList;
	}

}