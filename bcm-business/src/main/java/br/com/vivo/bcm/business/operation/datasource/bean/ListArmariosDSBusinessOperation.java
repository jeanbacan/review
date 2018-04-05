package br.com.vivo.bcm.business.operation.datasource.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IArmarioDAO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Armario;
import br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation;
import br.com.vivo.bcm.business.operation.bean.BaseBusinessOperation;

@Named("listArmariosDSBusinessOperation")
public class ListArmariosDSBusinessOperation extends BaseBusinessOperation implements IDataSourceBusinessOperation {

	private static final Logger logger = Logger.getLogger(ListArmariosDSBusinessOperation.class);

	@Inject
	@Named("armarioDAO")
	private IArmarioDAO armarioDAO;

	@Override
	public List<ComboBoxValueDTO> execute(ComboBoxDTO comboBoxFilter) throws BusinessException {
		logger.info("ListArmariosDSBusinessOperation - Consultando");

		List<Armario> armarios = new ArrayList<Armario>();
		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = null;

		if (comboBoxFilter != null && comboBoxFilter.getValue() != null) {
			logger.debug("ListArmariosDSBusinessOperation - Consultando Key: " + comboBoxFilter.getValue());
			armarios = armarioDAO.findByCidade(Long.parseLong(comboBoxFilter.getValue()));
		}

		for (Armario arm : armarios) {
			comboBoxValueDTO = new ComboBoxValueDTO();

			comboBoxValueDTO.setKey(arm.getUid());
			comboBoxValueDTO.setValue(arm.getName());

			result.add(comboBoxValueDTO);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation#
	 * findByKeys(java.util.List)
	 */
	@Override
	public List<ComboBoxValueDTO> findByKeys(List<Long> ids) {
		logger.info("findByKey - Consultando");
		logger.debug("findByKey - Consultando Key: " + ids);

		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = null;

		List<Armario> armarios = armarioDAO.findByPrimaryKeys(ids);

		for (Armario arm : armarios) {
			comboBoxValueDTO = new ComboBoxValueDTO();

			comboBoxValueDTO.setKey(arm.getUid());
			comboBoxValueDTO.setValue(arm.getName());

			result.add(comboBoxValueDTO);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation#findByKey
	 * (java.lang.Long)
	 */
	@Override
	public List<ComboBoxValueDTO> findByKey(Long id) {
		logger.info("findByKey - Consultando");
		logger.debug("findByKey - Consultando Key: " + id);
		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = new ComboBoxValueDTO();

		Armario armario = armarioDAO.findByPrimaryKey(id);

		comboBoxValueDTO.setKey(armario.getUid());
		comboBoxValueDTO.setValue(armario.getName());

		result.add(comboBoxValueDTO);

		return result;
	}
}
