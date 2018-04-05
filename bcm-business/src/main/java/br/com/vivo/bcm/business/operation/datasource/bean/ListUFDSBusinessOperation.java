package br.com.vivo.bcm.business.operation.datasource.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IUFDAO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.UF;
import br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation;
import br.com.vivo.bcm.business.operation.bean.BaseBusinessOperation;

@Named("listUFDSBusinessOperation")
public class ListUFDSBusinessOperation extends BaseBusinessOperation implements IDataSourceBusinessOperation {

	private static final Logger logger = Logger.getLogger(ListUFDSBusinessOperation.class);

	@Inject
	@Named("ufDAO")
	private IUFDAO ufDAO;

	@Override
	public List<ComboBoxValueDTO> execute(ComboBoxDTO comboBoxFilter) throws BusinessException {
		logger.info("ListUFDSBusinessOperation - Consultando");
		
		List<UF> ufs;
		
		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = null;
		
		
		if (comboBoxFilter != null && comboBoxFilter.getValue() != null){
			logger.debug("ListUFDSBusinessOperation - Consultando Key: " + comboBoxFilter.getValue());
			
			ufs = ufDAO.findByDiretoria(Long.parseLong(comboBoxFilter.getValue()));
			
		} else {
			ufs = ufDAO.findAll();
		}
		
		for (UF uf : ufs){
			comboBoxValueDTO = new ComboBoxValueDTO();
			
			comboBoxValueDTO.setKey(uf.getUid());
			comboBoxValueDTO.setValue(uf.getName());
			
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
		
		
		UF uf = ufDAO.findByPrimaryKey(id);
			
		comboBoxValueDTO.setKey(uf.getUid());
		comboBoxValueDTO.setValue(uf.getName());
		
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
