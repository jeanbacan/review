/**
 * 
 */
package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

/**
 * @author A0051460
 *
 */
public class MissingInformationException  extends BusinessException {

	private static final long serialVersionUID = 1L;

	public MissingInformationException() {
		super(ErrorCode.MISSING_INFORMATION);
	}
}
