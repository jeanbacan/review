package br.com.vivo.bcm.business.exception;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author Rodrigo Blanc
 */
public class InvalidObjectInfo {

	private String message = Status.INTERNAL_SERVER_ERROR.getReasonPhrase();

	private String type;

	private Object invalidObject;

	public InvalidObjectInfo() {
	}

	public InvalidObjectInfo(Throwable t, Object invalidObject) {
		this.message = t.getMessage();
		this.type = t.getClass().getSimpleName();
		this.invalidObject = invalidObject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getInvalidObject() {
		return invalidObject;
	}

	public void setInvalidObject(Object invalidObject) {
		this.invalidObject = invalidObject;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}