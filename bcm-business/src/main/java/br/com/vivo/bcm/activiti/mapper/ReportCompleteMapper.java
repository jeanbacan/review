package br.com.vivo.bcm.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import br.com.vivo.bcm.activiti.query.ReportCompleteQuery;

/**
 * Mapeamento de queries para Relatorio completo.
 * @author Jean Bacan
 * @since 10/07/2017
 *
 */
public interface ReportCompleteMapper {

	@Select("select MAIN.*, GRID_ARQUIVO.VALUE as gridArquivo, GRID_ARMARIO.VALUE as gridArmario, OBRA.* " +
			"from ( " +
			"select distinct RES.ID_, RES.PROC_INST_ID_ as processInstanceId, RES.BUSINESS_KEY_ as businessKey, RES.START_TIME_ as startDate, TSK_RUNT.PROGRESS, " +
			"TSK_RUNT.DUE_DATE_ as endDate, USR.LAST_ as owner, TSK_HIST.NAME_ as taskName, TSK_HIST.START_TIME_ as taskStartDate, TSK_HIST.END_TIME_ as taskEndDate, " +  
			"TSK_HIST.DUE_DATE_ as taskDueDate, TSK_HIST.GROUP_ID_ as taskGroup, DIR.value as diretoria, TIPO_OBRA.text_ as tipoObra, GRID_ARQUIVO.value as ID_ARQUIVO, GRID_ARMARIO.value as ID_ARMARIO, UF.value as uf, CID.value as cidade, TEC.value as tipoTecnologia, " +
			"INV.value as tipoInvestimento, INTERESSE.value as possuiInteresse, INCLUSAO.value as sugerirInclusaoArea, COMPLETO_B2C.value as mercadoCompletoB2C, COMPLETO_B2B.value as mercadoCompletoB2B, LEVANTAMENTO.value as levantamentoValido, QTD_AREAS.value as quantidadeAreas, " +
			"RECOMENDACAO.value as resultadoFinanceiro, OBSERVACAO_RESULTADO.value as observacaoResultado, " +
			"APROVACAO_CDG.value as aprovacaoCDG, OBSERVACAO_CDG.value as observacaoCDG, PRIORIZACAO_OBRA.value as revisarPriorizacaoObra, OBSERVACAO_PRIORIZACAO.value as observacaoPriorizacaoObra, FACILIDADE.value as facilidade, " +
			"RA.VALUE as ra, RB.VALUE as RB, RC.VALUE as rc, RD.VALUE as rd, " +
			"NA.VALUE as na, NB.VALUE as nb, NC.VALUE as nc, PA.VALUE as pa, " +
			"DEMANDA_B2C.VALUE as demandaB2C, DEMANDA_B2B.VALUE as demandaB2B, " +
			"QUARTER_PRIORIZACAO.VALUE as quarterPriorizacaoObra, ANO_PRIORIZACAO.VALUE as anoPriorizacaoObra, CENTRAL.VALUE as central, " + 
			"OBSERVACAO_ETP.value AS observacaoETP, OBSERVACAO_BASE.VALUE as observacaoBase, OBSERVACAO_PROJETO.VALUE AS observacaoProjeto, OBSERVACAO_LICENCA.VALUE AS observacaoLicenca, " +
			"DATA_CONC_CONST.VALUE AS dataConclusaoConstrucao, DATA_CONC_PREV_CONST.VALUE AS dataConclusaoPrevConstrucao, OBSERVACAO_CONSTRUCAO.VALUE AS observacaoConstrucao, "+
			"TOTAL_FACILIDADES_ENTREGUES.VALUE AS totalFacilidadesEntregues, OBSERVACAO_ENTREGA_TOTAL.VALUE AS observacaoEntregaTotal "+
			"FROM ACT_HI_PROCINST RES " +
			"LEFT outer join ACT_ID_USER USR ON RES.START_USER_ID_ = USR.ID_ " +   
			"LEFT join ACT_HI_VARINST V on RES.PROC_INST_ID_ = V.PROC_INST_ID_ " +
			"LEFT JOIN ( " +
	        	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " +
	        	"FROM ACT_HI_VARINST V INNER JOIN DIRETORIA D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'diretoria' " +
	        ") DIR ON RES.PROC_INST_ID_ = DIR.PROC_INST_ID_ " + 
	        "LEFT JOIN ( " +
	        	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " + 
	        	"FROM ACT_HI_VARINST V INNER JOIN CIDADE D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'cidade' " +
	        ") CID ON RES.PROC_INST_ID_ = CID.PROC_INST_ID_ " + 
	        "LEFT JOIN ( " + 
		    	"SELECT V.TEXT_ as value, V.PROC_INST_ID_ FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'armariosNovaArea' " +  
	        ") GRID_ARMARIO ON RES.PROC_INST_ID_ = GRID_ARMARIO.PROC_INST_ID_  " +
	        "LEFT JOIN (  " +
	        	"SELECT V.TEXT_ as value, V.PROC_INST_ID_ FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'arquivosNovaArea' " + 
    		") GRID_ARQUIVO ON RES.PROC_INST_ID_ = GRID_ARQUIVO.PROC_INST_ID_ " + 	
	        "LEFT JOIN ( " +
		        "SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " + 
		        "FROM ACT_HI_VARINST V INNER JOIN UF D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'uf' " +
	        ") UF ON RES.PROC_INST_ID_ = UF.PROC_INST_ID_ " + 
	        "LEFT JOIN ( " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoTecnologia' " +
	        ") TEC ON RES.PROC_INST_ID_ = TEC.PROC_INST_ID_ " + 
			"LEFT JOIN ( " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoInvestimento' " +
			") INV ON RES.PROC_INST_ID_ = INV.PROC_INST_ID_ " +
			"LEFT JOIN ( " +  
				"SELECT DISTINCT TEXT_, V.PROC_INST_ID_ " +
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoObra' " +  
			") TIPO_OBRA ON RES.PROC_INST_ID_ = TIPO_OBRA.PROC_INST_ID_  " +
			"LEFT JOIN ( " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'possuiInteresse' " + 
			") INTERESSE ON RES.PROC_INST_ID_ = INTERESSE.PROC_INST_ID_ " +  			
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'sugerirInclusaoArea' " + 
			") INCLUSAO ON RES.PROC_INST_ID_ = INCLUSAO.PROC_INST_ID_ " +  			
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'mercadoCompletoB2C' " + 
			") COMPLETO_B2C ON RES.PROC_INST_ID_ = COMPLETO_B2C.PROC_INST_ID_  " + 			
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'mercadoCompletoB2B' " + 
			") COMPLETO_B2B ON RES.PROC_INST_ID_ = COMPLETO_B2B.PROC_INST_ID_ " + 			
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'quantidadeAreas' " + 
			") QTD_AREAS ON RES.PROC_INST_ID_ = QTD_AREAS.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
	        	"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
	        	"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'levantamentoValido' " + 
	        ") LEVANTAMENTO ON RES.PROC_INST_ID_ = LEVANTAMENTO.PROC_INST_ID_ " +		        
			"LEFT JOIN ( " + 
			    "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
			    "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'anoPriorizacaoObra' " + 
			") ANO_PRIORIZACAO ON RES.PROC_INST_ID_ = ANO_PRIORIZACAO.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
			    "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'quarterPriorizacaoObra' " + 
			") QUARTER_PRIORIZACAO ON RES.PROC_INST_ID_ = QUARTER_PRIORIZACAO.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'central' " + 
			") CENTRAL ON RES.PROC_INST_ID_ = CENTRAL.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'ra' " + 
			") RA ON RES.PROC_INST_ID_ = RA.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'rb' " + 
			") RB ON RES.PROC_INST_ID_ = RB.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'rc' " + 
			") RC ON RES.PROC_INST_ID_ = RC.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'rd' " + 
			") RD ON RES.PROC_INST_ID_ = RD.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'demandaB2C' " + 
			") DEMANDA_B2C ON RES.PROC_INST_ID_ = DEMANDA_B2C.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'na' " + 
			") NA ON RES.PROC_INST_ID_ = NA.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'nb' " + 
			") NB ON RES.PROC_INST_ID_ = NB.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'nc' " + 
			") NC ON RES.PROC_INST_ID_ = NC.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'pa' " + 
			") PA ON RES.PROC_INST_ID_ = PA.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'demandaB2B' " + 
			") DEMANDA_B2B ON RES.PROC_INST_ID_ = DEMANDA_B2B.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'facilidade' " + 
			") FACILIDADE ON RES.PROC_INST_ID_ = FACILIDADE.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'recomendacao' " + 
			") RECOMENDACAO ON RES.PROC_INST_ID_ = RECOMENDACAO.PROC_INST_ID_ " +			
        	"LEFT JOIN ( " + 
	    		"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
	    		"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoResultado' " + 
	    	") OBSERVACAO_RESULTADO ON RES.PROC_INST_ID_ = OBSERVACAO_RESULTADO.PROC_INST_ID_ " +
	    	"LEFT JOIN ( " + 
	    		"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
	    		"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoBase' " + 
	    	") OBSERVACAO_BASE ON RES.PROC_INST_ID_ = OBSERVACAO_BASE.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'aprovacaoCDG' " + 
			") APROVACAO_CDG ON RES.PROC_INST_ID_ = APROVACAO_CDG.PROC_INST_ID_ " +  				
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoResultadoCDG' " + 
			") OBSERVACAO_CDG ON RES.PROC_INST_ID_ = OBSERVACAO_CDG.PROC_INST_ID_ " +
			"LEFT JOIN ( " +  
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_  "+  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoETP' "+
			") OBSERVACAO_ETP ON RES.PROC_INST_ID_ = OBSERVACAO_ETP.PROC_INST_ID_ "	+
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'revisarPriorizacaoObra' " + 
			") PRIORIZACAO_OBRA ON RES.PROC_INST_ID_ = PRIORIZACAO_OBRA.PROC_INST_ID_ " +  							
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoPriorizacaoObra' " + 
			") OBSERVACAO_PRIORIZACAO ON RES.PROC_INST_ID_ = OBSERVACAO_PRIORIZACAO.PROC_INST_ID_ " +				
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoProjeto' " + 
			") OBSERVACAO_PROJETO ON RES.PROC_INST_ID_ = OBSERVACAO_PROJETO.PROC_INST_ID_ " +		
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoLicenca' " + 
			") OBSERVACAO_LICENCA ON RES.PROC_INST_ID_ = OBSERVACAO_LICENCA.PROC_INST_ID_ " +	
			"LEFT JOIN (  " +
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ "+
				"FROM ACT_HI_VARINST V WHERE  V.NAME_ = 'idDataConclusaoConstrucao' "+
			") DATA_CONC_CONST ON RES.PROC_INST_ID_ = DATA_CONC_CONST.PROC_INST_ID_ "+
	        "LEFT JOIN ( "+
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ "+ 
				"FROM ACT_HI_VARINST V WHERE  V.NAME_ = 'idDataConclusaoPrevConstrucao' "+
			") DATA_CONC_PREV_CONST ON RES.PROC_INST_ID_ = DATA_CONC_PREV_CONST.PROC_INST_ID_ "+
			"LEFT JOIN ( " +
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoConstrucao' "+  
			") OBSERVACAO_CONSTRUCAO ON RES.PROC_INST_ID_ = OBSERVACAO_CONSTRUCAO.PROC_INST_ID_ "+ 
			"LEFT JOIN ( " +  
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +   
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'totalFacilidadesEntregues' " +  
			") TOTAL_FACILIDADES_ENTREGUES ON RES.PROC_INST_ID_ = TOTAL_FACILIDADES_ENTREGUES.PROC_INST_ID_ " + 	
		    "LEFT JOIN ( " +
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +    
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoEntregaTotal' "+  
			") OBSERVACAO_ENTREGA_TOTAL ON RES.PROC_INST_ID_ = OBSERVACAO_ENTREGA_TOTAL.PROC_INST_ID_ " + 	
			"INNER JOIN ( " +
				"SELECT TSK.PROC_INST_ID_, MAX(TSK.DUE_DATE_) as DUE_DATE_, MAX(PR.VALUE) as PROGRESS " +
		        "FROM ACT_HI_PROCINST F " +
		        "INNER JOIN ACT_RU_TASK TSK ON TSK.PROC_INST_ID_ = F.ID_ " +
		        "INNER JOIN ACT_RE_PROCDEF DEF ON F.PROC_DEF_ID_ = DEF.ID_ " +   			
		        "INNER join PROGRESS PR ON (PR.TASK_DEF_KEY_ = TSK.TASK_DEF_KEY_ AND PR.PROCESS_KEY = DEF.KEY_) " +   			
		        "GROUP BY TSK.PROC_INST_ID_ " +
			") TSK_RUNT ON RES.PROC_INST_ID_ = TSK_RUNT.PROC_INST_ID_  " +  			
			"INNER JOIN ( " +
				"SELECT T.PROC_INST_ID_, NAME_, START_TIME_, END_TIME_, DUE_DATE_, L.GROUP_ID_ " +
				"FROM ACT_HI_TASKINST T " +
				"INNER JOIN ACT_RU_IDENTITYLINK L ON T.ID_ = L.TASK_ID_ " +      
			") TSK_HIST ON RES.PROC_INST_ID_ = TSK_HIST.PROC_INST_ID_ " +
			"WHERE RES.END_TIME_ IS NULL " + 
			"AND V.TEXT_ LIKE #{filter} AND V.NAME_ = 'uf' " +
			"AND RES.DELETE_REASON_ IS NULL ) MAIN " + 
			"LEFT JOIN ( " + 
				"SELECT SUBSTR(D.VALUE, 1,  dbms_lob.getlength(D.VALUE)+1) as VALUE, D.ID " +
				"FROM ACTIVITI_FORM_VARIABLES D " +
			") GRID_ARMARIO ON GRID_ARMARIO.ID = MAIN.ID_ARMARIO " +
			"LEFT JOIN ( " + 
				"SELECT SUBSTR(D.VALUE, 1,  dbms_lob.getlength(D.VALUE)+1) as VALUE, D.ID " +
				"FROM ACTIVITI_FORM_VARIABLES D " +
			") GRID_ARQUIVO ON GRID_ARQUIVO.ID = MAIN.ID_ARQUIVO " +
			"LEFT JOIN ( " +
				"SELECT PROC_INST_ID_, " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevETP', LONG_, NULL)) dataConclusaoPrevETP, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoETP', LONG_, NULL)) dataConclusaoETP,  " +
	            "MAX(DECODE (NAME_, 'observacaoETP', LONG_, NULL)) observacaoConclusaotETP,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevBase', LONG_, NULL)) dataConclusaoPrevBase, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoBase', LONG_, NULL)) dataConclusaoBase,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotBase', LONG_, NULL)) observacaoConclusaotBase,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevProjeto', LONG_, NULL)) dataConclusaoPrevProjeto, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoProjeto', LONG_, NULL)) dataConclusaoProjeto,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotProjeto', LONG_, NULL)) observacaoConclusaotProjeto,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevLicenca', LONG_, NULL)) dataConclusaoPrevLicenca, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoLicenca', LONG_, NULL)) dataConclusaoLicenca,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotLicenca', LONG_, NULL)) observacaoConclusaotLicenca,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevArmario', LONG_, NULL)) dataConclusaoPrevArmario, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoArmario', LONG_, NULL)) dataConclusaoArmario,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotArmario', LONG_, NULL)) observacaoConclusaotArmario,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevConstrucao', LONG_, NULL)) idDataConclusaoPrevConstrucao, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoConstrucao', LONG_, NULL)) dataConclusaoConstrucao,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotConstrucao', LONG_, NULL)) observacaoConclusaotConstrucao,  " +
	            "MAX(DECODE (NAME_, 'totalFacilidadesEntregues', LONG_, NULL)) totalFacilidadesEntregues, " +
	            "MAX(DECODE (NAME_, 'observacaoEntregaTotal', LONG_, NULL)) observacaoEntregaTotal " +
	            "FROM ( " +
	              "SELECT V.NAME_, V.PROC_INST_ID_, V.LONG_ FROM ACT_HI_TASKINST T INNER JOIN ACT_HI_VARINST V ON T.PROC_INST_ID_ = V.PROC_INST_ID_ " + 
	              "WHERE T.TASK_DEF_KEY_ = 'Id_e3d7cdfb-ab5c-4d1f-ae20-7411ecee67bd' " +
	            ") GROUP BY PROC_INST_ID_ " +
			") OBRA ON OBRA.PROC_INST_ID_ = MAIN.ID_ " +
			"order by MAIN.ID_ desc ")
	List<ReportCompleteQuery> selectOpenedInstances(String filter);
	
	@Select("select MAIN.*, GRID_ARQUIVO.VALUE as gridArquivo, GRID_ARMARIO.VALUE as gridArmario, OBRA.* " +
			"from ( " +
				"select distinct  RES.ID_, RES.PROC_INST_ID_ as processInstanceId, RES.BUSINESS_KEY_ as businessKey, RES.START_TIME_ as startDate, 100 as PROGRESS, " +
				"RES.END_TIME_ as endDate, USR.LAST_ as owner, TSK_HIST.NAME_ as taskName, TSK_HIST.START_TIME_ as taskStartDate, TSK_HIST.END_TIME_ as taskEndDate, " +  
				"TSK_HIST.DUE_DATE_ as taskDueDate, TSK_HIST.GROUP_ID_ as taskGroup, TSK_HIST.LAST_ as assignee, DIR.value as diretoria, GRID_ARQUIVO.value as ID_ARQUIVO, GRID_ARMARIO.value as ID_ARMARIO, UF.value as uf, CID.value as cidade, TEC.value as tipoTecnologia, " +
				"INV.value as tipoInvestimento, INTERESSE.value as possuiInteresse, INCLUSAO.value as sugerirInclusaoArea, COMPLETO_B2C.value as mercadoCompletoB2C, COMPLETO_B2B.value as mercadoCompletoB2B, LEVANTAMENTO.value as levantamentoValido, QTD_AREAS.value as quantidadeAreas, " +
				"RECOMENDACAO.value as resultadoFinanceiro, OBSERVACAO_RESULTADO.value as observacaoResultado, " +
				"APROVACAO_CDG.value as aprovacaoCDG, OBSERVACAO_CDG.value as observacaoCDG, PRIORIZACAO_OBRA.value as revisarPriorizacaoObra, OBSERVACAO_PRIORIZACAO.value as observacaoPriorizacaoObra, FACILIDADE.value as facilidade, " +
				"RA.VALUE as ra, RB.VALUE as RB, RC.VALUE as rc, RD.VALUE as rd, " +
				"NA.VALUE as na, NB.VALUE as nb, NC.VALUE as nc, PA.VALUE as pa, " +
				"DEMANDA_B2C.VALUE as demandaB2C, DEMANDA_B2B.VALUE as demandaB2B, " +		
				"QUARTER_PRIORIZACAO.VALUE as quarterPriorizacaoObra, ANO_PRIORIZACAO.VALUE as anoPriorizacaoObra, CENTRAL.VALUE as central, " + 
				"OBSERVACAO_ETP.value AS observacaoETP, OBSERVACAO_BASE.VALUE as observacaoBase, OBSERVACAO_PROJETO.VALUE AS observacaoProjeto, OBSERVACAO_LICENCA.VALUE AS observacaoLicenca, " +
				"DATA_CONC_CONST.VALUE AS dataConclusaoConstrucao, DATA_CONC_PREV_CONST.VALUE AS dataConclusaoPrevConstrucao, OBSERVACAO_CONSTRUCAO.VALUE AS observacaoConstrucao, "+
				"TOTAL_FACILIDADES_ENTREGUES.VALUE AS totalFacilidadesEntregues, OBSERVACAO_ENTREGA_TOTAL.VALUE AS observacaoEntregaTotal "+
				"FROM ACT_HI_PROCINST RES " +
			"LEFT outer join ACT_ID_USER USR ON RES.START_USER_ID_ = USR.ID_ " +   
			"LEFT join ACT_HI_VARINST V on RES.PROC_INST_ID_ = V.PROC_INST_ID_ " +
			"LEFT JOIN ( " +
	        	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " +
	        	"FROM ACT_HI_VARINST V INNER JOIN DIRETORIA D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'diretoria' " +
	        ") DIR ON RES.PROC_INST_ID_ = DIR.PROC_INST_ID_ " + 
	        "LEFT JOIN ( " +
	        	"SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " + 
	        	"FROM ACT_HI_VARINST V INNER JOIN CIDADE D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'cidade' " +
	        ") CID ON RES.PROC_INST_ID_ = CID.PROC_INST_ID_ " + 
	        "LEFT JOIN ( " + 
	    		"SELECT V.TEXT_ as value, V.PROC_INST_ID_ FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'armariosNovaArea' " +  
	    	") GRID_ARMARIO ON RES.PROC_INST_ID_ = GRID_ARMARIO.PROC_INST_ID_  " +
        	"LEFT JOIN (  " +
        		"SELECT V.TEXT_ as value, V.PROC_INST_ID_ FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'arquivosNovaArea' " + 
        	") GRID_ARQUIVO ON RES.PROC_INST_ID_ = GRID_ARQUIVO.PROC_INST_ID_ " + 			 	 
	        "LEFT JOIN ( " +
		        "SELECT DISTINCT NAME as VALUE, V.PROC_INST_ID_ " + 
		        "FROM ACT_HI_VARINST V INNER JOIN UF D ON D.ID = V.TEXT_ WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'uf' " +
	        ") UF ON RES.PROC_INST_ID_ = UF.PROC_INST_ID_ " + 
	        "LEFT JOIN ( " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoTecnologia' " +
	        ") TEC ON RES.PROC_INST_ID_ = TEC.PROC_INST_ID_ " + 
			"LEFT JOIN ( " +
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " + 
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoInvestimento' " +
			") INV ON RES.PROC_INST_ID_ = INV.PROC_INST_ID_ " +
			"LEFT JOIN ( "+  
				"SELECT DISTINCT TEXT_, V.PROC_INST_ID_ " +
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'tipoObra' "+  
			") TIPO_OBRA ON RES.PROC_INST_ID_ = TIPO_OBRA.PROC_INST_ID_  " +
			"LEFT JOIN ( " +
	        	"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
	        	"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'possuiInteresse' " + 
			") INTERESSE ON RES.PROC_INST_ID_ = INTERESSE.PROC_INST_ID_ " +  			
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'sugerirInclusaoArea' " + 
			") INCLUSAO ON RES.PROC_INST_ID_ = INCLUSAO.PROC_INST_ID_ " +  			
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'mercadoCompletoB2C' " + 
			") COMPLETO_B2C ON RES.PROC_INST_ID_ = COMPLETO_B2C.PROC_INST_ID_  " +
			"LEFT JOIN ( " + 
	        	"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
	        	"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'quantidadeAreas' " + 
	        ") QTD_AREAS ON RES.PROC_INST_ID_ = QTD_AREAS.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'mercadoCompletoB2B' " + 
			") COMPLETO_B2B ON RES.PROC_INST_ID_ = COMPLETO_B2B.PROC_INST_ID_ " + 			
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'levantamentoValido' " + 
			") LEVANTAMENTO ON RES.PROC_INST_ID_ = LEVANTAMENTO.PROC_INST_ID_ " +  			
			"LEFT JOIN ( " + 
		    	"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		    	"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'anoPriorizacaoObra' " + 
		    ") ANO_PRIORIZACAO ON RES.PROC_INST_ID_ = ANO_PRIORIZACAO.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		    	"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'quarterPriorizacaoObra' " + 
		    ") QUARTER_PRIORIZACAO ON RES.PROC_INST_ID_ = QUARTER_PRIORIZACAO.PROC_INST_ID_ " +						
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'central' " + 
			") CENTRAL ON RES.PROC_INST_ID_ = CENTRAL.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'ra' " + 
			") RA ON RES.PROC_INST_ID_ = RA.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'rb' " + 
			") RB ON RES.PROC_INST_ID_ = RB.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'rc' " + 
			") RC ON RES.PROC_INST_ID_ = RC.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'rd' " + 
			") RD ON RES.PROC_INST_ID_ = RD.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'demandaB2C' " + 
			") DEMANDA_B2C ON RES.PROC_INST_ID_ = DEMANDA_B2C.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'na' " + 
			") NA ON RES.PROC_INST_ID_ = NA.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'nb' " + 
			") NB ON RES.PROC_INST_ID_ = NB.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'nc' " + 
			") NC ON RES.PROC_INST_ID_ = NC.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'pa' " + 
			") PA ON RES.PROC_INST_ID_ = PA.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'demandaB2B' " + 
			") DEMANDA_B2B ON RES.PROC_INST_ID_ = DEMANDA_B2B.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'facilidade' " + 
			") FACILIDADE ON RES.PROC_INST_ID_ = FACILIDADE.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
		        "SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
		        "FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'recomendacao' " + 
			") RECOMENDACAO ON RES.PROC_INST_ID_ = RECOMENDACAO.PROC_INST_ID_ " +
			"LEFT JOIN ( " + 
	        	"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
	        	"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoResultado' " + 
	        ") OBSERVACAO_RESULTADO ON RES.PROC_INST_ID_ = OBSERVACAO_RESULTADO.PROC_INST_ID_ " +
	        "LEFT JOIN ( " + 
	    		"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
	    		"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoBase' " + 
	    	") OBSERVACAO_BASE ON RES.PROC_INST_ID_ = OBSERVACAO_BASE.PROC_INST_ID_ " +
	        "LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'aprovacaoCDG' " + 
			") APROVACAO_CDG ON RES.PROC_INST_ID_ = APROVACAO_CDG.PROC_INST_ID_ " +  			
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoResultadoCDG' " + 
			") OBSERVACAO_CDG ON RES.PROC_INST_ID_ = OBSERVACAO_CDG.PROC_INST_ID_ " +
			"LEFT JOIN ( " +  
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_  "+  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoETP' "+
			") OBSERVACAO_ETP ON RES.PROC_INST_ID_ = OBSERVACAO_ETP.PROC_INST_ID_ "	+	
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'revisarPriorizacaoObra' " + 
			") PRIORIZACAO_OBRA ON RES.PROC_INST_ID_ = PRIORIZACAO_OBRA.PROC_INST_ID_ " +  			
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoPriorizacaoObra' " + 
			") OBSERVACAO_PRIORIZACAO ON RES.PROC_INST_ID_ = OBSERVACAO_PRIORIZACAO.PROC_INST_ID_ " +	
			"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoProjeto' " + 
			") OBSERVACAO_PROJETO ON RES.PROC_INST_ID_ = OBSERVACAO_PROJETO.PROC_INST_ID_ " +		
				"LEFT JOIN ( " + 
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoLicenca' " + 
			") OBSERVACAO_LICENCA ON RES.PROC_INST_ID_ = OBSERVACAO_LICENCA.PROC_INST_ID_ " +	
			"LEFT JOIN (  " +
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ "+
				"FROM ACT_HI_VARINST V WHERE  V.NAME_ = 'idDataConclusaoConstrucao' "+
			") DATA_CONC_CONST ON RES.PROC_INST_ID_ = DATA_CONC_CONST.PROC_INST_ID_ "+
	        "LEFT JOIN ( "+
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ "+ 
				"FROM ACT_HI_VARINST V WHERE  V.NAME_ = 'idDataConclusaoPrevConstrucao' "+
			") DATA_CONC_PREV_CONST ON RES.PROC_INST_ID_ = DATA_CONC_PREV_CONST.PROC_INST_ID_ "+
			"LEFT JOIN ( " +
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +  
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoConstrucao' "+  
			") OBSERVACAO_CONSTRUCAO ON RES.PROC_INST_ID_ = OBSERVACAO_CONSTRUCAO.PROC_INST_ID_ "+ 
				"LEFT JOIN ( " +  
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +   
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'totalFacilidadesEntregues' " +  
			") TOTAL_FACILIDADES_ENTREGUES ON RES.PROC_INST_ID_ = TOTAL_FACILIDADES_ENTREGUES.PROC_INST_ID_ " + 	
		    "LEFT JOIN ( " +
				"SELECT DISTINCT TEXT_ as VALUE, V.PROC_INST_ID_ " +    
				"FROM ACT_HI_VARINST V WHERE V.TASK_ID_ IS NULL AND V.NAME_ = 'observacaoEntregaTotal' "+  
			") OBSERVACAO_ENTREGA_TOTAL ON RES.PROC_INST_ID_ = OBSERVACAO_ENTREGA_TOTAL.PROC_INST_ID_ " + 	
			"INNER JOIN ( " +
				"SELECT T.PROC_INST_ID_, NAME_, START_TIME_, END_TIME_, DUE_DATE_, L.GROUP_ID_, U.LAST_ " +
				"FROM ACT_HI_TASKINST T " +
				"INNER JOIN ACT_HI_IDENTITYLINK L ON T.ID_ = L.TASK_ID_ " +        
				"INNER JOIN ACT_ID_USER U ON U.ID_ = T.ASSIGNEE_ " +
			") TSK_HIST ON RES.PROC_INST_ID_ = TSK_HIST.PROC_INST_ID_ " +
			"WHERE RES.END_TIME_ IS NOT NULL " + 
			"AND V.TEXT_ LIKE #{filter} AND V.NAME_ = 'uf' " +
			"AND RES.DELETE_REASON_ IS NULL ) MAIN " +
			"LEFT JOIN ( " + 
			"        SELECT SUBSTR(D.VALUE, 1,  dbms_lob.getlength(D.VALUE)+1) as VALUE, D.ID " +
			"        FROM ACTIVITI_FORM_VARIABLES D " +
			") GRID_ARMARIO ON GRID_ARMARIO.ID = MAIN.ID_ARMARIO " +
			"LEFT JOIN ( " + 
			"        SELECT SUBSTR(D.VALUE, 1,  dbms_lob.getlength(D.VALUE)+1) as VALUE, D.ID " +
			"        FROM ACTIVITI_FORM_VARIABLES D " +
			") GRID_ARQUIVO ON GRID_ARQUIVO.ID = MAIN.ID_ARQUIVO " +
			"LEFT JOIN ( " +
				"SELECT PROC_INST_ID_, " +
				"MAX(DECODE (NAME_, 'idDataConclusaoPrevETP', LONG_, NULL)) dataConclusaoPrevETP, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoETP', LONG_, NULL)) dataConclusaoETP,  " +
	            "MAX(DECODE (NAME_, 'observacaoETP', LONG_, NULL)) observacaoConclusaotETP,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevBase', LONG_, NULL)) dataConclusaoPrevBase, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoBase', LONG_, NULL)) dataConclusaoBase,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotBase', LONG_, NULL)) observacaoConclusaotBase,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevProjeto', LONG_, NULL)) dataConclusaoPrevProjeto, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoProjeto', LONG_, NULL)) dataConclusaoProjeto,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotProjeto', LONG_, NULL)) observacaoConclusaotProjeto,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevLicenca', LONG_, NULL)) dataConclusaoPrevLicenca, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoLicenca', LONG_, NULL)) dataConclusaoLicenca,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotLicenca', LONG_, NULL)) observacaoConclusaotLicenca,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevArmario', LONG_, NULL)) dataConclusaoPrevArmario, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoArmario', LONG_, NULL)) dataConclusaoArmario,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotArmario', LONG_, NULL)) observacaoConclusaotArmario,  " +
	            "MAX(DECODE (NAME_, 'idDataConclusaoPrevConstrucao', LONG_, NULL)) idDataConclusaoPrevConstrucao, " + 
	            "MAX(DECODE (NAME_, 'dataConclusaoConstrucao', LONG_, NULL)) dataConclusaoConstrucao,  " +
	            "MAX(DECODE (NAME_, 'observacaoConclusaotConstrucao', LONG_, NULL)) observacaoConclusaotConstrucao,  " +
	            "MAX(DECODE (NAME_, 'totalFacilidadesEntregues', LONG_, NULL)) totalFacilidadesEntregues, " +
	            "MAX(DECODE (NAME_, 'observacaoEntregaTotal', LONG_, NULL)) observacaoEntregaTotal " +
	            "FROM ( " +
	              "SELECT V.NAME_, V.PROC_INST_ID_, V.LONG_ FROM ACT_HI_TASKINST T INNER JOIN ACT_HI_VARINST V ON T.PROC_INST_ID_ = V.PROC_INST_ID_ " +
	              "WHERE T.TASK_DEF_KEY_ = 'Id_e3d7cdfb-ab5c-4d1f-ae20-7411ecee67bd' " +
	            ") GROUP BY PROC_INST_ID_ " +
			") OBRA ON OBRA.PROC_INST_ID_ = MAIN.ID_ " +
			"order by MAIN.ID_ desc ")
	List<ReportCompleteQuery> selectFinishedInstances(String filter);
}
