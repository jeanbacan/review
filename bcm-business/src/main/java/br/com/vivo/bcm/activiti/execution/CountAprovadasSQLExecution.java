package br.com.vivo.bcm.activiti.execution;

import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;

import br.com.vivo.bcm.activiti.mapper.CountEsteiraGroupMapper;
import br.com.vivo.bcm.activiti.query.EsteiraCountResult;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;

/**
 * Polimorfismo para CustomSqlExecution
 * 
 * @author Jean Bacan
 *
 */
public class CountAprovadasSQLExecution extends AbstractCustomSqlExecution<CountEsteiraGroupMapper, EsteiraCountResult> {

	private FilterDTO filter;

	public CountAprovadasSQLExecution() {
		super(CountEsteiraGroupMapper.class);
	}

	@Override
	public EsteiraCountResult execute(CountEsteiraGroupMapper customMapper) {
		return customMapper.countAprovados(filter);
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
