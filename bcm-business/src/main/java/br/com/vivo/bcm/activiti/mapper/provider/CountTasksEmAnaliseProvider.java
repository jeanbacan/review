package br.com.vivo.bcm.activiti.mapper.provider;

import java.util.Map;

import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.SQLBuilderException;

/**
 * Prove Select builder de tasks para Mapper do Ibatis
 * @author G0054687
 *
 */
public class CountTasksEmAnaliseProvider {

	public String countEmAnalise(Map<String, Object> parameters) throws SQLBuilderException {
		
		FilterDTO filter = (FilterDTO) parameters.get("filter");
		
		String stmt = "SELECT COUNT(*) as count,  SUM(A.facilidades) as facilidades FROM (" +  
				"select distinct RES.ID_, (CASE WHEN Y.FACILIDADE_ IS NULL THEN 0 ELSE Y.FACILIDADE_ END) as facilidades " + 
				"FROM ACT_RU_TASK RES  " +
				"inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ " + 
				"inner join ACT_HI_PROCINST P on RES.PROC_INST_ID_ = P.ID_ " +
				"inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ " +
				"INNER join ACT_ID_USER OWN ON P.START_USER_ID_ = OWN.ID_ " +
				"LEFT JOIN (SELECT XV.LONG_ AS FACILIDADE_, XV.PROC_INST_ID_ FROM ACT_HI_VARINST XV WHERE XV.NAME_ = 'facilidade' AND TASK_ID_ is NULL) Y ON Y.PROC_INST_ID_ = RES.PROC_INST_ID_ ";
				
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
		stmt += "AND I.TYPE_ = 'candidate' ";
		
		if (taskName != null){
			stmt += "AND UPPER(RES.NAME_) LIKE UPPER('%" + taskName + "%') ";
		}
		if (ownerName != null){
			stmt += "AND UPPER(OWN.FIRST_) LIKE UPPER('%" + ownerName + "%') ";	
		}
		
		stmt += ") A";
		
		return stmt;		
	}
}
