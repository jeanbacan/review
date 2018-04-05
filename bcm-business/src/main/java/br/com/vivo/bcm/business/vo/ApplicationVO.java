/**
 * 
 */
package br.com.vivo.bcm.business.vo;

import java.util.Date;
import java.util.List;

/**
 * @author P9923900
 *
 */
public class ApplicationVO {

	private Long uid;
	private String applicationKey;
	private Date insertDate;
	private String name;
	private String documentPath;
	private List<DocumentVO> documents;
	/**
	 * @return the uid
	 * 
	 * 
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
	 * @return the applicationKey
	 */
	public String getApplicationKey() {
		return applicationKey;
	}
	/**
	 * @param applicationKey the applicationKey to set
	 */
	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
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
	 * @return the documents
	 */
	public List<DocumentVO> getDocuments() {
		return documents;
	}
	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<DocumentVO> documents) {
		this.documents = documents;
	}
	

	
	
	
}
