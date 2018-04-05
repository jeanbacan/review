package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.Armario;

/**
 * @author G0029875
 */
public interface IArmarioDAO extends IJPABaseDAO<Long, Armario> {
	
	List<Armario> findByCidade(Long clusterId);
	
	List<Armario> findByPrimaryKeys(List<Long> ids);
}