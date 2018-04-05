package br.com.vivo.bcm.rest.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.exception.ActivitiErrorException;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.DataExtractorException;
import br.com.vivo.bcm.business.exception.EmptyFieldException;
import br.com.vivo.bcm.business.exception.ErrorSendEmailException;
import br.com.vivo.bcm.business.exception.MissingInformationException;
import br.com.vivo.bcm.business.exception.MissingTypesGroups;
import br.com.vivo.bcm.business.exception.NotAllowedException;
import br.com.vivo.bcm.business.exception.NotFoundUserInGroupException;
import br.com.vivo.bcm.business.exception.ProcessDefinitionKeyEmptyException;
import br.com.vivo.bcm.business.exception.UserInactiveException;
import br.com.vivo.bcm.business.exception.UserNotFoundException;
import br.com.vivo.bcm.business.vo.ErrorVO;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

	private static final Logger logger = Logger.getLogger(GlobalExceptionController.class);

	@ExceptionHandler({ NotFoundUserInGroupException.class, UserNotFoundException.class })
	public ResponseEntity<Object> handleErrorNotFound(BusinessException businessException, WebRequest request) {
		return handleExceptionInternal(businessException, new ErrorVO(businessException), this.getHttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ UserInactiveException.class, NotAllowedException.class })
	public ResponseEntity<Object> handleErrorUnauthorized(BusinessException businessException, WebRequest request) {
		return handleExceptionInternal(businessException, new ErrorVO(businessException), this.getHttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler({ MissingTypesGroups.class, ProcessDefinitionKeyEmptyException.class })
	public ResponseEntity<Object> handleErrorNotAcceptable(BusinessException businessException, WebRequest request) {
		return handleExceptionInternal(businessException, new ErrorVO(businessException), this.getHttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler({ EmptyFieldException.class, ErrorSendEmailException.class, MissingInformationException.class })
	public ResponseEntity<Object> handleErrorEmptyFields(BusinessException businessException, WebRequest request) {
		return handleExceptionInternal(businessException, new ErrorVO(businessException), this.getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ ActivitiErrorException.class })
	public ResponseEntity<Object> handleErrorActivitiErrorException(BusinessException businessException, WebRequest request) {
		return handleExceptionInternal(businessException, new ErrorVO(businessException.getActivitiErrorCode(), businessException.getMessage()), this.getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ DataExtractorException.class })
	public ResponseEntity<Object> handleErrorExtractorException(BusinessException businessException, WebRequest request) {
		return handleExceptionInternal(businessException, new ErrorVO(businessException.getActivitiErrorCode(), businessException.getMessage()), this.getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleErrorDefault(Exception exception, WebRequest request) {
		ErrorVO errorVO = new ErrorVO();
		errorVO.setCode(ErrorCode.GENERIC.getCode());
		errorVO.setDescription(ErrorCode.GENERIC.getDescription());
		errorVO.setComments(ErrorCode.GENERIC.getComments());

		logger.error(errorVO.toString(), exception);

		return handleExceptionInternal(exception, errorVO, this.getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return httpHeaders;
	}
}