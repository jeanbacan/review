package br.com.vivo.bcm.business.operation;

import br.com.vivo.bcm.business.enums.ExtractorType;

/**
 * Factory que define contrato para IDataExtractor.
 * 
 * @author Jean Bacan
 */
public interface IDataExtractorFactory {

	/**
	 * Recebe uma implementação a partir de um {@link ContactIterationType}.
	 * 
	 * @param IDataExtractor
	 * @return Implementação
	 */
	IDataExtractor createDataExtractor(ExtractorType contactIterationType);

}
