package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.Progress;

/**
 * @author G0029875
 */
public interface IProgressDAO extends IJPABaseDAO<Long, Progress> {
	List<Progress> findAll();
}