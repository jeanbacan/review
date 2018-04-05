package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.activiti.execution.SelectHistoricTasksSQLExecution;
import br.com.vivo.bcm.activiti.execution.SelectRuntimeTasksSQLExecution;
import br.com.vivo.bcm.activiti.query.KanbanTaskQuery;
import br.com.vivo.bcm.business.dto.filter.Constraint;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.ConstraintType;
import br.com.vivo.bcm.business.enums.KanbanTaskType;
import br.com.vivo.bcm.business.enums.TaskStatusType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.SQLBuilderException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;

@Named("listKanbanTasksBusinessOperation")
public class ListKanbanTasksBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<FilterDTO, Map<KanbanTaskType, List<VivoTask>>> {

	private static final Logger logger = Logger.getLogger(ListKanbanTasksBusinessOperation.class);
	
	@Override
	public Map<KanbanTaskType, List<VivoTask>> execute(FilterDTO filterDTO) throws BusinessException {

		logger.info("execute do ListTaskBusinessOperation .");

		/*
		 * Captura do usuário logado
		 */
		List<String> myCandidateGroups = securityHelper.getLoggedCadidateGroups();

		/*
		 * Captura dos grupos de acesso do usuário
		 */
		Map<KanbanTaskType, List<VivoTask>> taskMap = new HashMap<KanbanTaskType, List<VivoTask>>();

		logger.info("Buscando Tarefas do grupo do usuario.");
		logger.debug("Buscando tarefas dos grupos: " + myCandidateGroups.toString());

		List<VivoTask> listPending = new ArrayList<VivoTask>();
		List<VivoTask> listExecuting = new ArrayList<VivoTask>();

		//Define parâmetro de grupos do usuário logado
		Constraint constraint = new Constraint();
		constraint.setType(ConstraintType.EQUAL);
		constraint.setValue(StringUtils.join(myCandidateGroups, ','));
		
		if (filterDTO.getConstraints() == null){
			filterDTO.setConstraints(new HashMap<String, Constraint>());
		}
		filterDTO.getConstraints().put(FilterDTO.FILTER_GROUPS, constraint);
		
		try {
			List<VivoTask> listOpened = this.getTasksFromTaskService(filterDTO);
			List<VivoTask> listComplete = this.getTasksFromHistoryService(filterDTO);

			// Separa tasks sem usuario para retornar a tela.
			for (VivoTask vivoTask : listOpened) {
				if (StringUtils.isEmpty(vivoTask.getAssignee())) {
					listPending.add(vivoTask);
				} else {
					listExecuting.add(vivoTask);
				}
			}

			taskMap.put(KanbanTaskType.PENDING, listPending);
			taskMap.put(KanbanTaskType.EXECUTING, listExecuting);
			taskMap.put(KanbanTaskType.COMPLETED, listComplete);

			return taskMap;
			
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	/**
	 * Resgata tasks abertas
	 * 
	 * @param List<String> Tarefas dos candidate groups informados
	 * @param FilterDTO Filtro para buscar por parâmetro
	 * @return List<VivoTask> Tasks encontradas
	 * @throws SQLBuilderException 
	 */
	private List<VivoTask> getTasksFromTaskService(FilterDTO filter) throws SQLBuilderException {
		List<VivoTask> taskList = new ArrayList<VivoTask>();
		
		SelectRuntimeTasksSQLExecution customSqlExecution = new SelectRuntimeTasksSQLExecution();
		customSqlExecution.setFilter(filter);
		
		List<KanbanTaskQuery> results = getManagementService().executeCustomSql(customSqlExecution);

		for (KanbanTaskQuery task : results) {
			VivoTask taskVO = new VivoTask(task, TaskStatusType.OPENED);
			taskList.add(taskVO);
		}
		return taskList;
	}

	/**
	 * Resgata tasks já completadas
	 * 
	 * @param List<String> Tarefas dos candidate groups informados
	 * @param FilterDTO Filtro para buscar por parâmetro
	 * @return List<VivoTask> Tasks encontradas
	 * @throws SQLBuilderException 
	 */
	private List<VivoTask> getTasksFromHistoryService(FilterDTO filter) throws SQLBuilderException {
		List<VivoTask> taskList = new ArrayList<VivoTask>();

		SelectHistoricTasksSQLExecution customSqlExecution = new SelectHistoricTasksSQLExecution();
		customSqlExecution.setFilter(filter);
		
		List<KanbanTaskQuery> results = getManagementService().executeCustomSql(customSqlExecution);
		
		for (KanbanTaskQuery historicTaskInstance : results) {
			VivoTask taskVO = new VivoTask(historicTaskInstance, TaskStatusType.CLOSED);
			taskList.add(taskVO);
		}
		return taskList;
	}
}
