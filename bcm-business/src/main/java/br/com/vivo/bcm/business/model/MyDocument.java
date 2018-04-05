package br.com.vivo.bcm.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The persistent class for the MY_DOCUMENT database table.
 * 
 */
@Entity
@Table(name = "MY_DOCUMENT")
public class MyDocument implements IUID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MY_DOCUMENT_SEQ")
	@SequenceGenerator(name = "MY_DOCUMENT_SEQ", sequenceName = "MY_DOCUMENT_SEQ", allocationSize = 1)
	private Long uid;

	@Column(name = "DOCUMENT_MANAGER_ID")
	private Long documentManagerId;

	@Column(name = "IS_UPLOADED")
	private boolean isUploaded;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DOCUMENT_PATH")
	private String documentPath;

	@Column(name = "USER_ID")
	private Long userId;

	@Override
	public Long getUid() {
		return this.uid;
	}

	@Override
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the documentManagerId
	 */
	public Long getDocumentManagerId() {
		return documentManagerId;		
	}

	/**
	 * @param documentManagerId the documentManagerId to set
	 */
	public void setDocumentManagerId(Long documentManagerId) {
		this.documentManagerId = documentManagerId;
	}

	/**
	 * @return the documentPath
	 */
	public String getDocumentPath() {
		return documentPath;
	}

	/**
	 * @param documentPath the documentPath to set
	 */
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	/**
	 * @return the isUploaded
	 */
	public boolean getIsUploaded() {
		return isUploaded;
	}

	/**
	 * @param isUploaded the isUploaded to set
	 */
	public void setIsUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the user
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
