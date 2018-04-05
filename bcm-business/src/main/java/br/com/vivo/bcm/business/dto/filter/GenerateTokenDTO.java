/**
 * 
 */
package br.com.vivo.bcm.business.dto.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vivo.bcm.business.enums.TokenType;
import br.com.vivo.bcm.business.vo.DocumentVO;

/**
 * @author P9923900
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class GenerateTokenDTO {

	private DocumentVO documentVO;
	private TokenType accessType;
	private String applicationHash;

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
	 * @return the accessType
	 */
	public TokenType getAccessType() {
		return accessType;
	}

	/**
	 * @param accessType
	 *            the accessType to set
	 */
	public void setAccessType(TokenType accessType) {
		this.accessType = accessType;
	}

	/**
	 * @return the applicationHash
	 */
	public String getApplicationHash() {
		return applicationHash;
	}

	/**
	 * @param applicationHash the applicationHash to set
	 */
	public void setApplicationHash(String applicationHash) {
		this.applicationHash = applicationHash;
	}

	
	

}
