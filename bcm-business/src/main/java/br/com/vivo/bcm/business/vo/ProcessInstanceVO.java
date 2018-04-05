package br.com.vivo.bcm.business.vo;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ProcessInstanceVO {

	private String processInstanceId;

	private String processName;

	private String processDefinitionName;

	private String owner;

	private String businessKey;

	private Date startDate;

	private Date endDate;

	private Integer percentual;

	private String cancelReason;

	private String status;

	private long lateDays;

	private Map<String, Object> processVariables;

	public ProcessInstanceVO() {
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
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return the processDefinitionName
	 */
	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	/**
	 * @param processDefinitionName the processDefinitionName to set
	 */
	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * @return the percentual
	 */
	public Integer getPercentual() {
		return percentual;
	}

	/**
	 * @param percentual the percentual to set
	 */
	public void setPercentual(Integer percentual) {
		this.percentual = percentual;
	}

	/**
	 * @return the processVariables
	 */
	public Map<String, Object> getProcessVariables() {
		return processVariables;
	}

	/**
	 * @param processVariables the processVariables to set
	 */
	public void setProcessVariables(Map<String, Object> processVariables) {
		this.processVariables = processVariables;
	}

	/**
	 * @return the cancelReason
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param cancelReason the cancelReason to set
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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
	 * @return the lateDays
	 */
	public long getLateDays() {
		Date today = GregorianCalendar.getInstance().getTime();

		if (this.getEndDate() != null && this.getEndDate().before(today)) {
			long diffDays = today.getTime() - this.getEndDate().getTime();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processInstanceId == null) ? 0 : processInstanceId.hashCode());
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
		ProcessInstanceVO other = (ProcessInstanceVO) obj;
		if (processInstanceId == null) {
			if (other.processInstanceId != null)
				return false;
		} else if (!processInstanceId.equals(other.processInstanceId))
			return false;
		return true;
	}

}
