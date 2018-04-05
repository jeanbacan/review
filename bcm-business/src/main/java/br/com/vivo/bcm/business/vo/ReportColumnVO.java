package br.com.vivo.bcm.business.vo;

public class ReportColumnVO {
	private String columnHeader;
	private String columnValue;

	
	public ReportColumnVO() {
		// TODO Auto-generated constructor stub
	}
	
	public ReportColumnVO(String header, String value) {
		this.columnHeader = header;
		this.columnValue = value;
	}
	
	/**
	 * @return the columnHeader
	 */
	public String getColumnHeader() {
		return columnHeader;
	}

	/**
	 * @param columnHeader the columnHeader to set
	 */
	public void setColumnHeader(String columnHeader) {
		this.columnHeader = columnHeader;
	}

	/**
	 * @return the columnValue
	 */
	public String getColumnValue() {
		return columnValue;
	}

	/**
	 * @param columnValue the columnValue to set
	 */
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

}
