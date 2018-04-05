package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

public class GroupNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public GroupNotFoundException() {
		super(ErrorCode.GROUP_NOT_FOUND);
	}
}