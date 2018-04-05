package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.Diretoria;

/**
 * @author G0029875
 */
public interface IDiretoriaDAO extends IJPABaseDAO<Long, Diretoria> {
	
	List<Diretoria> findAll();

}