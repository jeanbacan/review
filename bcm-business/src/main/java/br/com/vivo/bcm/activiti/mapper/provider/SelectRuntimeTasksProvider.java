package br.com.vivo.bcm.activiti.mapper.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.exception.SQLBuilderException;

/**
 * Prove Select builder de tasks para Mapper do Ibatis
 * @author G0054687
 *
 */
public class SelectRuntimeTasksProvider {

	private static final Logger logger = Logger.getLogger(SelectRuntimeTasksProvider.class);
	
	public String selectRuntimeTasks(Map<String, Object> parameters) throws SQLBuilderException {
		
		FilterDTO filter = (FilterDTO) parameters.get("filter");
		
		String stmt = "SELECT * FROM (select distinct RES.ID_ as taskId, RES.NAME_ as name,RES.DUE_DATE_ as dueDate, RES.CREATE_TIME_ as createTime," +
		"ASS.ID_ as assigneeId, ASS.LAST_ as assignee, OWN.ID_ as ownerId, OWN.LAST_ as owner, RES.PROC_INST_ID_ as processInstanceId," +
		"P.BUSINESS_KEY_ as businessKey, I.GROUP_ID_ as candidateGroup, TEC.VALUE as tipoTecnologia, " +
		"INV_OBRA.value as tipoObra, INV.value as tipoInvestimento " +
		"FROM ACT_RU_TASK RES " +
		"inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ " +
		"inner join ACT_HI_PROCINST P on RES.PROC_INST_ID_ = P.ID_ " +
		"LEFT join ACT_ID_USER ASS ON RES.ASSIGNEE_ = ASS.ID_ " +
		"INNER join ACT_ID_USER OWN ON P.START_USER_ID_ = OWN.ID_ ";		
		
		//Grupos de tarefa que o usuário possui
		String taskGroups = filter.getConstantValue(FilterDTO.FILTER_GROUPS);
		
		//Filtros selecionados pela UI
		String uf = filter.getConstantValue(FilterDTO.FILTER_UF);
		String cidade = filter.getConstantValue(FilterDTO.FILTER_CIDADE);
		String tipoObra = filter.getConstantValue(FilterDTO.FILTER_OBRA);
		String taskName = filter.getConstantValue(FilterDTO.FILTER_TASK_NAME);
		String ownerName = filter.getConstantValue(FilterDTO.FILTER_OWNER);
		String diretoria = filter.getConstantValue(FilterDTO.FILTER_DIRETORIA);
		String ownerGroup = filter.getConstantValue(FilterDTO.FILTER_OWNER_GROUP);
		String businessKey = filter.getConstantValue(FilterDTO.FILTER_BUSINESSKEY);
		String tipoTecnologia = filter.getConstantValue(FilterDTO.FILTER_TECNOLOGIA);
		String tipoFluxoInvestimento = filter.getConstantValue(FilterDTO.FILTER_FLUXO);
		
		if (diretoria != null){
			stmt += "INNER JOIN ( " +
	    	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " +
	    	"FROM ACT_HI_VARINST V INNER JOIN DIRETORIA D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'diretoria' AND V.TEXT_ = '"+diretoria+"' " +
		    ") DIR ON RES.PROC_INST_ID_ = DIR.PROC_INST_ID_ ";
		}
		if (uf != null){
			stmt += "INNER JOIN ( " +
	        "SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " + 
	        "FROM ACT_HI_VARINST V INNER JOIN UF D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'uf' AND V.TEXT_ = '"+uf+"' " +
	        ") UF ON RES.PROC_INST_ID_ = UF.PROC_INST_ID_ ";
		}
		if (cidade != null){
			stmt += "INNER JOIN ( " +
	    	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " + 
	    	"FROM ACT_HI_VARINST V INNER JOIN CIDADE D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'cidade' AND V.TEXT_ = '"+cidade+"' " +
	    	") CID ON RES.PROC_INST_ID_ = CID.PROC_INST_ID_ ";
		}

		stmt += "INNER JOIN ( " +
        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoTecnologia' " + (tipoTecnologia != null ? "AND V.TEXT_ = '"+tipoTecnologia+"'" : "" ) +
	    ") TEC ON RES.PROC_INST_ID_ = TEC.PROC_INST_ID_ ";

		//TIPO INVESTIMENTO
		//LEFT se faz necessário devido a algumas tarefas antigas(antigas não possuirem a var tipo de investimento ate a 2 tarefa).
		//Isso poderá ser moficiado para INNER quando as tarefas antigas forem corrigidas manualmente no banco de dados
		if (tipoFluxoInvestimento == null){
			stmt += "LEFT JOIN ( ";
		} else {
			stmt += "INNER JOIN ( ";
		}
		stmt += "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoInvestimento' " + (tipoFluxoInvestimento != null ? "AND V.TEXT_ = '"+tipoFluxoInvestimento+"'" : "" ) +
        ") INV ON RES.PROC_INST_ID_ = INV.PROC_INST_ID_ ";
		
		//TIPO OBRA
		//LEFT se faz necessário devido a algumas tarefas não possuirem tipo de obra.
		if (tipoObra == null){
			stmt += "LEFT JOIN ( ";
		} else {
			stmt += "INNER JOIN ( ";
		}
		stmt += "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoObra' " + (tipoObra != null ? "AND V.TEXT_ = '"+tipoObra+"'" : "" ) +
		") INV_OBRA ON RES.PROC_INST_ID_ = INV_OBRA.PROC_INST_ID_ ";	
		
		if (ownerGroup != null){
			stmt += "INNER JOIN ( " +  
		    "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +   
		    "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'idUserGroup' AND V.TEXT_ = '" + ownerGroup + "' " +  
			") OWNER_GROUP ON RES.PROC_INST_ID_ = OWNER_GROUP.PROC_INST_ID_ ";
		}	

		stmt += "WHERE 1=1 ";
		stmt += "AND I.TYPE_ = 'candidate' ";
		
		if (taskName != null){
			stmt += "AND RES.TASK_DEF_KEY_  = '" + taskName + "' ";
		}
		if (ownerName != null){
			stmt += "AND UPPER(OWN.LAST_) LIKE UPPER('%" + ownerName + "%') ";	
		}
		if (businessKey != null){
			stmt += "AND UPPER(P.BUSINESS_KEY_) LIKE UPPER('%" + businessKey + "%') ";
		}
		
		//Preenche filtro de grupos
		if (taskGroups != null) {
			
			List<String> groups = new ArrayList<String>();
			
			for (String str : taskGroups.split(",")) {
				groups.add("'" + str + "'");
			}
			stmt += "and ( I.GROUP_ID_ IN ( " + StringUtils.join(groups, ',') + " ) ) ";
			
		} else {
			logger.error(ErrorCode.STATEMENT_GROUP_REQUIRED.toString());
			throw new SQLBuilderException(ErrorCode.STATEMENT_GROUP_REQUIRED, "Grupos são obritatórios para consulta");
		}
		stmt += " order by RES.CREATE_TIME_ desc) WHERE ROWNUM <= 25 ";
		
		logger.debug(stmt);
		
		return stmt;
	}
	
}
