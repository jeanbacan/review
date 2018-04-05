package br.com.vivo.bcm.business.vo;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vivo.bcm.activiti.query.KanbanTaskQuery;
import br.com.vivo.bcm.business.enums.TaskStatusType;
import br.com.vivo.bcm.business.util.ComboDescriptions;
import br.com.vivo.rubeus.client.vo.UserVO;

/*
 * @author A0051460
 */

@JsonInclude(value = Include.NON_NULL)
public class VivoTask {

	private String name;

	private Date dueDate;

	private String taskId;

	private Date createTime;
	
	/**
	 * Usuário atual responsável pela tarefa
	 */
	private String assignee;
	
	private Date createProcessTime;
	
	private Date completedTime;

	/**
	 * Usuário que solicitou a demanda
	 */
	private VivoTaskUser owner;

	private String description;

	private long lateDays;

	private String businessKey;

	private TaskStatusType status;

	private String candidateGroup;
	
	private String candidateGroupDescription;

	private String processInstanceId;

	private List<VivoTaskFormItem> formItens;
	
	private List<VivoTaskFormItem> headers;
	
	private Map<String, String> processVariables;
	
	public VivoTask(Task task, TaskStatusType taskStatusType, UserVO userVO) {
		this.taskId = task.getId();
		this.createTime = task.getCreateTime();
		this.status = taskStatusType;
		this.name = task.getName();
		this.dueDate = task.getDueDate();
		this.processInstanceId = task.getProcessInstanceId();

		if (userVO != null) {
			this.owner = new VivoTaskUser();
			if (userVO.getUid() != null) {
				this.owner.setId(userVO.getUid().toString());
			}
			this.owner.setName(userVO.getName());
		}
	}

	public VivoTask(HistoricTaskInstance historicTaskInstance, TaskStatusType taskStatusType, UserVO userVO) {
		this.taskId = historicTaskInstance.getId();
		this.createTime = historicTaskInstance.getCreateTime();
		this.status = taskStatusType;
		this.name = historicTaskInstance.getName();
		this.dueDate = historicTaskInstance.getDueDate();
		this.completedTime = historicTaskInstance.getEndTime();
		this.processInstanceId = historicTaskInstance.getProcessInstanceId();
		
		if (userVO != null) {
			this.owner = new VivoTaskUser();
			if (userVO.getUid() != null) {
				this.owner.setId(userVO.getUid().toString());
			}
			this.owner.setName(userVO.getName());
		}
	}
	
	public VivoTask(KanbanTaskQuery task, TaskStatusType status) {
		this.taskId = task.getTaskId();
		this.createTime = task.getCreateTime();
		this.completedTime = task.getEndTime();
		this.status = status;
		this.name = task.getName();
		this.dueDate = task.getDueDate();
		this.completedTime = task.getEndTime();
		this.processInstanceId = task.getProcessInstanceId();
		this.owner = new VivoTaskUser();
		this.owner.setId(task.getOwnerId());
		this.owner.setName(task.getOwner());
		this.assignee = task.getAssignee();
		this.candidateGroup = task.getCandidateGroup();
		this.businessKey = task.getBusinessKey();
		
		this.processVariables = new HashMap<>();
		this.processVariables.put("tipoInvestimento", ComboDescriptions.getTipoInvestimentoDescription(task.getTipoInvestimento()));
		this.processVariables.put("tipoObra", ComboDescriptions.getTipoObraDescription(task.getTipoObra()));
		this.processVariables.put("tipoTecnologia", ComboDescriptions.getTipoTecnologiaDescription(task.getTipoTecnologia()));
	}
	
	public VivoTask(){
		
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the lateDays
	 */
	public long getLateDays() {
		Date today = null;
		if (this.completedTime == null){
			today = GregorianCalendar.getInstance().getTime();			
		} else {
			today = completedTime;
		}
		
		if (this.getDueDate() != null && this.getDueDate().before(today)) {
			long diffDays = today.getTime() - this.getDueDate().getTime();
			this.lateDays = TimeUnit.DAYS.convert(diffDays, TimeUnit.MILLISECONDS);
		} else {
			this.lateDays = 0L;
		}
		return this.lateDays;
	}

	/**
	 * @param lateDays the lateDays to set
	 */
	public void setLateDays(long lateDays) {
		this.lateDays = lateDays;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the candidateGroup
	 */
	public String getCandidateGroup() {
		return candidateGroup;
	}

	/**
	 * @param candidateGroup the candidateGroup to set
	 */
	public void setCandidateGroup(String candidateGroup) {
		this.candidateGroup = candidateGroup;
	}

	/**
	 * @return the candidateGroupDescription
	 */
	public String getCandidateGroupDescription() {
		return candidateGroupDescription;
	}

	/**
	 * @param candidateGroupDescription the candidateGroupDescription to set
	 */
	public void setCandidateGroupDescription(String candidateGroupDescription) {
		this.candidateGroupDescription = candidateGroupDescription;
	}

	/**
	 * @return the formItens
	 */
	public List<VivoTaskFormItem> getFormItens() {
		return formItens;
	}

	/**
	 * @param formItens the formItens to set
	 */
	public void setFormItens(List<VivoTaskFormItem> formItens) {
		this.formItens = formItens;
	}

	/**
	 * @return the status
	 */
	public TaskStatusType getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatusType status) {
		this.status = status;
	}

	/**
	 * @return the owner
	 */
	public VivoTaskUser getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(VivoTaskUser owner) {
		this.owner = owner;
	}

	/**
	 * @return the headers
	 */
	public List<VivoTaskFormItem> getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(List<VivoTaskFormItem> headers) {
		this.headers = headers;
	}

	/**
	 * @return the businessKey
	 */
	public String getBusinessKey() {
		return businessKey;
	}

	/**
	 * @param businessKey the businessKey to set
	 */
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	
	/**
	 * @return the completedTime
	 */
	public Date getCompletedTime() {
		return completedTime;
	}

	
	/**
	 * @param completedTime the completedTime to set
	 */
	public void setCompletedTime(Date completedTime) {
		this.completedTime = completedTime;
	}

	
	/**
	 * @return the createProcessTime
	 */
	public Date getCreateProcessTime() {
		return createProcessTime;
	}

	
	/**
	 * @param createProcessTime the createProcessTime to set
	 */
	public void setCreateProcessTime(Date createProcessTime) {
		this.createProcessTime = createProcessTime;
	}

	
	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	
	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	
	/**
	 * @return the processVariables
	 */
	public Map<String, String> getProcessVariables() {
		return processVariables;
	}

	
	/**
	 * @param processVariables the processVariables to set
	 */
	public void setProcessVariables(Map<String, String> processVariables) {
		this.processVariables = processVariables;
	}

}
