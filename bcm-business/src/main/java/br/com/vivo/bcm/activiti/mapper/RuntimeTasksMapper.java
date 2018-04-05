package br.com.vivo.bcm.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import br.com.vivo.bcm.activiti.mapper.provider.SelectRuntimeTasksProvider;
import br.com.vivo.bcm.activiti.query.KanbanTaskQuery;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;

/**
 * Mapeamento de queries para Listar tarefas Runtime do Kanban
 * 
 * @author Jean Bacan
 * @since 15/12/2017
 *
 */
public interface RuntimeTasksMapper {

	@SelectProvider(type = SelectRuntimeTasksProvider.class, method = "selectRuntimeTasks")
	List<KanbanTaskQuery> selectRuntimeTasks(@Param("filter") FilterDTO filter);

}
