package br.com.vivo.bcm.activiti.query;

import java.util.Date;

/**
 * Bean mapeado consulta de informações necessárias para email de nova tarefa
 * 
 * @author Jean Bacan
 * @since 21/11/2017
 */
public class NewTaskMailQuery {

	private String processInstanceId;

	private String owner;

	private String businessKey;

	private String taskGroup;
	
	private String taskName;

	private Date taskDueDate;

	private String assignee;
	
	private String ownerGroup;

	private String uf;

	private String cidade;

	private String diretoria;

	private String tipoTecnologia;

	private String tipoInvestimento;

	private String tipoObra;

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
	 * @return the taskGroup
	 */
	public String getTaskGroup() {
		return taskGroup;
	}

	/**
	 * @param taskGroup the taskGroup to set
	 */
	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	/**
	 * @return the taskDueDate
	 */
	public Date getTaskDueDate() {
		return taskDueDate;
	}

	/**
	 * @param taskDueDate the taskDueDate to set
	 */
	public void setTaskDueDate(Date taskDueDate) {
		this.taskDueDate = taskDueDate;
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
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the diretoria
	 */
	public String getDiretoria() {
		return diretoria;
	}

	/**
	 * @param diretoria the diretoria to set
	 */
	public void setDiretoria(String diretoria) {
		this.diretoria = diretoria;
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
	 * @return the ownerGroup
	 */
	public String getOwnerGroup() {
		return ownerGroup;
	}

	
	/**
	 * @param ownerGroup the ownerGroup to set
	 */
	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}
	
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessKey == null) ? 0 : businessKey.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewTaskMailQuery other = (NewTaskMailQuery) obj;
		if (businessKey == null) {
			if (other.businessKey != null)
				return false;
		} else if (!businessKey.equals(other.businessKey))
			return false;
		return true;
	}

}
