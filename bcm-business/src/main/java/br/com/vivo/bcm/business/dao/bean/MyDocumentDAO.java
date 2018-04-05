/**
 * 
 */
package br.com.vivo.bcm.business.dao.bean;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IMyDocumentDAO;
import br.com.vivo.bcm.business.model.MyDocument;

/**
 * @author P9923900
 *
 */
@Named("myDocumentDAO")
public class MyDocumentDAO extends JPABaseDAO<Long, MyDocument> implements IMyDocumentDAO {

	private static final Logger logger = Logger.getLogger(MyDocumentDAO.class);

	/**
	 * Construtor básico para classe de acesso a dados (DAO)
	 *
	 * @param manager
	 *            - EntityManager
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MyDocument findById(Long uid) {

		logger.debug("findById - begin");

		MyDocument result = null;
		Query q = this.entityManager.createQuery("FROM MyDocument d where d.uid = :uid");
		q.setParameter("uid", uid);

		try {
			result = (MyDocument) q.getSingleResult();
		} catch (NoResultException exception) {
			logger.error(exception);
		}

		logger.debug("findById - end - " + result);
		return result;
	}

	@Override
	public MyDocument findByDocumentManagerId(Long uid) {

		logger.debug("findByDocumentManagerId - begin");

		MyDocument result = null;
		Query q = this.entityManager.createQuery("FROM MyDocument d where d.documentManagerId = :uid");
		q.setParameter("uid", uid);

		try {
			result = (MyDocument) q.getSingleResult();
		} catch (NoResultException exception) {
			logger.error(exception);
		}

		logger.debug("findByDocumentManagerId - end - " + result);
		return result;
	}
}