/**
 * 
 */
package br.com.vivo.bcm.business.exception;

public class DataExtractorException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public DataExtractorException(String errorCode, String message) {
		super(errorCode, message);
	}

}
