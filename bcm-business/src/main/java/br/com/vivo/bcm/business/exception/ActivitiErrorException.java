/**
 * 
 */
package br.com.vivo.bcm.business.exception;

public class ActivitiErrorException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ActivitiErrorException(String errorCode, String message) {
		super(errorCode, message);
	}

}
