/**
 * 
 */
package br.com.vivo.bcm.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.vivo.bcm.business.enums.VivoTaskFormType;

/**
 * @author A0051460
 *
 */

@Entity
@Table(name = "ACTIVITI_FORM_VARIABLES")
public class ActivitiFormVariables implements IUID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITI_FORM_VAR_SEQ")
	@SequenceGenerator(name = "ACTIVITI_FORM_VAR_SEQ", sequenceName = "ACTIVITI_FORM_VAR_SEQ", allocationSize = 1)
	private Long uid;

	@Column(name = "TASK_ID")
	private String taskId;
	
	@Column(name = "VAR_NAME")
	private String varName;
	
	@Column(name = "VAR_TYPE")
	private VivoTaskFormType varType;

	@Column(name = "VALUE")
	private String value;

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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the varType
	 */
	public VivoTaskFormType getVarType() {
		return varType;
	}

	/**
	 * @param varType the varType to set
	 */
	public void setVarType(VivoTaskFormType varType) {
		this.varType = varType;
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
	 * @return the varName
	 */
	public String getVarName() {
		return varName;
	}

	/**
	 * @param varName the varName to set
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

}