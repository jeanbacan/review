package br.com.vivo.bcm.business.operation.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.vivo.bcm.business.dao.IConfigurationDAO;
import br.com.vivo.bcm.business.model.Configuration;
import br.com.vivo.bcm.business.operation.IBusinessOperation;

@Named("listConfigurationsBusinessOperation")
public class ListConfigurationsBusinessOperation implements IBusinessOperation<Void, List<Configuration>> {

	@Inject
	IConfigurationDAO dao;

	@Override
	public List<Configuration> execute(Void param) {
		List<Configuration> configurations = dao.findAll();
		return configurations;
	}
}
