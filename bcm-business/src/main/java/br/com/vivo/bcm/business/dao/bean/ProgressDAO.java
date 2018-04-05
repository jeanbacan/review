package br.com.vivo.bcm.business.dao.bean;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IProgressDAO;
import br.com.vivo.bcm.business.model.Progress;

/**
 * @author G0029875
 */
@Named("progressDAO")
public class ProgressDAO extends JPABaseDAO<Long, Progress> implements IProgressDAO {

	private static final Logger logger = Logger.getLogger(UFDAO.class);
	
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
	public List<Progress> findAll() {
		logger.debug("findAll - begin");
		List<Progress> result = null;
		Query q = this.entityManager.createQuery("FROM Progress ORDER BY processOrder, value ASC");
		result = q.getResultList();
		logger.debug("findAll - end - " + result.size());
		return result;
	}
}
