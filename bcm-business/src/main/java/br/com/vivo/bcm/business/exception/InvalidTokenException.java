package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class InvalidTokenException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
		super(ErrorCode.INVALID_TOKEN);
	}
}