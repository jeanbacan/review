package br.com.vivo.bcm.business.model;

import java.io.Serializable;

/**
 * Descreve o contrato de uma entidade que possui uma chave (UID)
 * 
 * @author Marcelo Paes Rech
 */
public interface IUID<T extends Serializable> {

	/**
	 * Retorna a chave primária da entidade.
	 * 
	 * @return a chave primária da entidade.
	 */
	public T getUid();

	public void setUid(T uid);
}