package br.com.vivo.bcm.business.dto.form;

import java.util.List;

import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

/**
 * 
 * FormField para ComboBox
 * @author G0054687
 *
 */
public class ComboBoxFormField extends VivoTaskFormItem {

	private String listening;

	private boolean isLazy = false;

	private String dataSourceName;

	private List<ComboBoxValueDTO> comboBoxPossibleValues;
	
	/**
	 * @return the listening
	 */
	public String getListening() {
		return listening;
	}

	/**
	 * @param listening the listening to set
	 */
	public void setListening(String listening) {
		this.listening = listening;
	}

	/**
	 * @return the isLazy
	 */
	public boolean isLazy() {
		return isLazy;
	}

	/**
	 * @param isLazy the isLazy to set
	 */
	public void setLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}


	/**
	 * @return the comboBoxPossibleValues
	 */
	public List<ComboBoxValueDTO> getComboBoxPossibleValues() {
		return comboBoxPossibleValues;
	}

	/**
	 * @param comboBoxPossibleValues the comboBoxPossibleValues to set
	 */
	public void setComboBoxPossibleValues(List<ComboBoxValueDTO> comboBoxPossibleValues) {
		this.comboBoxPossibleValues = comboBoxPossibleValues;
	}

	/**
	 * @return the dataSourceName
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * @param dataSourceName the dataSourceName to set
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

}
