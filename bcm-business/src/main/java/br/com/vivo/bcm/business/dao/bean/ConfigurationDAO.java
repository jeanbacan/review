package br.com.vivo.bcm.business.dao.bean;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import br.com.vivo.bcm.business.dao.IConfigurationDAO;
import br.com.vivo.bcm.business.model.Configuration;

@Named("configurationDAO")
public class ConfigurationDAO extends JPABaseDAO<Long, Configuration> implements IConfigurationDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Configuration> findAll() {
		Query q = getEntityManager().createQuery("SELECT o FROM Configuration o");
		return q.getResultList();
	}
}