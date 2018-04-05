package br.com.vivo.bcm.business.operation.extractor.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.annotation.qualifiers.SystemConfigurationQualifier;
import br.com.vivo.bcm.business.dto.extractor.ArmarioDTO;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.DataExtractorException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.operation.IDataExtractor;
import br.com.vivo.bcm.business.transformer.IDataTransformer;
import br.com.vivo.configurationutils.IConfiguration;

/**
 * @author P9923900
 *
 */
@Named("armarioBusinessExtractor")
public class ArmarioBusinessExtractor implements IDataExtractor {

	private static final Logger logger = Logger.getLogger(ArmarioBusinessExtractor.class);

	private static final String SPLIT = ";";

	@Inject
	@SystemConfigurationQualifier
	private IConfiguration configuration;

	/**
	 * Business para executar operação com dados retornados
	 */
	@Inject
	@Named("saveArmarioBusinessOperation")
	private IBusinessOperation<ArmarioDTO, Boolean> businessOperation;

	/**
	 * Transforma dados vindos dos arquivos Armario
	 */
	@Inject
	@Named("armarioDataTransformer")
	private IDataTransformer<ArmarioDTO, String[], String[]> dataTransformer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.operation.IBusinessOperation#execute(java.lang.Object)
	 */
	@Override
	public Void execute(Void t) throws BusinessException {
		BufferedReader br = null;

		String filePath = configuration.getProperty(ConfigurationType.ARMARIO_FILE_PATH.getKey());
		File file = new File(filePath);

		try {
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));

			boolean header = true;

			int count = 0;
			String line;
			while ((line = br.readLine()) != null) {

				String[] params = line.split(SPLIT);

				if (header) {
					logger.debug("Iniciando processamento do Header do File[ armario ], Path[" + filePath + "].");
					dataTransformer.setup(params);
					header = false;
					continue;
				}

				count++;
				logger.debug("Iniciando processamento da Linha[" + count + "] e Path[" + filePath + "].");
				ArmarioDTO loadDTO = dataTransformer.transform(params);

				if (loadDTO != null) {
					logger.debug("Iniciando execução da Linha[" + count + "]");
					businessOperation.execute(loadDTO);
				}
			}
			
		} catch (Exception e) {
			logger.error("Falha ao processar arquivo", e);
			throw new DataExtractorException(ErrorCode.EXTRACT_DATA_ERROR.getCode(), e.getMessage());
		} finally {
			try {
				if (br != null) {
					br.close();					
				}
			} catch (IOException e) {
				logger.error("Falha ao fechar BufferedReader do File[" + filePath + "].", e);
				logger.debug("Falha ao fechar arquivo", e);
			}
		}
		file.delete();
		return null;
	}
}
