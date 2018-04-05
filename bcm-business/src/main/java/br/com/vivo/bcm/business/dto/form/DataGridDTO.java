/**
 * 
 */
package br.com.vivo.bcm.business.dto.form;

import java.util.List;

import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

/**
 * @author A0051460
 *
 */
public class DataGridDTO {

	protected List<VivoTaskFormItem> values;

	/**
	 * @return the values
	 */
	public List<VivoTaskFormItem> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<VivoTaskFormItem> values) {
		this.values = values;
	}

}
