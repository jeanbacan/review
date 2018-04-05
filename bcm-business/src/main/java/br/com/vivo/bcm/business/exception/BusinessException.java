package br.com.vivo.bcm.business.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.enums.ErrorCode;

/**
 * Exceção de negócios do Harmonic.<br>
 * Poderá ser lançada para contrato de comunicação em caso de erros com outras camadas e para a API.
 * 
 * @author Deividi Cavarzan
 * @since 27/08/13
 */
public class BusinessException extends Exception implements IBaseException {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(BusinessException.class);

	private transient List<InvalidObjectInfo> invalidObjects = new ArrayList<InvalidObjectInfo>();

	/** Código específico para uma exceção. */
	private ErrorCode errorCode;

	/** Código especifico para exceções provenientes do Activiti **/
	private String activitiErrorCode;

	/**
	 * Construção padrão com uma mensagem de erro.
	 * 
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Exception e) {
		super(e);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException() {
		super();
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
		logger.info(this);
	}

	public BusinessException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getDescription(), cause);
		this.errorCode = errorCode;
		logger.info(this);
	}

	public BusinessException(ErrorCode errorCode, String message) {
		super(errorCode.getDescription() + " - " + message);
		this.errorCode = errorCode;
		logger.info(this);
	}

	public BusinessException(String errorCode, String message) {
		super(message);
		this.activitiErrorCode = errorCode;
		logger.info(this);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * @return the invalidObjects
	 */
	public List<InvalidObjectInfo> getInvalidObjects() {
		return invalidObjects;
	}

	/**
	 * Adiciona um objeto inválido na lista de exceções.
	 * 
	 * @param objeto em que houve exceção.
	 */
	public void addInvalidObject(InvalidObjectInfo invalidObject) {
		this.invalidObjects.add(invalidObject);
	}

	/**
	 * Indica se existe um ou mais objetos inválidos.
	 * 
	 * @return <tt>true</tt> caso exista e <tt>false</tt> caso contrário.
	 */
	public boolean hasInvalidObjects() {
		return !invalidObjects.isEmpty();
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		if (this.errorCode != null) {
			return "Erro: " + this.errorCode.getCode() + " -> " + this.errorCode.getDescription();
		} else {
			return "Erro!";
		}
	}

	/**
	 * @return the activitiErrorCode
	 */
	public String getActivitiErrorCode() {
		return activitiErrorCode;
	}
}