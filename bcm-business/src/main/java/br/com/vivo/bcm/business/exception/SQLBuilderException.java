/**
 * 
 */
package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class SQLBuilderException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public SQLBuilderException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

}
