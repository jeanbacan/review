/**
 * 
 */
package br.com.vivo.bcm.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import br.com.vivo.bcm.business.dto.GridValuesDTO;
import br.com.vivo.bcm.business.dto.LineValueDTO;
import br.com.vivo.bcm.business.dto.ValueDTO;
import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.enums.ReportType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.ActivitiFormVariables;
import br.com.vivo.bcm.business.vo.GridReportVO;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

/**
 * @author Jean Bacan
 *
 */
public class ConverterUtil {

	private static final Logger logger = Logger.getLogger(ConverterUtil.class);
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
			logger.error("Não foi possivel converter grid.", e);
			throw new BusinessException(ErrorCode.ERROR_CONVERT_GRID);
		}
	}

	/**
	 * Converte grid salva em Grid para Report.
	 * @param GridValuesDTO gridDTO
	 * @return List<GridReportVO> Linhas do grid
	 */
	public static List<GridReportVO> transformToGridVOLines(GridValuesDTO gridDTO) {
		
		List<GridReportVO> vos = new ArrayList<GridReportVO>();
		
		for (LineValueDTO lineValue : gridDTO.getLines()){
			
			GridReportVO vo = new GridReportVO();
						
			for(ValueDTO vDto : lineValue.getValues()){
				if(vDto.getKey().equals(ReportType.ARMARIO.getDescription())){
					vo.setArmario(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.NOME_AREA.getDescription())){
					vo.setNomeArea(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.TX_PENETRACAO_B2C.getDescription())){
					vo.setTxPenetracaoB2C(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.TX_PENETRACAO_PREDIO_B2C.getDescription())){
					vo.setTxPenetracaoPredioB2C(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.TX_PENETRACAO_B2B.getDescription())){
					vo.setTxPenetracaoB2B(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.TX_PENETRACAO_PREDIO_B2B.getDescription())){
					vo.setTxPenetracaoPredioB2B(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_INICIO_PREVISAO_CM.getDescription())){
					vo.setInicioPrevistoCM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_INICIO_CM.getDescription())){
					vo.setInicioCM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_FIM_PREVISAO_CM.getDescription())){
					vo.setFimPrevistoCM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_FIM_CM.getDescription())){
					vo.setFimCM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_INICIO_PREVISAO_LM.getDescription())){
					vo.setInicioPrevistoLM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_INICIO_LM.getDescription())){
					vo.setInicioLM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_FIM_PREVISAO_LM.getDescription())){
					vo.setFimPrevistoLM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.DT_FIM_LM.getDescription())){
					vo.setFimLM(vDto.getValue());
					
				}else if(vDto.getKey().equals(ReportType.FACILIDADE.getDescription())){
					vo.setFacilidade(vDto.getValue());
				}
				
			}			
			vos.add(vo);
			
		}
		
		return vos;
	}
	
}