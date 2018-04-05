package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.util.ConverterUtil;
import br.com.vivo.bcm.business.vo.VivoTask;

@Named("saveDraftTaskBusinessOperation")
public class SaveDraftTaskBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<VivoTask, Void> {

	private static final Logger logger = Logger.getLogger(SaveDraftTaskBusinessOperation.class);

	@Inject
	@Named("saveGridBusinessOperation")
	private IBusinessOperation<VivoTask, VivoTask> saveGridBusinessOperation;

	@Override
	@Transactional
	public Void execute(VivoTask vivoTask) throws BusinessException {
		logger.info("execute do SaveDraftTaskBusinessOperation .");
		try {

			//Persiste grid e atualiza value dos form do tipo grid com id
			vivoTask = saveGridBusinessOperation.execute(vivoTask);
			
			//Converte lista de items da tela para Map e remove itens not writable
			//getTaskService().setVariablesLocal(vivoTask.getTaskId(),ConverterUtil.transformFormItemsToMap(vivoTask.getFormItens()));
			getFormService().saveFormData(vivoTask.getTaskId(),ConverterUtil.transformFormItemsToMap(vivoTask.getFormItens()));

		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
		return null;
	}
}
