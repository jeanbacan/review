package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.configuration.bean.ProcessEngineConfiguration;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.ProcessDefinitionVO;

@Named("listProcessDefinitionsBusinessOperation")
public class ListProcessDefinitionsBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<FilterDTO, List<ProcessDefinitionVO>> {

	private static final Logger logger = Logger.getLogger(ListProcessDefinitionsBusinessOperation.class);

	@Inject
	@Named("processEngineConfiguration")
	private ProcessEngineConfiguration processEngineConfiguration;

	@Override
	public List<ProcessDefinitionVO> execute(FilterDTO filterDTO) throws BusinessException {

		logger.info("execute do ListProcessDefinitionsBusinessOperation .");

		List<ProcessDefinitionVO> processesVOList = this.getProcessDefinitionsFromRepositoryService(filterDTO);
		return processesVOList;
	}

	// Busca as ultimas versões de cada process definition, e também as versões antigas que ainda contem projetos ativos.
	private List<ProcessDefinitionVO> getProcessDefinitionsFromRepositoryService(FilterDTO filterDTO) throws BusinessException {
		ProcessDefinitionQuery processDefinitionQuery = getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> processDefinitions = processDefinitionQuery.active().latestVersion().orderByProcessDefinitionName().asc().list();
		
		List<ProcessDefinitionVO> processDefinitionVOs = new ArrayList<ProcessDefinitionVO>();
		for (ProcessDefinition processDefinition : processDefinitions) {
			
			ProcessDefinitionVO processDefinitionVO = this.createVO(processDefinition);			
			Deployment deployment = getRepositoryService().createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
			if (deployment != null){
				processDefinitionVO.setLastUpdate(deployment.getDeploymentTime());
			}
			processDefinitionVOs.add(processDefinitionVO);
		}
		return processDefinitionVOs;
	}

	private ProcessDefinitionVO createVO(ProcessDefinition processDefinition) {
		ProcessDefinitionVO processDefinitionVO = new ProcessDefinitionVO();
		processDefinitionVO.setId(processDefinition.getId());
		processDefinitionVO.setKey(processDefinition.getKey());
		processDefinitionVO.setName(processDefinition.getName());
		processDefinitionVO.setVersion(processDefinition.getVersion());
		
		long amount = getRuntimeService().createProcessInstanceQuery().active().processDefinitionId(processDefinition.getId()).count();		
		processDefinitionVO.setQtdProjectsDescendent(amount);
		
		return processDefinitionVO;
	}
}
