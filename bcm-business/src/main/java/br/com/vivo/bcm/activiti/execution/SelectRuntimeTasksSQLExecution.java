package br.com.vivo.bcm.activiti.execution;

import java.util.List;

import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;

import br.com.vivo.bcm.activiti.mapper.RuntimeTasksMapper;
import br.com.vivo.bcm.activiti.query.KanbanTaskQuery;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;

/**
 * Polimorfismo para CustomSqlExecution
 * 
 * @author Jean Bacan
 *
 */
public class SelectRuntimeTasksSQLExecution extends AbstractCustomSqlExecution<RuntimeTasksMapper, List<KanbanTaskQuery>> {

	private FilterDTO filter;

	public SelectRuntimeTasksSQLExecution() {
		super(RuntimeTasksMapper.class);
	}

	@Override
	public List<KanbanTaskQuery> execute(RuntimeTasksMapper customMapper) {
		return customMapper.selectRuntimeTasks(filter);
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
