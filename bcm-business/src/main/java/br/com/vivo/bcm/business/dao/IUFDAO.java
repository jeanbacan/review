package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.UF;

/**
 * @author G0029875
 */
public interface IUFDAO extends IJPABaseDAO<Long, UF> {
	
	List<UF> findAll();

	List<UF> findByDiretoria(Long diretoriaId);
}