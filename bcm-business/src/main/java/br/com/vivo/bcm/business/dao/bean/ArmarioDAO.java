package br.com.vivo.bcm.business.dao.bean;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IArmarioDAO;
import br.com.vivo.bcm.business.model.Armario;

/**
 * @author G0029875
 */
@Named("armarioDAO")
public class ArmarioDAO extends JPABaseDAO<Long, Armario> implements IArmarioDAO {

	private static final Logger logger = Logger.getLogger(ArmarioDAO.class);

	/**
	 * Construtor básico para classe de acesso a dados (DAO)
	 *
	 * @param manager
	 *            - EntityManager
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Armario> findByCidade(Long cidadeId) {
		logger.debug("Armarios findByCidade - begin");
		List<Armario> result = null;
		Query q = this.entityManager.createQuery("FROM Armario t LEFT JOIN FETCH t.cidade u where u.id = :cidadeId ORDER BY t.name ASC");
		q.setParameter("cidadeId", cidadeId);

		result = (List<Armario>) q.getResultList();
		logger.debug("Armarios findByCidade - end - cidade: " + cidadeId);
		return result;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dao.IArmarioDAO#findByPrimaryKeys(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Armario> findByPrimaryKeys(List<Long> ids) {
		logger.debug("Armarios findByPrimaryKeys - begin");
		List<Armario> result = null;
		Query q = this.entityManager.createQuery("FROM Armario where id IN (:ids)");
		q.setParameter("ids", ids);

		result = (List<Armario>) q.getResultList();
		logger.debug("Armarios findByPrimaryKeys - end - ids: " + ids);
		return result;
	}
}