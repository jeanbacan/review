package br.com.vivo.bcm.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jean Bacan
 */
@Entity
@Table(name = "PROGRESS")
public class Progress implements IUID<Long>, Comparable<Progress> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long uid;

	@Column(name = "TASK_DEF_KEY_")
	private String taskDefinitionKey;

	@Column(name = "TASK_NAME")
	private String taskName;

	@Column(name = "PROCESS_KEY")
	private String processKey;

	@Column(name = "VALUE")
	private Long value;

	@Column(name = "PROC_ORDER")
	private Long processOrder;

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the taskDefinitionKey
	 */
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	/**
	 * @param taskDefinitionKey the taskDefinitionKey to set
	 */
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
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

	/**
	 * @return the processKey
	 */
	public String getProcessKey() {
		return processKey;
	}

	/**
	 * @param processKey the processKey to set
	 */
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	/**
	 * @return the value
	 */
	public Long getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Long value) {
		this.value = value;
	}

	
	/**
	 * @return the processOrder
	 */
	public Long getProcessOrder() {
		return processOrder;
	}

	
	/**
	 * @param processOrder the processOrder to set
	 */
	public void setProcessOrder(Long processOrder) {
		this.processOrder = processOrder;
	}

	@Override
	public int compareTo(Progress arg0) {
		int result = this.processOrder.compareTo(arg0.getProcessOrder());
		if (result == 0){
			return this.value.compareTo(arg0.getValue());
		} else {
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Progress other = (Progress) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

}
