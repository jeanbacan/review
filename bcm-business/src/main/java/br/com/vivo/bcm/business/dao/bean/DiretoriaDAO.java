package br.com.vivo.bcm.business.dao.bean;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IDiretoriaDAO;
import br.com.vivo.bcm.business.model.Diretoria;

/**
 * @author G0029875
 */
@Named("diretoriaDAO")
public class DiretoriaDAO extends JPABaseDAO<Long, Diretoria> implements IDiretoriaDAO {

	private static final Logger logger = Logger.getLogger(DiretoriaDAO.class);

	/**
	 * Construtor básico para classe de acesso a dados (DAO)
	 *
	 * @param manager - EntityManager
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Diretoria> findAll() {
		logger.debug("findAll - begin");
		List<Diretoria> result = null;
		Query q = this.entityManager.createQuery("FROM Diretoria");
		result = q.getResultList();
		logger.debug("findAll - end - " + result.size());
		return result;
	}

}
