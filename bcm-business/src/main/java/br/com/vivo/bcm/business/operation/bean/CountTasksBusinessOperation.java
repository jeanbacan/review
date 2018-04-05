package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.builder.ISQLSBuilder;
import br.com.vivo.bcm.business.dao.IProgressDAO;
import br.com.vivo.bcm.business.dto.CountTasksDTO;
import br.com.vivo.bcm.business.dto.CountTasksItemDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Progress;
import br.com.vivo.bcm.business.operation.IBusinessOperation;

@Named("countTasksBusinessOperation")
public class CountTasksBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<FilterDTO, CountTasksDTO> {

	private static final Logger logger = Logger.getLogger(CountTasksBusinessOperation.class);

	@Inject
	@Named("progressDAO")
	private IProgressDAO progressDAO;
	
	@Inject
	@Named("countTaskByCandidateGroupSQLBuilder")
	private ISQLSBuilder countTaskByCandidateGroupSQLBuilder;
	
	@Override
	public CountTasksDTO execute(FilterDTO filterDTO) throws BusinessException {
		logger.info("execute do countTaskByCandidateGroup .");

		List<Progress> progressList = progressDAO.find();
		
		//Ordena para apresentação
		Collections.sort(progressList);
		
		int totalTask = 0;
		int totalFacility = 0;
		List<CountTasksItemDTO> countTasks = new ArrayList<CountTasksItemDTO>();
		
		//Prepara todos os grupos de atividade
		for (Progress progress : progressList){
			
			CountTasksItemDTO item = new CountTasksItemDTO();	
			item.setTaskAmount(0);
			item.setFacilityAmount(0);
			item.setId(0);
			item.setName(progress.getTaskDefinitionKey());
			item.setDescription(progress.getTaskName());
			item.setProcessKey(progress.getProcessKey());
			
			countTasks.add(item);	
		}		
		
		//Consulta grupos existentes para montar esteira
		List<Task> tasks = new ArrayList<Task>();
		
		try {
			String stmt = countTaskByCandidateGroupSQLBuilder.buildStatement(filterDTO);
			tasks = getTaskService().createNativeTaskQuery().sql(stmt).list();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
		
		for (Task task : tasks){
			
			CountTasksItemDTO model = new CountTasksItemDTO();
			model.setName(task.getTaskDefinitionKey());
			model.setProcessKey(task.getFormKey());
			
			int index = countTasks.indexOf(model);
			
			if (index != -1){			
				CountTasksItemDTO item = countTasks.get(index);
				
				//Facilidade direcionada para coluna priority
				int totalFacilidadeTarefa = task.getPriority();
				
				item.setTaskAmount(item.getTaskAmount() + 1);				
				item.setFacilityAmount(item.getFacilityAmount() + totalFacilidadeTarefa);
				
				//Incrementa totais
				totalTask += 1;
				totalFacility += totalFacilidadeTarefa;
			}
		}
		
		CountTasksDTO dto = new CountTasksDTO();
		dto.setTotalFacility(totalFacility);
		dto.setTotalTasks(totalTask);
		dto.setCountTaskItems(countTasks);
				
		return dto;
	}
}
