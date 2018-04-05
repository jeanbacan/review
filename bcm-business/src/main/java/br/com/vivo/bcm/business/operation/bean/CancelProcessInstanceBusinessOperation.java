package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dto.CancelProcessDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.EmptyFieldException;
import br.com.vivo.bcm.business.exception.NotAllowedException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;

@Named("cancelProcessInstanceBusinessOperation")
public class CancelProcessInstanceBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<CancelProcessDTO, Void> {

	private static final Logger logger = Logger.getLogger(CancelProcessInstanceBusinessOperation.class);
	
	@Override
	public Void execute(CancelProcessDTO cancelProcessDTO) throws BusinessException {

		if (cancelProcessDTO == null){
			logger.error("Parâmetro nulo.");
			throw new EmptyFieldException();
		}
		
		logger.info("execute do CancelProcessInstanceBusinessOperation .");
		logger.debug("execute do CancelProcessInstanceBusinessOperation ." + cancelProcessDTO.getProcessInstanceId());

		boolean isAdmin = super.securityHelper.isLoggedUserAdmin();
		
		if (isAdmin){
			getRuntimeService().deleteProcessInstance(cancelProcessDTO.getProcessInstanceId(), cancelProcessDTO.getReason());	
		} else {
			throw new NotAllowedException();				
		}
		return null;
	}
}