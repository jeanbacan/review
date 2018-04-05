package br.com.vivo.bcm.business.vo;

import java.util.List;

public class ReportRowVO {

	private String processInstanceId;

	private List<ReportColumnVO> processInstanceColumns;

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
	 * @return the processInstanceColumns
	 */
	public List<ReportColumnVO> getProcessInstanceColumns() {
		return processInstanceColumns;
	}

	/**
	 * @param processInstanceColumns the processInstanceColumns to set
	 */
	public void setProcessInstanceColumns(List<ReportColumnVO> processInstanceColumns) {
		this.processInstanceColumns = processInstanceColumns;
	}

}
