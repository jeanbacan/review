package br.com.vivo.bcm.business.builder.bean;

import javax.inject.Named;

import br.com.vivo.bcm.business.builder.ISQLSBuilder;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.SQLBuilderException;

/**
 * 
 * @author Jean Marcel
 * @since 08/12/2017
 *
 */
@Named("countTaskByCandidateGroupSQLBuilder")
public class CountTaskByCandidateGroupSQLBuilder extends BaseSQLBuilder implements ISQLSBuilder {

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.builder.ISQLSBuilder#buildStatement(br.com.vivo.bcm.business.dto.filter.FilterDTO)
	 */
	@Override
	public String buildStatement(FilterDTO filter) throws SQLBuilderException {
		String stmt = "select distinct RES.ID_,RES.TASK_DEF_KEY_, RES.NAME_,RES.DUE_DATE_,RES.CREATE_TIME_,RES.ASSIGNEE_,RES.OWNER_,RES.DESCRIPTION_,RES.PROC_INST_ID_,RES.PROC_DEF_ID_, " +
				"D.KEY_ as FORM_KEY_, I.GROUP_ID_ as CATEGORY_, (CASE WHEN Y.FACILIDADE_ IS NULL THEN 0 ELSE Y.FACILIDADE_ END) as PRIORITY_ " +
				"FROM ACT_RU_TASK RES " +
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
		if (tipoTecnologia != null){
			stmt += "INNER JOIN ( " +
	        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
	        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoTecnologia' AND V.TEXT_ = '"+tipoTecnologia+"' " +
		    ") TEC ON RES.PROC_INST_ID_ = TEC.PROC_INST_ID_ ";
		}
		
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

		System.out.println(stmt);
		
		return stmt;
	}

}
