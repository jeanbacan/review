/**
 * 
 */
package br.com.vivo.bcm.business.dto;

import java.util.List;

/**
 * @author A0051460
 *
 */
public class GridValuesDTO {
	private List<LineValueDTO> lines;

	public GridValuesDTO() {

	}

	/**
	 * @return the lines
	 */
	public List<LineValueDTO> getLines() {
		return lines;
	}

	/**
	 * @param lines
	 *            the lines to set
	 */
	public void setLines(List<LineValueDTO> lines) {
		this.lines = lines;
	}

}
