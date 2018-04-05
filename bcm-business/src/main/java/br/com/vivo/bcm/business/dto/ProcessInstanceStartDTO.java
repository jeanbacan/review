package br.com.vivo.bcm.business.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

@JsonInclude(value = Include.NON_NULL)
public class ProcessInstanceStartDTO {

	private String processInstanceId;

	private String processDefinitionId;

	private List<VivoTaskFormItem> formItems;

	/**
	 * @return the formItems
	 */
	public List<VivoTaskFormItem> getFormItems() {
		return formItems;
	}

	/**
	 * @param formItems the formItems to set
	 */
	public void setFormItems(List<VivoTaskFormItem> formItems) {
		this.formItems = formItems;
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
	 * @return the processDefinitionId
	 */
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	
	/**
	 * @param processDefinitionId the processDefinitionId to set
	 */
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

}
