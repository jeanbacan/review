package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.vivo.bcm.business.dao.IMyDocumentDAO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.MyDocument;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.MyDocumentVO;

/**
 * Chamado para informação que arquivo 
 * @author P9923900
 *
 */
@Named("callBackSuccessUploadBusinessOperation")
public class CallBackSuccessUploadBusinessOperation implements IBusinessOperation<MyDocumentVO, Void> {

	private static final Logger logger = Logger.getLogger(CallBackSuccessUploadBusinessOperation.class);

	@Inject
	@Named("myDocumentDAO")
	private IMyDocumentDAO myDocumentDAO;

	@Override
	@Transactional
	public Void execute(MyDocumentVO myDocumentVO) throws BusinessException {
		logger.info("execute do CallBackSuccessUploadBusinessOperation - inicio");
		MyDocument myDocument = this.myDocumentDAO.findById(myDocumentVO.getUid());

		myDocument.setIsUploaded(true);

		this.myDocumentDAO.save(myDocument);

		logger.info("execute do CallBackSuccessUploadBusinessOperation - fim");
		return null;
	}
}