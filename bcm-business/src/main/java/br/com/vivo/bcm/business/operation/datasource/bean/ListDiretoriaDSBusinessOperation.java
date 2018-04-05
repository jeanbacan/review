package br.com.vivo.bcm.business.operation.datasource.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IDiretoriaDAO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Diretoria;
import br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation;
import br.com.vivo.bcm.business.operation.bean.BaseBusinessOperation;

@Named("listDiretoriaDSBusinessOperation")
public class ListDiretoriaDSBusinessOperation extends BaseBusinessOperation implements IDataSourceBusinessOperation {

	private static final Logger logger = Logger.getLogger(ListDiretoriaDSBusinessOperation.class);

	@Inject
	@Named("diretoriaDAO")
	private IDiretoriaDAO diretoriaDAO;

	@Override
	public List<ComboBoxValueDTO> execute(ComboBoxDTO comboBoxFilter) throws BusinessException {
		logger.info("ListDiretoriaDSBusinessOperation - Consultando");

		List<Diretoria> diretorias;
		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = null;

		diretorias = diretoriaDAO.findAll();

		for (Diretoria dir : diretorias) {
			comboBoxValueDTO = new ComboBoxValueDTO();
			
			comboBoxValueDTO.setKey(dir.getUid());
			comboBoxValueDTO.setValue(dir.getName());
			
			result.add(comboBoxValueDTO);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation#findByKey(java.lang.Long)
	 */
	@Override
	public List<ComboBoxValueDTO> findByKey(Long id) {
		logger.info("findByKey - Consultando");
		logger.debug("findByKey - Consultando Key: " + id);
		
		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = new ComboBoxValueDTO();
		
		Diretoria diretoria = diretoriaDAO.findByPrimaryKey(id);
			
		comboBoxValueDTO.setKey(diretoria.getUid());
		comboBoxValueDTO.setValue(diretoria.getName());
		
		result.add(comboBoxValueDTO);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation#findByKeys(java.util.List)
	 */
	@Override
	public List<ComboBoxValueDTO> findByKeys(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}
}
