/**
 * 
 */
package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

/**
 * @author A0051460
 *
 */
public class NotFoundUserInGroupException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public NotFoundUserInGroupException() {
		super(ErrorCode.USER_NOT_FOUND_IN_GROUP);
	}
}
