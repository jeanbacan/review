package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.util.ConverterUtil;
import br.com.vivo.bcm.business.vo.VivoTask;

@Named("completeTaskBusinessOperation")
public class CompleteTaskBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<VivoTask, Void> {

	private static final Logger logger = Logger.getLogger(CompleteTaskBusinessOperation.class);

	@Inject
	@Named("saveGridBusinessOperation")
	private IBusinessOperation<VivoTask, VivoTask> saveGridBusinessOperation;
	
	@Inject
	@Named("notifyNewTasksBusinessOperation")
	private IBusinessOperation<String, Void> notifyNewTasksBusinessOperation;
	
	@Override
	public Void execute(VivoTask vivoTask) throws BusinessException {

		logger.info("execute do CompleteTaskBusinessOperation .");

		try {
			
			//Persiste grid e atualiza value dos form do tipo grid com id
			vivoTask = saveGridBusinessOperation.execute(vivoTask);
			
			//Converte lista de items da tela para Map e remove itens not writable
			getTaskService().setVariablesLocal(vivoTask.getTaskId(), ConverterUtil.transformFormItemsToMap(vivoTask.getFormItens()));
			getFormService().submitTaskFormData(vivoTask.getTaskId(), ConverterUtil.transformFormItemsToMap(vivoTask.getFormItens()));
			
			notifyNewTasksBusinessOperation.execute(vivoTask.getBusinessKey());
			
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}

		return null;
	}
}
