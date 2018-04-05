package br.com.vivo.bcm.business.operation.bean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.activiti.execution.CountAprovadasSQLExecution;
import br.com.vivo.bcm.activiti.execution.CountEmAnaliseSQLExecution;
import br.com.vivo.bcm.activiti.execution.CountEmAndamentoSQLExecution;
import br.com.vivo.bcm.activiti.execution.CountEmEstudoSQLExecution;
import br.com.vivo.bcm.activiti.query.EsteiraCountResult;
import br.com.vivo.bcm.business.dto.CountEsteiraGroupDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.EsteiraCountType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;

@Named("countEsteiraGroupBusinessOperation")
public class CountEsteiraGroupBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<FilterDTO, Map<EsteiraCountType, CountEsteiraGroupDTO>> {

	private static final Logger logger = Logger.getLogger(CountEsteiraGroupBusinessOperation.class);

	@Override
	public Map<EsteiraCountType, CountEsteiraGroupDTO> execute(FilterDTO filterDTO) throws BusinessException {
		logger.info("execute do countTaskByCandidateGroup .");

		Map<EsteiraCountType, CountEsteiraGroupDTO> map = new HashMap<>();
			
		//Configura Executions com parametros
		CountAprovadasSQLExecution countAprovadasExec = new CountAprovadasSQLExecution();
		countAprovadasExec.setFilter(filterDTO);
		
		CountEmAnaliseSQLExecution countEmAnaliseExec = new CountEmAnaliseSQLExecution();
		countEmAnaliseExec.setFilter(filterDTO);
		
		CountEmEstudoSQLExecution countEmEstudoExec = new CountEmEstudoSQLExecution();
		countEmEstudoExec.setFilter(filterDTO);
		
		CountEmAndamentoSQLExecution countEmAndamentoExec = new CountEmAndamentoSQLExecution();
		countEmAndamentoExec.setFilter(filterDTO);
		
		//Executa SQLS para cada status
		EsteiraCountResult emAndamentoResult = getManagementService().executeCustomSql(countEmAndamentoExec);
		//EsteiraCountResult aprovadasResult = getManagementService().executeCustomSql(countAprovadasExec);
		//EsteiraCountResult emAnaliseResult = getManagementService().executeCustomSql(countEmAnaliseExec);
		//EsteiraCountResult emEstudoResult = getManagementService().executeCustomSql(countEmEstudoExec);
		
		//Converte Models em DTo para retorno do REST
//		CountEsteiraGroupDTO aprovadasDTO = new CountEsteiraGroupDTO();
//		aprovadasDTO.setTotalTasks(aprovadasResult.getCount());
//		aprovadasDTO.setTotalFacility(aprovadasResult.getFacilidades());
//		
//		CountEsteiraGroupDTO emEstudoDTO = new CountEsteiraGroupDTO();
//		emEstudoDTO.setTotalTasks(emEstudoResult.getCount());
//		emEstudoDTO.setTotalFacility(emEstudoResult.getFacilidades());
//		
//		CountEsteiraGroupDTO emAnaliseDTO = new CountEsteiraGroupDTO();
//		emAnaliseDTO.setTotalTasks(emAnaliseResult.getCount());
//		emAnaliseDTO.setTotalFacility(emAnaliseResult.getFacilidades());

		CountEsteiraGroupDTO emAndamentoDTO = new CountEsteiraGroupDTO();
		emAndamentoDTO.setTotalTasks(emAndamentoResult.getCount());
		emAndamentoDTO.setTotalFacility(emAndamentoResult.getFacilidades());
		
//		map.put(EsteiraCountType.APROVADOS, aprovadasDTO);
//		map.put(EsteiraCountType.EM_ESTUDO, emEstudoDTO);
//		map.put(EsteiraCountType.EM_ANALISE, emAnaliseDTO);
		map.put(EsteiraCountType.EM_ANDAMENTO, emAndamentoDTO);
		
		return map;
	}
}
