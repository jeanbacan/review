/**
 * 
 */
package br.com.vivo.bcm.business.dto;

import br.com.vivo.bcm.activiti.formType.MetadataFormObject;
import br.com.vivo.bcm.business.vo.DocumentVO;

/**
 * @author A0051460
 *
 */
public class ValueDTO {
	private String key;
	private String value;
	private MetadataFormObject metadata;
	private String displayName;
	private DocumentVO myDocumentVO;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the metadata
	 */
	public MetadataFormObject getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(MetadataFormObject metadata) {
		this.metadata = metadata;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the myDocumentVO
	 */
	public DocumentVO getMyDocumentVO() {
		return myDocumentVO;
	}

	/**
	 * @param myDocumentVO
	 *            the myDocumentVO to set
	 */
	public void setMyDocumentVO(DocumentVO myDocumentVO) {
		this.myDocumentVO = myDocumentVO;
	}

}
