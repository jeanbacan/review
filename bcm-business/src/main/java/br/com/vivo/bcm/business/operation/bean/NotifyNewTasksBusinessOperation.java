package br.com.vivo.bcm.business.operation.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

import br.com.vivo.bcm.activiti.execution.NewTaskMailSQLExecution;
import br.com.vivo.bcm.activiti.mapper.NewTaskMailMapper;
import br.com.vivo.bcm.activiti.query.NewTaskMailQuery;
import br.com.vivo.bcm.business.dto.SenderObject;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.enums.JaimailTemplateType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.ErrorSendEmailException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.operation.IJaimailSenderBusinessOperation;
import br.com.vivo.bcm.business.util.ComboDescriptions;
import br.com.vivo.configurationutils.IConfiguration;
import br.com.vivo.rubeus.client.vo.GroupVO;
import br.com.vivo.rubeus.client.vo.UserVO;

@Named("notifyNewTasksBusinessOperation")
public class NotifyNewTasksBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String, Void> {

	private static final Logger logger = Logger.getLogger(NotifyNewTasksBusinessOperation.class);

	@Inject
	@Named("systemConfiguration")
	private IConfiguration systemConfiguration;

	@Inject
	@Named("jaimailSender")
	private IJaimailSenderBusinessOperation jaimailSender;
	
	@Override
	@Async
	public Void execute(String businessKey) throws BusinessException {

		logger.info("execute do NotifyNewTasksBusinessOperation - begin");
		
		NewTaskMailSQLExecution customSqlExecution = new NewTaskMailSQLExecution(businessKey, NewTaskMailMapper.class);
    	NewTaskMailQuery createdTask = getManagementService().executeCustomSql(customSqlExecution);
		
    	//Resgata todos os usuaários do grupo da tarefa criada
		List<UserVO> usersToMail = new ArrayList<UserVO>();
		List<UserVO> userVOs = this.securityHelper.getUsersByGroupName(createdTask.getTaskGroup());

		for (UserVO user : userVOs){
			if (usersToMail.indexOf(user) < 0){
				usersToMail.add(user);
			}
		}

		if (usersToMail != null && usersToMail.size() > 0){
			
			createdTask.setTipoInvestimento(ComboDescriptions.getTipoInvestimentoDescription(createdTask.getTipoInvestimento()));
			createdTask.setTipoTecnologia(ComboDescriptions.getTipoTecnologiaDescription(createdTask.getTipoTecnologia()));
			createdTask.setTipoObra(ComboDescriptions.getTipoObraDescription(createdTask.getTipoObra()));
			
			//Atualiza nome do grupo de tarefa de (KEY) para (Descrição)
			List<GroupVO> groups = this.securityHelper.getGroupsByUserId(usersToMail.get(0).getUid());
			for (GroupVO group : groups){
				if (group.getName().equals(createdTask.getTaskGroup())){
					createdTask.setTaskGroup(group.getDescription());
				}
			}			
			
			this.sendEmail(createdTask, usersToMail);
			
			logger.info("Enviando emails para " + usersToMail.size() + " usuários.");
			logger.debug("Enviando emails para:" + usersToMail);			
		}		
		logger.info("execute do NotifyNewTasksBusinessOperation - end");

		return null;
	}

	private void sendEmail(NewTaskMailQuery createdTask, List<UserVO> userVOs) throws BusinessException {

		String assunto = this.systemConfiguration.getProperty(ConfigurationType.NEW_TASK_SUBJECT.getKey());
		assunto = assunto.replace("$businessKey", createdTask.getBusinessKey());
		
		String emailOrigem = this.systemConfiguration.getProperty(ConfigurationType.NEW_TASK_EMAIL.getKey());
		String jaimailTemplateCode = this.systemConfiguration.getProperty(ConfigurationType.NEW_TASK_TEMPLATE.getKey());

		logger.debug(jaimailTemplateCode);
		
		Map<String, Object> map = new HashMap<String, Object>();

		for (UserVO userVO : userVOs) {

			map = new HashMap<String, Object>();

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");		
			
			map.put(JaimailTemplateType.DESTINO.getValue(), userVO.getEmail());
			map.put(JaimailTemplateType.ORIGEM.getValue(), emailOrigem);
			map.put(JaimailTemplateType.ASSUNTO.getValue(), assunto);
			map.put(JaimailTemplateType.USER_NAME.getValue(), userVO.getName());
			map.put(JaimailTemplateType.BUSINESS_KEY.getValue(), createdTask.getBusinessKey());
			map.put(JaimailTemplateType.TASK_NAME.getValue(), createdTask.getTaskName());
			map.put(JaimailTemplateType.DUE_DATE.getValue(), format.format(createdTask.getTaskDueDate()));
			map.put(JaimailTemplateType.ID_USER_GROUP.getValue(), createdTask.getOwnerGroup());
			map.put(JaimailTemplateType.DIRETORIA.getValue(), createdTask.getDiretoria());
			map.put(JaimailTemplateType.UF.getValue(), createdTask.getUf());
			map.put(JaimailTemplateType.CIDADE.getValue(), createdTask.getCidade());
			map.put(JaimailTemplateType.TIPO_INVESTIMENTO.getValue(), createdTask.getTipoInvestimento());
			map.put(JaimailTemplateType.TIPO_TECNOLOGIA.getValue(), createdTask.getTipoTecnologia());
			map.put(JaimailTemplateType.TIPO_OBRA.getValue(), createdTask.getTipoObra());
			map.put(JaimailTemplateType.OWNER.getValue(), createdTask.getOwner());

			SenderObject senderObject = new SenderObject();
			senderObject.setParams(map);
			senderObject.setTemplateCode(jaimailTemplateCode);

			try {
				logger.info("Iniciando o  envio de Email do NotifyLateTasksBusinessOperation para o destinatário: "+ userVO.getEmail());
				this.jaimailSender.execute(senderObject);
			} catch (BusinessException e) {
				logger.error("Não foi possível enviar email", e);
				throw new ErrorSendEmailException();
			}
		}
	}	

}