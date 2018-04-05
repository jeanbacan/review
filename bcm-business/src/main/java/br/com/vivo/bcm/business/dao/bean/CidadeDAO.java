package br.com.vivo.bcm.business.dao.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.ICidadeDAO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Cidade;

/**
 * @author G0029875
 */
@Named("cidadeDAO")
public class CidadeDAO extends JPABaseDAO<Long, Cidade> implements ICidadeDAO {

	private static final Logger logger = Logger.getLogger(CidadeDAO.class);

	/**
	 * Construtor básico para classe de acesso a dados (DAO)
	 *
	 * @param manager - EntityManager
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cidade> findByUF(Long ufId) {
		logger.debug("findByUF - begin");
		List<Cidade> result = null;
		Query q = this.entityManager.createQuery("FROM Cidade t LEFT JOIN FETCH t.uf u where u.id = :ufId ORDER BY t.name");
		q.setParameter("ufId", ufId);

		result = (List<Cidade>) q.getResultList();
		logger.debug("findByUF - end - uf: " + ufId);
		return result;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dao.bean.JPABaseDAO#save(br.com.vivo.bcm.business.model.IUID)
	 */
	@Override
	public Cidade save(Cidade t) throws BusinessException {
		logger.debug("findByUF - begin");
		
		Query q = this.entityManager.createNativeQuery("SELECT MAX(ID)+1 FROM Cidade");
		BigDecimal id = (BigDecimal) q.getSingleResult();
		
		t.setUid(id.longValue());
		
		return super.save(t);
	}
}
