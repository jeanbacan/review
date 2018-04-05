/**
 * 
 */
package br.com.vivo.bcm.business.transformer.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.annotation.qualifiers.SystemConfigurationQualifier;
import br.com.vivo.bcm.business.configuration.bean.SystemConfiguration;
import br.com.vivo.bcm.business.enums.ArmarioColumnsType;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.transformer.IColumnResolver;
import br.com.vivo.configurationutils.IConfiguration;

/**
 * Implementação para mapear colunas dos relatórios com os models.
 * 
 * @author Jean Marcel
 *
 */
@Named("armarioColumnResolver")
public class ArmarioColumnResolver implements IColumnResolver<String[], ArmarioColumnsType> {

	private static final Logger LOGGER = Logger.getLogger(ArmarioColumnResolver.class);

	@Inject
	@SystemConfigurationQualifier
	private IConfiguration configuration;

	private String[] data;

	private List<String> columns;

	private Map<ArmarioColumnsType, String> columnsPositionMap = new HashMap<ArmarioColumnsType, String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.transformer.IColumnResolver#setData(java.lang.Object)
	 */
	@Override
	public void setData(String[] data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.transformer.IColumnResolver#setHeader(java.lang.Object)
	 */
	@Override
	public void setHeader(String[] header) {
		columns = new ArrayList<String>();
		for (String h : header) {
			columns.add(StringUtils.trimToEmpty(h).toLowerCase());
		}

		String columnsConfiguration = this.configuration.getProperty(ConfigurationType.COLUMNS_HEADERS_ARMARIO.getKey());
		String[] columnNames = columnsConfiguration.split(SystemConfiguration.SEPARATOR);

		for (String column : columnNames) {
			String[] columnsValue = column.split("=");

			ArmarioColumnsType type = ArmarioColumnsType.valueOf(columnsValue[0].trim());

			if (type == null) {
				LOGGER.warn("Ignorando column: " + columnsValue[1]);
				continue;
			}
			String value = StringUtils.trimToEmpty(columnsValue[1]).toLowerCase();

			columnsPositionMap.put(type, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.transformer.IColumnResolver#getValue(br.com.vivo.bcm.business.enums.ColumnsType)
	 */
	@Override
	public String getValue(ArmarioColumnsType columnsType) {
		String result = null;

		try {
			String fileColumnName = columnsPositionMap.get(columnsType);
			Integer columnPosition = columns.indexOf(fileColumnName);
			String value = data[columnPosition];

			if (value != null && value.length() > 0) {
				result = value;
			}

			result = data[columnPosition];
			
		} catch (Exception ex) {
			LOGGER.error("Coluna não encontrada. Retornando vazio." + columnsType, ex);
		}
		return result;
	}

}
