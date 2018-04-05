package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.Cidade;

/**
 * @author G0029875
 */
public interface ICidadeDAO extends IJPABaseDAO<Long, Cidade> {

	List<Cidade> findByUF(Long ufId);
	
}
