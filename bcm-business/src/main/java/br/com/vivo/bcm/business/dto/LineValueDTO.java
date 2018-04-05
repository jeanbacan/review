/**
 * 
 */
package br.com.vivo.bcm.business.dto;

import java.util.List;

/**
 * @author A0051460
 *
 */
public class LineValueDTO {
	private List<ValueDTO> values;

	public LineValueDTO() {

	}

	public LineValueDTO(List<ValueDTO> values) {
		super();
		this.values = values;
	}

	/**
	 * @return the values
	 */
	public List<ValueDTO> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(List<ValueDTO> values) {
		this.values = values;
	}

}
