package br.com.vivo.bcm.business.vo;

import java.util.List;

/**
 * 
 * @author Jean Marcel
 *
 */
public class ReportVO {

	private String[] headers;

	private List<ReportRowVO> rows;

	/**
	 * @return the header
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	/**
	 * @return the rows
	 */
	public List<ReportRowVO> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<ReportRowVO> rows) {
		this.rows = rows;
	}

}
