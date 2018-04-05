package br.com.vivo.bcm.business.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import br.com.vivo.bcm.business.exception.BusinessException;

public class ErrorVO {
	private String code;
	private String description;
	private String comments;

	/**
	 * default constructor
	 */
	public ErrorVO() {
	}

	/**
	 * @param code
	 * @param description
	 * @param comments
	 */
	public ErrorVO(String code, String description, String comments) {
		this.code = code;
		this.description = description;
		this.comments = comments;
	}

	/**
	 * @param businessException
	 */
	public ErrorVO(BusinessException businessException) {
		this.code = businessException.getErrorCode().getCode();
		this.description = businessException.getErrorCode().getDescription();
		this.comments = businessException.getErrorCode().getComments();
	}

	/**
	 * @param businessException
	 */
	public ErrorVO(String errorCode, String message) {
		this.description = message;
		this.code = errorCode;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}