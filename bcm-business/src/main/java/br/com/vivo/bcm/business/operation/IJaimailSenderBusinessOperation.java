package br.com.vivo.bcm.business.operation;

import br.com.vivo.bcm.business.dto.SenderObject;
import br.com.vivo.bcm.business.exception.BusinessException;

/**
 * @author Renato Siqueira
 * @since 24/02/2016
 */
public interface IJaimailSenderBusinessOperation extends IBusinessOperation<SenderObject, String> {

	String execute(SenderObject senderObject) throws BusinessException;

}
