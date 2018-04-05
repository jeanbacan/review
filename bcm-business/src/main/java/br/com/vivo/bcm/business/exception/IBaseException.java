package br.com.vivo.bcm.business.exception;

import br.com.vivo.bcm.business.enums.ErrorCode;

/**
 * Define que toda exception do sistema deve possuir um código (a partir de tipos enumerados)
 */
public interface IBaseException {
	public ErrorCode getErrorCode();
}