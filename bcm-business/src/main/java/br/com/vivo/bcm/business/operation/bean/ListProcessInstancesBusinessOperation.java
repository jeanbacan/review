package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.builder.ISQLSBuilder;
import br.com.vivo.bcm.business.dto.ResultPageDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.ProcessInstanceStatusType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.NotAllowedException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.ProcessInstanceVO;

@Named("listProcessInstancesBusinessOperation")
public class ListProcessInstancesBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<FilterDTO, ResultPageDTO> {

	private static final Logger logger = Logger.getLogger(ListProcessInstancesBusinessOperation.class);

	@Inject
	@Named("listInstancesSQLBuilder")
	private ISQLSBuilder instancesSQLBuilder;
	
	@Override
	public ResultPageDTO execute(FilterDTO filterDTO) throws BusinessException {

		logger.info("execute do ListProcessInstancesBusinessOperation .");

		boolean isAdmin = super.securityHelper.isLoggedUserAdmin();
		if (!isAdmin) {
			throw new NotAllowedException();
		}
		
		String stmt = instancesSQLBuilder.buildStatement(filterDTO);

		NativeHistoricProcessInstanceQuery piQuery = getHistoryService().createNativeHistoricProcessInstanceQuery().sql(stmt);
		List<HistoricProcessInstance> processInstances = piQuery.listPage(filterDTO.getPage().getOffset(), filterDTO.getPage().getSize());

		long processInstancesCount = this.count(stmt);

		List<ProcessInstanceVO> processInstanceVOs = new ArrayList<ProcessInstanceVO>();
		for (HistoricProcessInstance processInstance : processInstances) {
			ProcessInstanceVO instanceVO = this.createVO(processInstance);
			
			processInstanceVOs.add(instanceVO);
		}

		ResultPageDTO resultPageDTO = new ResultPageDTO();
		resultPageDTO.setListResult(processInstanceVOs);
		resultPageDTO.setResultCount(processInstancesCount);
		return resultPageDTO;
	}

	/**
	 * Executa count de um query statement
	 * 
	 * @return long total de registros na query
	 */
	private long count(String stmt) {
		String query = "select count(*) from ( " + stmt + " ) ";

		NativeHistoricProcessInstanceQuery piQuery = getHistoryService().createNativeHistoricProcessInstanceQuery().sql(query);
		return piQuery.count();
	}

	/**
	 * @param HistoricProcessInstance Objeto a ser convertido
	 * @return ProcessInstanceVO
	 */
	private ProcessInstanceVO createVO(HistoricProcessInstance processInstance) {
		ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();

		processInstanceVO.setProcessInstanceId(processInstance.getId());
		processInstanceVO.setBusinessKey(processInstance.getBusinessKey());
		processInstanceVO.setStartDate(processInstance.getStartTime());
		processInstanceVO.setEndDate(processInstance.getEndTime());
		if (processInstance.getDurationInMillis() != null){		
			processInstanceVO.setPercentual(Integer.parseInt(processInstance.getDurationInMillis().toString()));
		} else {
			processInstanceVO.setPercentual(0);
			processInstanceVO.setStatus(ProcessInstanceStatusType.CANCELED.toString());
		}
		processInstanceVO.setOwner(processInstance.getStartUserId());
		return processInstanceVO;
	}
}
