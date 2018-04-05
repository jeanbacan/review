/**
 * 
 */
package br.com.vivo.bcm.business.operation.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.vivo.bcm.business.dao.IActivitiFormVariablesDAO;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.ActivitiFormVariables;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.util.ConverterUtil;
import br.com.vivo.bcm.business.vo.VivoTask;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

/**
 * Salva / Atualiza Grid 
 * 
 * @author Jean Bacan
 * @since 18/07/2017
 *
 */
@Named("saveGridBusinessOperation")
public class SaveGridBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<VivoTask, VivoTask> {

	private static final Logger logger = Logger.getLogger(CompleteTaskBusinessOperation.class);
	
	@Inject
	@Named("activitiFormVariablesDAO")
	private IActivitiFormVariablesDAO activitiFormVariablesDAO;
	
	@Override
	@Transactional
	public VivoTask execute(VivoTask param) throws BusinessException {
		logger.info("execute do SaveActivitiUserBusinessOperation.");
		
		ActivitiFormVariables filter = new ActivitiFormVariables();
		filter.setTaskId(param.getTaskId());		
		
		for (VivoTaskFormItem item : param.getFormItens()){
			
			if (VivoTaskFormType.DATAGRID.equals(item.getType())){
				filter.setVarName(item.getId());

				//Valida se existe grid salvo, caso sim atualiza, senao persiste.
				List<ActivitiFormVariables> gridForm = activitiFormVariablesDAO.find(filter);

				//Cria objeto para save
				ActivitiFormVariables grid = ConverterUtil.buildActivitiFormVariable(param.getTaskId(), item);
				
				if (gridForm != null && gridForm.size() > 0){
					grid.setUid(gridForm.get(0).getUid());
				}
				activitiFormVariablesDAO.save(grid);
				
				//Atualiza formItem com valor do Id
				item.setValue(grid.getUid().toString());
			}
		}
		return param;
	}
}
