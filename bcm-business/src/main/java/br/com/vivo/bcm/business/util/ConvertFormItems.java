/**
 * 
 */
package br.com.vivo.bcm.business.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.ActivitiFormVariables;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

/**
 * @author Jean Bacan
 *
 */
public class ConvertFormItems {

	private static final Logger logger = Logger.getLogger(ConvertFormItems.class);
	/**
	 * Transforma Lista de {@link VivoTaskFormItem} para um Map
	 * @param List<VivoTaskFormItem> formItens
	 * @return Map<String, String> Map para save or complete
	 */
	public static Map<String, String> transformFormItemsToMap(List<VivoTaskFormItem> formItens) {
		Map<String, String> map = new HashMap<String, String>();
		if (formItens != null) {
			for (VivoTaskFormItem item : formItens) {
				if (item.isWritable()){
					map.put(item.getId(), item.getValue());
				}
			}
		}
		return map;
	}

	/**
	 * Transforma Lista de {@link VivoTaskFormItem} para um Map
	 * @param List<VivoTaskFormItem> formItens
	 * @return Map<String, String> Map para save or complete
	 */
	public static Map<String, Object> transformFormItemsToObjectMap(List<VivoTaskFormItem> formItens) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (formItens != null) {
			for (VivoTaskFormItem item : formItens) {
				if (item.isWritable()){
					map.put(item.getId(), item.getValue());
				}
			}
		}
		return map;
	}
	
	public static ActivitiFormVariables buildActivitiFormVariable(String taskId, VivoTaskFormItem item) throws BusinessException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		try {
			String json = ow.writeValueAsString(item.getGridValues());
			
			ActivitiFormVariables grid = new ActivitiFormVariables();
			grid.setTaskId(taskId);
			grid.setVarName(item.getId());
			grid.setVarType(item.getType());
			grid.setValue(json);
			
			return grid;
			
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(ErrorCode.ERROR_CONVERT_GRID);
		}
	}
}