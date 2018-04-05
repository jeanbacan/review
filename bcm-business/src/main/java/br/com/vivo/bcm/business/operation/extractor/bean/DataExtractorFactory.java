package br.com.vivo.bcm.business.operation.extractor.bean;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import br.com.vivo.bcm.business.enums.ExtractorType;
import br.com.vivo.bcm.business.operation.IDataExtractor;
import br.com.vivo.bcm.business.operation.IDataExtractorFactory;

/**
 * Factory para DataExtractor.
 * 
 * @author P9924018
 *
 */
@Named("dataExtractorFactory")
public class DataExtractorFactory implements IDataExtractorFactory {

	private static final Logger logger = Logger.getLogger(DataExtractorFactory.class);

	@Autowired 
    private ApplicationContext ctx;
	
	@Override
	public IDataExtractor createDataExtractor(ExtractorType extractorType) {
		logger.debug("ContactIterationType[" + extractorType + "] passado.");

		if (extractorType == null) {
			logger.error("ExtractorType não pode ser nulo.");
			throw new IllegalArgumentException("ExtractorType não pode ser nulo.");
		}

		if (ExtractorType.ARMARIOS.equals(extractorType)) {
			return ctx.getBean(ArmarioBusinessExtractor.class);
		}
		
		logger.error("DataExtractorFactory não conseguiu devolver uma implementação a partir do extractor[" + extractorType + "] passado.");
		throw new IllegalStateException("DataExtractorFactory não conseguiu devolver uma implementação a partir do extractor[" + extractorType + "] passado.");
	}
	
}
