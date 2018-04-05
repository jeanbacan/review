package br.com.vivo.bcm.activiti.execution;

import java.util.List;

import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;

import br.com.vivo.bcm.activiti.mapper.HistoricTasksMapper;
import br.com.vivo.bcm.activiti.query.KanbanTaskQuery;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;

/**
 * Polimorfismo para CustomSqlExecution
 * 
 * @author Jean Bacan
 *
 */
public class SelectHistoricTasksSQLExecution extends AbstractCustomSqlExecution<HistoricTasksMapper, List<KanbanTaskQuery>> {

	private FilterDTO filter;

	public SelectHistoricTasksSQLExecution() {
		super(HistoricTasksMapper.class);
	}

	@Override
	public List<KanbanTaskQuery> execute(HistoricTasksMapper customMapper) {
		return customMapper.selectHistoricTasks(filter);
	}

	/**
	 * @return the filter
	 */
	public FilterDTO getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterDTO filter) {
		this.filter = filter;
	}

}
