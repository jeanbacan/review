package br.com.vivo.bcm.business.operation.datasource.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IProgressDAO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxTaskValueDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Progress;
import br.com.vivo.bcm.business.operation.IBusinessOperation;

@Named("listTasksDSBusinessOperation")
public class ListTasksDSBusinessOperation implements IBusinessOperation<ComboBoxDTO, List<ComboBoxTaskValueDTO>> {

	private static final Logger logger = Logger.getLogger(ListTasksDSBusinessOperation.class);

	@Inject
	@Named("progressDAO")
	private IProgressDAO progressDAO;

	@Override
	public List<ComboBoxTaskValueDTO> execute(ComboBoxDTO comboBoxFilter) throws BusinessException {
		logger.info("ListUFDSBusinessOperation - Consultando");
		
		List<Progress> progress;
		
		List<ComboBoxTaskValueDTO> result = new ArrayList<ComboBoxTaskValueDTO>();
		ComboBoxTaskValueDTO comboBoxValueDTO = null;
		
		progress = progressDAO.findAll();
		
		for (Progress prog : progress){
			comboBoxValueDTO = new ComboBoxTaskValueDTO();
			
			comboBoxValueDTO.setKey(prog.getTaskDefinitionKey());
			comboBoxValueDTO.setValue(prog.getTaskName());
			
			result.add(comboBoxValueDTO);
		}
		return result;
	}
}
