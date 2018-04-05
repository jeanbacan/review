package br.com.vivo.bcm.business.dao.bean;

import java.math.BigDecimal;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IBusinessKeyDAO;

/**
 * @author G0029875
 */
@Named("businessKeyDAO")
public class BusinessKeyDAO implements IBusinessKeyDAO {

	private static final Logger logger = Logger.getLogger(BusinessKeyDAO.class);

	/**
	 * Construtor básico para classe de acesso a dados (DAO)
	 *
	 * @param manager - EntityManager
	 */
	@PersistenceContext
	private EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dao.IBusinessKeyDAO#nextval()
	 */
	@Override
	public Long nextval() {
		logger.debug("nextval");
		Query q = this.entityManager.createNativeQuery("SELECT BUSINESS_KEY_SEQ.nextval from dual");
		BigDecimal id = (BigDecimal) q.getSingleResult();		
		
		return id.longValue();
	}
}
