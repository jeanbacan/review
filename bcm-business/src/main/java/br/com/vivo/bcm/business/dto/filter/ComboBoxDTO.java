package br.com.vivo.bcm.business.dto.filter;

/**
 * DTO para efetuar buscas apos change de combobox
 * @author G0054687
 *
 */
public class ComboBoxDTO {

	private String value;

	private String dataSourceName;

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

}
