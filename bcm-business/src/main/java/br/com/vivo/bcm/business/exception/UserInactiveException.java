package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class UserInactiveException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public UserInactiveException() {
		super(ErrorCode.USER_INACTIVE);
	}

}
