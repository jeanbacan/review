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
public class CountTasksCalendarizadosProvider {

	private static final Logger logger = Logger.getLogger(CountTasksCalendarizadosProvider.class);
	
	public String countCalendarizados(Map<String, Object> parameters) throws SQLBuilderException {
		
		FilterDTO filter = (FilterDTO) parameters.get("filter");
		
		String stmt = "select distinct RES.ID_ as taskId, RES.NAME_ as name,RES.DUE_DATE_ as dueDate,RES.END_TIME_ as endTime, " +
				"ASS.ID_ as assigneeId, ASS.FIRST_ as assignee, OWN.ID_ as ownerId, OWN.ID_ as ownerId, OWN.FIRST_ as owner, RES.PROC_INST_ID_ as processInstanceId," +
				"RES.START_TIME_ as createTime, HPI.BUSINESS_KEY_ as businessKey, I.GROUP_ID_ as candidateGroup, TEC.VALUE as tipoTecnologia, " +
				"CASE WHEN INV.value IS NOT NULL THEN INV.value ELSE INV_OBRA.value END as tipoInvestimento, " +
				"CASE WHEN INV.value IS NULL THEN NULL ELSE INV_OBRA.VALUE END as tipoObra " +
				"from ACT_HI_TASKINST RES " +
				"inner join ACT_HI_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ " +
				"inner join ACT_HI_PROCINST HPI ON RES.PROC_INST_ID_ = HPI.ID_ " +
				"LEFT join ACT_ID_USER ASS ON RES.ASSIGNEE_ = ASS.ID_ " +
				"INNER join ACT_ID_USER OWN ON HPI.START_USER_ID_ = OWN.ID_ ";
				
		String taskGroups = filter.getConstantValue(FilterDTO.FILTER_GROUPS);
		
		//Filtros selecionados pela UI
		String uf = filter.getConstantValue(FilterDTO.FILTER_UF);
		String cidade = filter.getConstantValue(FilterDTO.FILTER_CIDADE);
		String tipoObra = filter.getConstantValue(FilterDTO.FILTER_OBRA);
		String taskName = filter.getConstantValue(FilterDTO.FILTER_TASK_NAME);
		String ownerName = filter.getConstantValue(FilterDTO.FILTER_OWNER);
		String diretoria = filter.getConstantValue(FilterDTO.FILTER_DIRETORIA);
		String ownerGroup = filter.getConstantValue(FilterDTO.FILTER_OWNER_GROUP);
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

		//TIPO TECNOLOGIA
		stmt += "INNER JOIN ( " +
        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoTecnologia' " + (tipoTecnologia != null ? "AND V.TEXT_ = '"+tipoTecnologia+"'" : "" ) +
	    ") TEC ON RES.PROC_INST_ID_ = TEC.PROC_INST_ID_ ";
		
		//TIPO INVESTIMENTO
		stmt += "INNER JOIN ( " +
        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL " +
        "AND (V.NAME_ = 'tipoInvestimento' OR V.NAME_ = 'tipoInvestimentoOld') " + (tipoFluxoInvestimento != null ? "AND V.TEXT_ = '"+tipoFluxoInvestimento+"'" : "" ) +
        ") INV ON RES.PROC_INST_ID_ = INV.PROC_INST_ID_ ";
		
		//TIPO OBRA
		stmt += "INNER JOIN ( " +
        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoInvestimento' " + (tipoObra != null ? "AND V.TEXT_ = '"+tipoObra+"'" : "" ) +
		") INV_OBRA ON RES.PROC_INST_ID_ = INV_OBRA.PROC_INST_ID_ ";
		
		if (ownerGroup != null){
			stmt += "INNER JOIN ( " +  
		    "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +   
		    "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'idUserGroup' AND V.TEXT_ = '" + ownerGroup + "' " +  
			") OWNER_GROUP ON RES.PROC_INST_ID_ = OWNER_GROUP.PROC_INST_ID_ ";
		}	

		stmt += "WHERE 1=1 ";
		stmt += "AND I.TYPE_ = 'candidate' AND RES.END_TIME_ is not null and HPI.END_TIME_ is null ";
		
		if (taskName != null){
			stmt += "AND UPPER(RES.NAME_) LIKE UPPER('%" + taskName + "%') ";
		}
		if (ownerName != null){
			stmt += "AND UPPER(OWN.FIRST_) LIKE UPPER('%" + ownerName + "%') ";	
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
		
		stmt += "order by RES.END_TIME_ desc";
		
		return stmt;		
	}
}
