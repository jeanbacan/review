package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dao.IMyDocumentDAO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.MyDocument;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.MyDocumentVO;

/**
 * @author P9923900
 *
 */
@Named("getMyDocumentByIdBusinessOperation")
public class GetMyDocumentByIdBusinessOperation implements IBusinessOperation<String, MyDocumentVO> {

	private static final Logger logger = Logger.getLogger(GetMyDocumentByIdBusinessOperation.class);

	@Inject
	@Named("myDocumentDAO")
	private IMyDocumentDAO myDocumentDAO;

	@Override
	public MyDocumentVO execute(String documentId) throws BusinessException {
		logger.info("execute do GetMyDocumentById - inicio.");

		MyDocument myDocument = this.myDocumentDAO.findByDocumentManagerId(Long.valueOf(documentId));

		logger.info("execute do GetMyDocumentById - termino.");

		return new MyDocumentVO(myDocument);
	}
}