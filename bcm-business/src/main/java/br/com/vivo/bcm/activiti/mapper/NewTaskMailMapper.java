package br.com.vivo.bcm.activiti.mapper;

import org.apache.ibatis.annotations.Select;

import br.com.vivo.bcm.activiti.query.NewTaskMailQuery;

/**
 * Mapeamento de queries informações ao enviar email de nova tarefa
 * @author Jean Bacan
 * @since 21/11/2017
 *
 */
public interface NewTaskMailMapper {

	/**
	 * @param String BusinessKey
	 * @return NewTaskMailQuery
	 */
	@Select("select distinct RES.ID_, RES.PROC_INST_ID_ as processInstanceId, RES.BUSINESS_KEY_ as businessKey, " +
			"USR.LAST_ as owner, TSK_HIST.DUE_DATE_ as taskDueDate, TSK_HIST.GROUP_ID_ as taskGroup, TSK_HIST.LAST_ as assignee, " + 
			"DIR.value as diretoria, UF.value as uf, CID.value as cidade, TEC.value as tipoTecnologia, INV_OBRA.value as tipoObra,  " +
			"INV.value as tipoInvestimento, OWNER_GROUP.VALUE as ownerGroup, TSK_HIST.NAME_ as taskName  " +
			"FROM ACT_HI_PROCINST RES  " +
			"LEFT outer join ACT_ID_USER USR ON RES.START_USER_ID_ = USR.ID_ " +    
			"LEFT join ACT_HI_VARINST V on RES.PROC_INST_ID_ = V.PROC_INST_ID_  " +
			"LEFT JOIN (  " +
	        	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " + 
	        	"FROM ACT_HI_VARINST V INNER JOIN DIRETORIA D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'diretoria' " + 
	        ") DIR ON RES.PROC_INST_ID_ = DIR.PROC_INST_ID_   " +
	        "LEFT JOIN (  " +
	        	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " +  
	        	"FROM ACT_HI_VARINST V INNER JOIN CIDADE D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'cidade' " + 
	        ") CID ON RES.PROC_INST_ID_ = CID.PROC_INST_ID_ " +  	       
	        "LEFT JOIN (  " +
		        "SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V INNER JOIN UF D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'uf' " + 
	        ") UF ON RES.PROC_INST_ID_ = UF.PROC_INST_ID_   " +
	        "LEFT JOIN (  " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoTecnologia' " + 
	        ") TEC ON RES.PROC_INST_ID_ = TEC.PROC_INST_ID_   " +
			"LEFT JOIN (  " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoInvestimento' " + 
			") INV ON RES.PROC_INST_ID_ = INV.PROC_INST_ID_  " + 			
			"LEFT JOIN (  " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoObra' " + 
			") INV_OBRA ON RES.PROC_INST_ID_ = INV_OBRA.PROC_INST_ID_  " +
			"LEFT JOIN ( " +  
			    "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +   
			     "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'idUserGroup' " +  
				") OWNER_GROUP ON RES.PROC_INST_ID_ = OWNER_GROUP.PROC_INST_ID_ " +			        
			"LEFT JOIN (  " +
			"SELECT T.PROC_INST_ID_, NAME_, DUE_DATE_, L.GROUP_ID_, U.LAST_  " +
				"FROM ACT_RU_TASK T  " +
				"LEFT JOIN ACT_RU_IDENTITYLINK L ON T.ID_ = L.TASK_ID_  " +
				"LEFT JOIN ACT_ID_USER U ON U.ID_ = T.ASSIGNEE_     " +     
			") TSK_HIST ON RES.PROC_INST_ID_ = TSK_HIST.PROC_INST_ID_  " +
			"WHERE RES.END_TIME_ IS NULL   " +
			"AND RES.BUSINESS_KEY_ = #{param} " +
			"AND RES.DELETE_REASON_ IS NULL")
	NewTaskMailQuery getInstance(String param);
	
}
