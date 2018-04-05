/**
 * 
 */
package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

/**
 * @author A0051460
 *
 */
public class ProcessDefinitionKeyEmptyException  extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ProcessDefinitionKeyEmptyException() {
		super(ErrorCode.PROCESS_DEFINITION_EMPTY);
	}
}

