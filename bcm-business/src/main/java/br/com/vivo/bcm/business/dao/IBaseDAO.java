package br.com.vivo.bcm.business.dao;

import java.io.Serializable;
import java.util.List;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.IUID;

/**
 * @author Deividi Cavarzan
 */
public interface IBaseDAO<B extends Serializable, T extends IUID<B>> {

	/**
	 * Persiste a entidade na base.
	 * 
	 * @param t a entidade a ser persistida.
	 * @return a entidade persistida.
	 * @throws BusinessException
	 */
	T save(T t) throws BusinessException;

	/**
	 * Exclui a entidade da base.
	 * 
	 * @param t a entidade a ser excluída.
	 */
	void remove(T t);

	/**
	 * Pesquisa pela entidade por chave primária.
	 * 
	 * @param b a chave primária da entidade a ser pesquisada.
	 * @return a entidade pesquisada ou <code>null</code> caso a entidade não exista.
	 */
	T findByPrimaryKey(B b);

	/**
	 * Lista todas as entidades.
	 * 
	 * @return uma lista com todas as entidades.
	 */
	List<T> find();

	/**
	 * Lista as entidades.
	 * 
	 * @param offset a posição da primeira entidade retornada.
	 * @param limit o número máximo de entidades retornadas.
	 * @return uma lista com as entidades.
	 */
	List<T> find(int offset, int limit);

	/**
	 * Conta a quantidade de entidades.
	 * 
	 * @return a quantidade de entidades.
	 */
	int count();
}