package br.com.vivo.bcm.activiti.execution;

import java.util.List;

import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;

import br.com.vivo.bcm.activiti.mapper.ReportCompleteMapper;
import br.com.vivo.bcm.activiti.query.ReportCompleteQuery;
import br.com.vivo.bcm.business.enums.ProcessInstanceStatusType;

/**
 * Polimorfismo para CustomSqlExecution
 * @author Jean Bacan
 *
 */
public class ReportCompleteSQLExecution extends AbstractCustomSqlExecution<ReportCompleteMapper, List<ReportCompleteQuery>> {

	private String statusFilter;

	private String ufFilter;

	public ReportCompleteSQLExecution(Class<ReportCompleteMapper> mapperClass) {
		super(mapperClass);
	}

	@Override
	public List<ReportCompleteQuery> execute(ReportCompleteMapper customMapper) {
		if (statusFilter != null && statusFilter.equals(ProcessInstanceStatusType.FINISHED.toString())) {

			return customMapper.selectFinishedInstances(ufFilter);
		} else {

			return customMapper.selectOpenedInstances(ufFilter);
		}
	}

	/**
	 * @return the statusFilter
	 */
	public String getStatusFilter() {
		return statusFilter;
	}

	/**
	 * @param statusFilter the statusFilter to set
	 */
	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}

	/**
	 * @return the ufFilter
	 */
	public String getUfFilter() {
		return ufFilter;
	}

	/**
	 * @param ufFilter the ufFilter to set
	 */
	public void setUfFilter(String ufFilter) {
		this.ufFilter = ufFilter;
	}

}
