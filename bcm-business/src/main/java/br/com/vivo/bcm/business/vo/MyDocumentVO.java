package br.com.vivo.bcm.business.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vivo.bcm.business.model.MyDocument;
import br.com.vivo.rubeus.client.vo.UserVO;

@JsonInclude(value = Include.NON_NULL)
public class MyDocumentVO {

	private Long uid;

	private Long documentManagerId;

	private boolean isUploaded;

	private String name;

	private UserVO userVO;

	private String documentPath;

	/**
	 * default constructor
	 */
	public MyDocumentVO() {
	}

	/**
	 * 
	 * @param group
	 */
	public MyDocumentVO(MyDocument myDocument) {
		this.uid = myDocument.getUid();
		this.documentManagerId = myDocument.getDocumentManagerId();
		this.isUploaded = myDocument.getIsUploaded();
		this.name = myDocument.getName();

		UserVO userVO = new UserVO();
		userVO.setUid(myDocument.getUserId());

		this.userVO = (userVO);
		this.documentPath = myDocument.getDocumentPath();
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
	 * @return the userVO
	 */
	public UserVO getUserVO() {
		return userVO;
	}

	/**
	 * @param userVO the userVO to set
	 */
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
}
