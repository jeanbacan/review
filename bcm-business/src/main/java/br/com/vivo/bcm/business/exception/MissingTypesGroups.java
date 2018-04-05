/**
 * 
 */
package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

/**
 * @author A0051460
 *
 */
public class MissingTypesGroups  extends BusinessException {

	private static final long serialVersionUID = 1L;

	public MissingTypesGroups() {
		super(ErrorCode.MISSING_TYPES_GROUPS);
	}
}
