package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class NotAllowedException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public NotAllowedException() {
		super(ErrorCode.NOT_ALLOWED);
	}

	public NotAllowedException(Throwable e) {
		super(ErrorCode.NOT_ALLOWED, e);
	}
}