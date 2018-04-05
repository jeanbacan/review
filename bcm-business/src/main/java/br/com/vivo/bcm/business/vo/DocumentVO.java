package br.com.vivo.bcm.business.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vivo.bcm.business.enums.TokenType;

/**
 * @author P9923900
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class DocumentVO {

	private Long uid;
	private Date insertDate;
	private boolean isUploaded;
	private String name;
	private String businessKey;
	private String fileName;
	private String documentPath;
	private TokenType accessType;
	private ApplicationVO applicationVO;
	
		

	
	public String getFileName() {
		return fileName;
	}

	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the insertDate
	 */
	public Date getInsertDate() {
		return insertDate;
	}

	/**
	 * @param insertDate the insertDate to set
	 */
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	public String getBusinessKey() {
		return businessKey;
	}

	
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}


	/**
	 * @return the isUploaded
	 */
	public boolean isUploaded() {
		return isUploaded;
	}

	/**
	 * @param isUploaded the isUploaded to set
	 */
	public void setUploaded(boolean isUploaded) {
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
	 * @return the accessType
	 */
	public TokenType getAccessType() {
		return accessType;
	}

	/**
	 * @param accessType the accessType to set
	 */
	public void setAccessType(TokenType accessType) {
		this.accessType = accessType;
	}

	/**
	 * @return the applicationVO
	 */
	public ApplicationVO getApplicationVO() {
		return applicationVO;
	}

	/**
	 * @param applicationVO the applicationVO to set
	 */
	public void setApplicationVO(ApplicationVO applicationVO) {
		this.applicationVO = applicationVO;
	}
}