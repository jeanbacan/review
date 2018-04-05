package br.com.vivo.bcm.business.builder;

import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.SQLBuilderException;

/**
 * Contrato para implementação de statement sql builder
 * @author Jean Marcel
 *
 */
public interface ISQLSBuilder {

	/**
	 * COnstrói stmt específico para cada implementação conforme seus parâmetros
	 * @param FilterDTO filterDTO
	 * @return String statment
	 * @throws BusinessException 
	 */
	String buildStatement(FilterDTO filter) throws SQLBuilderException;
}
