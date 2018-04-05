package br.com.vivo.bcm.activiti.query;

import java.util.Date;

import br.com.vivo.bcm.activiti.mapper.RuntimeTasksMapper;

/**
 * Bean mapeado e utilizado pelas consultas em {@link RuntimeTasksMapper}
 * 
 * @author Jean Bacan
 * @since 14/12/2017
 */
public class KanbanTaskQuery {

	private String name;

	private Date dueDate;

	private String taskId;

	private Date createTime;
	
	private Date endTime;

	/**
	 * Usuário atual responsável pela tarefa
	 */
	private String assignee;

	private String assigneeId;

	private String owner;

	private String ownerId;

	private String businessKey;

	private String candidateGroup;

	private String processInstanceId;

	private String tipoInvestimento;

	private String tipoObra;

	private String tipoTecnologia;

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
	 * @return the assigneeId
	 */
	public String getAssigneeId() {
		return assigneeId;
	}

	/**
	 * @param assigneeId the assigneeId to set
	 */
	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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
	 * @return the tipoInvestimento
	 */
	public String getTipoInvestimento() {
		return tipoInvestimento;
	}

	/**
	 * @param tipoInvestimento the tipoInvestimento to set
	 */
	public void setTipoInvestimento(String tipoInvestimento) {
		this.tipoInvestimento = tipoInvestimento;
	}

	/**
	 * @return the tipoObra
	 */
	public String getTipoObra() {
		return tipoObra;
	}

	/**
	 * @param tipoObra the tipoObra to set
	 */
	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	/**
	 * @return the tipoTecnologia
	 */
	public String getTipoTecnologia() {
		return tipoTecnologia;
	}

	/**
	 * @param tipoTecnologia the tipoTecnologia to set
	 */
	public void setTipoTecnologia(String tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}

	
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
