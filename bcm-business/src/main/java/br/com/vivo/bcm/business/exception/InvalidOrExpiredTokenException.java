package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class InvalidOrExpiredTokenException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public InvalidOrExpiredTokenException() {
		super(ErrorCode.INVALID_OR_EXPIRED_TOKEN);
	}
}