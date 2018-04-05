package br.com.vivo.bcm.business.dao.bean;

import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.ITokenDAO;
import br.com.vivo.bcm.business.model.Token;

/**
 * @author G0029875
 */
@Named("tokenDAO")
public class TokenDAO extends JPABaseDAO<Long, Token> implements ITokenDAO {

	private static final Logger logger = Logger.getLogger(TokenDAO.class);

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
	public List<Token> findAll() {
		logger.debug("findAll - begin");
		List<Token> result = null;
		Query q = this.entityManager.createQuery("FROM Token");
		result = q.getResultList();
		logger.debug("findAll - end - " + result.size());
		return result;
	}

	@Override
	public Token validadeTokenByHash(String tokenHash) {
		logger.debug("findByHash - begin");
		Token result = null;
		Query q = this.entityManager.createQuery("FROM Token t LEFT JOIN FETCH t.user u where t.hash = :tokenHash and t.expirationDate > :currentDate");
		q.setParameter("tokenHash", tokenHash);
		q.setParameter("currentDate", new Date());

		result = (Token) q.getSingleResult();
		logger.debug("findByHash - end - TokenHash: " + result.getHash());
		return result;
	}
}