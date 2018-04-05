package br.com.vivo.bcm.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import br.com.vivo.bcm.activiti.mapper.provider.SelectHistoricTasksProvider;
import br.com.vivo.bcm.activiti.query.KanbanTaskQuery;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;

/**
 * Mapeamento de queries para Relatorio completo.
 * 
 * @author Jean Bacan
 * @since 10/07/2017
 *
 */
public interface HistoricTasksMapper {

	@SelectProvider(type = SelectHistoricTasksProvider.class, method = "selectHistoricTasks")
	List<KanbanTaskQuery> selectHistoricTasks(@Param("filter") FilterDTO filter);

}
