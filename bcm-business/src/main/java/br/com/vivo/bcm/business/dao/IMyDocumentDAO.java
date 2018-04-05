package br.com.vivo.bcm.business.dao;

import br.com.vivo.bcm.business.model.MyDocument;

/**
 * @author P9923900
 *
 */
public interface IMyDocumentDAO extends IJPABaseDAO<Long, MyDocument> {
	MyDocument findById(Long uid);
	MyDocument findByDocumentManagerId(Long uid);
}