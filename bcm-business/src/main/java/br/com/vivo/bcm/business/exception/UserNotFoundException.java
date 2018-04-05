package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class UserNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
