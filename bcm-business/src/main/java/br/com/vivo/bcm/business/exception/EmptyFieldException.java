package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class EmptyFieldException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EmptyFieldException() {
		super(ErrorCode.EMPTY_FIELDS);
	}
}