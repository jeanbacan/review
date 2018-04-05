package br.com.vivo.bcm.activiti.formType;

/**
 * COnverte propriedade Metadata do Combobox
 * 
 * @author Jean Bacan
 *
 */
public class MetadataFormObject {

	private String beanName;

	private boolean lazy;

	private String listening;

	private boolean header = false;

	/**
	 * @return the beanName
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @param beanName the beanName to set
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * @return the isLazy
	 */
	public boolean isLazy() {
		return lazy;
	}

	/**
	 * @param isLazy the isLazy to set
	 */
	public void setLazy(boolean isLazy) {
		this.lazy = isLazy;
	}

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
	 * @return the header
	 */
	public boolean isHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(boolean header) {
		this.header = header;
	}

}
