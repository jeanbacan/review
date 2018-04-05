package br.com.vivo.bcm.business.operation.datasource.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.ICidadeDAO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Cidade;
import br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation;
import br.com.vivo.bcm.business.operation.bean.BaseBusinessOperation;

@Named("listCidadeDSBusinessOperation")
public class ListCidadeDSBusinessOperation extends BaseBusinessOperation implements IDataSourceBusinessOperation {

	private static final Logger logger = Logger.getLogger(ListCidadeDSBusinessOperation.class);

	@Inject
	@Named("cidadeDAO")
	private ICidadeDAO cidadeDAO;

	@Override
	public  List<ComboBoxValueDTO> execute(ComboBoxDTO comboBoxFilter) throws BusinessException {
		logger.info("ListCidadeDSBusinessOperation - Consultando");
		
		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = null;
		
		List<Cidade> cidades = new ArrayList<Cidade>();
		
		if (comboBoxFilter != null && comboBoxFilter.getValue() != null){
			logger.debug("ListCidadeDSBusinessOperation - Consultando Key: " + comboBoxFilter.getValue());
			cidades = cidadeDAO.findByUF(Long.parseLong(comboBoxFilter.getValue()));
		}
		
		for (Cidade cidade : cidades){
			comboBoxValueDTO = new ComboBoxValueDTO();
			
			comboBoxValueDTO.setKey(cidade.getUid());
			comboBoxValueDTO.setValue(cidade.getName());
			
			result.add(comboBoxValueDTO);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation#findByKey(java.lang.Long)
	 */
	@Override
	public  List<ComboBoxValueDTO> findByKey(Long id) {
		logger.info("findByKey - Consultando");
		logger.debug("findByKey - Consultando Key: " + id);

		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = new ComboBoxValueDTO();
		
		Cidade cidade = cidadeDAO.findByPrimaryKey(id);
		
		comboBoxValueDTO.setKey(cidade.getUid());
		comboBoxValueDTO.setValue(cidade.getName());
		
		result.add(comboBoxValueDTO);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation#findByKeys(java.util.List)
	 */
	@Override
	public  List<ComboBoxValueDTO> findByKeys(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}
}
