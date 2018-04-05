package br.com.vivo.bcm.business.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vivo.bcm.business.enums.TokenType;
import br.com.vivo.bcm.business.model.Token;

@JsonInclude(value = Include.NON_NULL)
public class TokenVO {

	private String hash;

	private TokenType tokenType;

	private Long userId;

	/**
	 * default constructor
	 */
	public TokenVO() {
	}

	/**
	 * 
	 * @param token
	 */
	public TokenVO(Token token) {
		this.hash = token.getHash();
		this.tokenType = token.getTokenType();
		this.userId = token.getUserId();
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return the tokenType
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * @return the userVO
	 */
	public Long getUserVO() {
		return userId;
	}

	/**
	 * @param userVO the userVO to set
	 */
	public void setUserVO(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
