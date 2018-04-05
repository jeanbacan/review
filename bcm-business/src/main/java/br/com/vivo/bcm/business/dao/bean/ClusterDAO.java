package br.com.vivo.bcm.business.dao.bean;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IClusterDAO;
import br.com.vivo.bcm.business.model.Cluster;

/**
 * @author G0029875
 */
@Named("clusterDAO")
public class ClusterDAO extends JPABaseDAO<Long, Cluster> implements IClusterDAO {

	private static final Logger logger = Logger.getLogger(ClusterDAO.class);

	/**
	 * Construtor básico para classe de acesso a dados (DAO)
	 *
	 * @param manager - EntityManager
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cluster> findByUF(Long ufId) {
		logger.debug("findByDiretoria - begin");
		List<Cluster> result = null;
		Query q = this.entityManager.createQuery("FROM Cluster t LEFT JOIN FETCH t.uf u where u.id = :ufId");
		q.setParameter("ufId", ufId);

		result = (List<Cluster>) q.getResultList();
		logger.debug("findByDiretoria - end - diretoria: " + ufId);
		return result;
	}
}
