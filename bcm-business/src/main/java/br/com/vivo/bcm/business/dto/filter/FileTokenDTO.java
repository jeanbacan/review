package br.com.vivo.bcm.business.dto.filter;

import br.com.vivo.bcm.business.vo.DocumentVO;
import br.com.vivo.bcm.business.vo.MyDocumentVO;

public class FileTokenDTO {

	private DocumentVO documentVO;
	private String token;
	private MyDocumentVO myDocumentVO;

	public FileTokenDTO() {
		super();
	}

	/**
	 * @return the myDocumentVO
	 */
	public MyDocumentVO getMyDocumentVO() {
		return myDocumentVO;
	}

	/**
	 * @param myDocumentVO
	 *            the myDocumentVO to set
	 */
	public void setMyDocumentVO(MyDocumentVO myDocumentVO) {
		this.myDocumentVO = myDocumentVO;
	}

	/**
	 * @return the documentVO
	 */
	public DocumentVO getDocumentVO() {
		return documentVO;
	}

	/**
	 * @param documentVO
	 *            the documentVO to set
	 */
	public void setDocumentVO(DocumentVO documentVO) {
		this.documentVO = documentVO;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
