package br.com.vivo.bcm.business.builder.bean;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.vivo.bcm.business.builder.ISQLSBuilder;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.ProcessInstanceStatusType;
import br.com.vivo.bcm.business.util.ApplicationConstants;

/**
 * Responsável por montar statement sql para lista de instancias.
 * @author Jean Marcel
 * @since 06/07/17
 *
 */
@Named("listInstancesSQLBuilder")
public class ListInstancesSQLBuilder extends BaseSQLBuilder implements ISQLSBuilder {

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.builder.ISQLSBuilder#buildStatement(br.com.vivo.bcm.business.dto.filter.FilterDTO)
	 */
	@Override
	public String buildStatement(FilterDTO filterDTO) {
		
		String stmt = "select distinct RES.ID_, RES.PROC_INST_ID_, RES.BUSINESS_KEY_, RES.PROC_DEF_ID_, RES.START_TIME_, #{progress} RES.START_ACT_ID_, " 
				+ "RES.END_ACT_ID_, RES.SUPER_PROCESS_INSTANCE_ID_, #{enddate}, RES.DELETE_REASON_, RES.TENANT_ID_, RES.NAME_, USR.FIRST_ as START_USER_ID_ " 
				+ "FROM ACT_HI_PROCINST RES "				
				+ "LEFT outer join ACT_ID_USER USR ON RES.START_USER_ID_ = USR.ID_ "
				+ "LEFT join ACT_HI_VARINST V on RES.PROC_INST_ID_ = V.PROC_INST_ID_ and V.NAME_ = 'uf'";
				
		String groupSelected = filterDTO.getConstantValue(ApplicationConstants.FILTER_SELECTED_GROUP);
		String businessKey = filterDTO.getConstantValue(ApplicationConstants.FILTER_BUSINESS_KEY);
		String statusFilter = filterDTO.getConstantValue(ApplicationConstants.FILTER_PROCESS_STATUS);
		String ufFilter = filterDTO.getConstantValue(ApplicationConstants.FILTER_SELECTED_UF);

		String sort = "RES.ID_ desc";
		String endDate = "RES.END_TIME_";
		String progress = "";

		// Filtra cancelado, completo ou ativos.
		if (statusFilter != null && StringUtils.isNotBlank(statusFilter)) {
			if (statusFilter.equals(ProcessInstanceStatusType.CANCELED.toString())) {
				stmt += "WHERE RES.DELETE_REASON_ IS NOT NULL ";

			} else if (statusFilter.equals(ProcessInstanceStatusType.FINISHED.toString())) {
				stmt += "INNER JOIN (SELECT MIN(ID_) as ID_, PROC_INST_ID_ FROM ACT_HI_TASKINST GROUP BY PROC_INST_ID_) TSK_MIN ON RES.PROC_INST_ID_ = TSK_MIN.PROC_INST_ID_ "
					+ "INNER JOIN ACT_HI_TASKINST TSK ON TSK_MIN.ID_ = TSK.ID_ "
					+ "INNER JOIN ACT_RE_PROCDEF DEF ON RES.PROC_DEF_ID_ = DEF.ID_ " 
					+ "LEFT join PROGRESS PR ON (PR.TASK_DEF_KEY_ = TSK.TASK_DEF_KEY_ AND PR.PROCESS_KEY = DEF.KEY_) "
					+ "INNER JOIN ACT_HI_IDENTITYLINK IDT ON TSK.ID_ = IDT.TASK_ID_ "
					+ "WHERE RES.END_TIME_ IS NOT NULL AND RES.DELETE_REASON_ IS NULL ";						
				
				progress = "100 AS DURATION_,";
				
				if (groupSelected != null) {
					stmt += "AND IDT.GROUP_ID_ = '" + groupSelected + "' ";
				}
				
			} else {	
				stmt += "INNER JOIN (SELECT MIN(ID_) as ID_, PROC_INST_ID_ FROM ACT_RU_TASK GROUP BY PROC_INST_ID_) TSK_MIN ON RES.PROC_INST_ID_ = TSK_MIN.PROC_INST_ID_ "
					+ "INNER JOIN ACT_RU_TASK TSK ON TSK_MIN.ID_ = TSK.ID_ "
					+ "INNER JOIN ACT_RE_PROCDEF DEF ON RES.PROC_DEF_ID_ = DEF.ID_ " 
					+ "LEFT join PROGRESS PR ON (PR.TASK_DEF_KEY_ = TSK.TASK_DEF_KEY_ AND PR.PROCESS_KEY = DEF.KEY_) "
					+ "INNER JOIN ACT_RU_IDENTITYLINK IDT ON TSK.ID_ = IDT.TASK_ID_ "
					+ "WHERE RES.END_TIME_ IS NULL AND RES.DELETE_REASON_ IS NULL ";
				
				progress = "PR.VALUE AS DURATION_,";
				endDate = "TSK.DUE_DATE_ as END_TIME_";
				
				if (groupSelected != null) {
					stmt += "AND IDT.GROUP_ID_ = '" + groupSelected + "' ";
				}
			}
		}
		if (businessKey != null && StringUtils.isNotBlank(businessKey)) {			 
			stmt += "AND RES.BUSINESS_KEY_ LIKE '%" + businessKey + "%' ";
		}
		//Filtra UF
		if (ufFilter != null){			
			stmt += "AND V.TEXT_ = '"+ufFilter+"'";
		}
		stmt += "order by #{sort} ";

		// Efetua replaces
		stmt = stmt.replace("#{sort}", sort);
		stmt = stmt.replace("#{enddate}", endDate);
		stmt = stmt.replace("#{progress}", progress);
		
		return stmt;
	}

}
