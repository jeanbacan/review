package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.Configuration;

public interface IConfigurationDAO extends IJPABaseDAO<Long, Configuration> {
	List<Configuration> findAll();
}