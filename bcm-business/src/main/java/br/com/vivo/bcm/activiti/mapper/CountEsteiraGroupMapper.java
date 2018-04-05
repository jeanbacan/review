package br.com.vivo.bcm.activiti.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import br.com.vivo.bcm.activiti.mapper.provider.CountTasksAprovadasProvider;
import br.com.vivo.bcm.activiti.mapper.provider.CountTasksCalendarizadosProvider;
import br.com.vivo.bcm.activiti.mapper.provider.CountTasksEmAnaliseProvider;
import br.com.vivo.bcm.activiti.mapper.provider.CountTasksEmAndamentoProvider;
import br.com.vivo.bcm.activiti.mapper.provider.CountTasksEmEstudoProvider;
import br.com.vivo.bcm.activiti.mapper.provider.CountTasksObrasConcluidasProvider;
import br.com.vivo.bcm.activiti.mapper.provider.CountTasksNaoCalendarizadosProvider;
import br.com.vivo.bcm.activiti.mapper.provider.CountTasksReprovadosProvider;
import br.com.vivo.bcm.activiti.query.EsteiraCountResult;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;

/**
 * Mapeamento de queries para Counts de Esteira por status agrupado.
 * 
 * @author Jean Bacan
 * @since 26/12/2017
 *
 */
public interface CountEsteiraGroupMapper {

	@SelectProvider(type = CountTasksEmAndamentoProvider.class, method = "countEmAndamento")
	EsteiraCountResult countEmAndamento(@Param("filter") FilterDTO filter);
	
	@SelectProvider(type = CountTasksEmEstudoProvider.class, method = "countEmEstudo")
	EsteiraCountResult countEmEstudo(@Param("filter") FilterDTO filter);
	
	@SelectProvider(type = CountTasksEmAnaliseProvider.class, method = "countEmAnalise")
	EsteiraCountResult countEmAnalise(@Param("filter") FilterDTO filter);
	
	@SelectProvider(type = CountTasksAprovadasProvider.class, method = "countAprovados")
	EsteiraCountResult countAprovados(@Param("filter") FilterDTO filter);
	
	@SelectProvider(type = CountTasksReprovadosProvider.class, method = "countReprovados")
	EsteiraCountResult countReprovados(@Param("filter") FilterDTO filter);
	
	@SelectProvider(type = CountTasksCalendarizadosProvider.class, method = "countCalendarizados")
	EsteiraCountResult countCalendarizados(@Param("filter") FilterDTO filter);
	
	@SelectProvider(type = CountTasksNaoCalendarizadosProvider.class, method = "countNaoCalendarizados")
	EsteiraCountResult countNaoCalendarizados(@Param("filter") FilterDTO filter);
	
	@SelectProvider(type = CountTasksObrasConcluidasProvider.class, method = "countObrasConcluidas")
	EsteiraCountResult countObrasConcluidas(@Param("filter") FilterDTO filter);

}
