package br.com.vivo.bcm.business.operation.datasource.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IClusterDAO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Cluster;
import br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation;
import br.com.vivo.bcm.business.operation.bean.BaseBusinessOperation;

@Named("listClusterDSBusinessOperation")
public class ListClusterDSBusinessOperation extends BaseBusinessOperation implements IDataSourceBusinessOperation {

	private static final Logger logger = Logger.getLogger(ListClusterDSBusinessOperation.class);

	@Inject
	@Named("clusterDAO")
	private IClusterDAO clusterDAO;

	@Override
	public List<ComboBoxValueDTO> execute(ComboBoxDTO comboBoxFilter) throws BusinessException {
		logger.info("ListClusterDSBusinessOperation - Consultando");
		
		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = null;

		List<Cluster> clusters = new ArrayList<Cluster>();

		if (comboBoxFilter != null && comboBoxFilter.getValue() != null) {
			logger.debug("ListClusterDSBusinessOperation - Consultando Key: " + comboBoxFilter.getValue());
			clusters = clusterDAO.findByUF(Long.parseLong(comboBoxFilter.getValue()));
		}

		for (Cluster cluster : clusters) {
			comboBoxValueDTO = new ComboBoxValueDTO();
			
			comboBoxValueDTO.setKey(cluster.getUid());
			comboBoxValueDTO.setValue(cluster.getName());
			
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
	public List<ComboBoxValueDTO> findByKey(Long id) {
		logger.info("findByKey - Consultando");
		logger.debug("findByKey - Consultando Key: " + id);

		List<ComboBoxValueDTO> result = new ArrayList<ComboBoxValueDTO>();
		ComboBoxValueDTO comboBoxValueDTO = new ComboBoxValueDTO();

		Cluster cluster = clusterDAO.findByPrimaryKey(id);
		
		comboBoxValueDTO.setKey(cluster.getUid());
		comboBoxValueDTO.setValue(cluster.getName());

		result.add(comboBoxValueDTO);
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation#findByKeys(java.util.List)
	 */
	@Override
	public List<ComboBoxValueDTO> findByKeys(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
