package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.Cluster;

/**
 * @author G0029875
 */
public interface IClusterDAO extends IJPABaseDAO<Long, Cluster> {

	List<Cluster> findByUF(Long ufId);
}