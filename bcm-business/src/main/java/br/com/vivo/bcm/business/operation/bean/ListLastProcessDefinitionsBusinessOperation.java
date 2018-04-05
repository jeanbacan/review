package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.ProcessDefinitionVO;

/**
 * Lista todas definições de processo. Por default trará apenas uma (AnaliseMercadologica).
 * 
 * @author Jean Bacan
 * @since 05/05/2017
 *
 */
@Named("listLastProcessDefinitionsBusinessOperation")
public class ListLastProcessDefinitionsBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<Void, List<ProcessDefinitionVO>> {

	private static final Logger logger = Logger.getLogger(ListLastProcessDefinitionsBusinessOperation.class);

	@Override
	public List<ProcessDefinitionVO> execute(Void vod) throws BusinessException {

		logger.info("execute do ListLastProcessDefinitionsBusinessOperation.");

		ProcessDefinitionQuery processDefinitionQuery = super.getRepositoryService().createProcessDefinitionQuery();

		List<ProcessDefinitionVO> lastVersionsProcessDefinitions = this.convertActivitiToVO(processDefinitionQuery.active().latestVersion().orderByProcessDefinitionName().asc().list());

		return lastVersionsProcessDefinitions;
	}

	/**
	 * Converte modelo do activiti para VO
	 * 
	 * @param processDefinitions Lista com definicoes do Activiti
	 * @return List<ProcessDefinitionVO> Lista com definições em VO
	 */
	private List<ProcessDefinitionVO> convertActivitiToVO(List<ProcessDefinition> processDefinitions) {
		List<ProcessDefinitionVO> processDefinitionVOs = new ArrayList<ProcessDefinitionVO>();

		for (ProcessDefinition processDefinition : processDefinitions) {
			ProcessDefinitionVO processDefinitionVO = new ProcessDefinitionVO();
			processDefinitionVO.setId(processDefinition.getId());
			processDefinitionVO.setKey(processDefinition.getKey());
			processDefinitionVO.setName(processDefinition.getName());
			processDefinitionVO.setVersion(processDefinition.getVersion());
			processDefinitionVOs.add(processDefinitionVO);
		}
		return processDefinitionVOs;
	}
}
