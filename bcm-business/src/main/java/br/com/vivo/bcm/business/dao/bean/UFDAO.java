package br.com.vivo.bcm.business.dao.bean;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IUFDAO;
import br.com.vivo.bcm.business.model.UF;

/**
 * @author G0029875
 */
@Named("ufDAO")
public class UFDAO extends JPABaseDAO<Long, UF> implements IUFDAO {

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
	public List<UF> findAll() {
		logger.debug("findAll - begin");
		List<UF> result = null;
		Query q = this.entityManager.createQuery("FROM UF ORDER BY name");
		result = q.getResultList();
		logger.debug("findAll - end - " + result.size());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UF> findByDiretoria(Long diretoriaId) {
		logger.debug("findByDiretoria - begin");
		List<UF> result = null;
		Query q = this.entityManager.createQuery("FROM UF t LEFT JOIN FETCH t.diretoria u where u.id = :diretoriaid ORDER BY t.name");
		q.setParameter("diretoriaid", diretoriaId);

		result = (List<UF>) q.getResultList();
		logger.debug("findByDiretoria - end - diretoria: " + diretoriaId);
		return result;
	}
}