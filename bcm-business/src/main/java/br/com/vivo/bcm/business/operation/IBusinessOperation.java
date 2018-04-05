package br.com.vivo.bcm.business.operation;

import br.com.vivo.bcm.business.exception.BusinessException;

/**
 * Interface para operações de negócio.
 * 
 * @author G0029875
 */
public interface IBusinessOperation<T, D> {

	/**
	 * Executa operação
	 * 
	 * @param T objeto para operação
	 * @return D retorno da operação.
	 */
	D execute(T t) throws BusinessException;
}