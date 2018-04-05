package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class ErrorSendEmailException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ErrorSendEmailException() {
		super(ErrorCode.ERROR_SEND_EMAIL);
	}
}