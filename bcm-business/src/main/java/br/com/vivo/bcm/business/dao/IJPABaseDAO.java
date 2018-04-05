package br.com.vivo.bcm.business.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.vivo.bcm.business.model.IUID;

/**
 * Interface com os contratos básicos para uma classe DAO (Data Access Object).
 * 
 * @author Marcelo Paes Rech
 * @param <B> tipo da chave primária da entidade.
 * @param <T> tipo da entidade.
 */
public interface IJPABaseDAO<B extends Serializable, T extends IUID<B>> extends IBaseDAO<B, T> {

	/**
	 * Retorna o entityManager.
	 * 
	 * @return o entityManager.
	 */
	EntityManager getEntityManager();

	/**
	 * Lista todas as entidades.
	 * <p>
	 * Os campos do parâmetro <code>filter</code> serão usados para montar a condição <code>WHERE</code>. Só serão usados os campos que:
	 * <ul>
	 * <li>Não são estáticos.
	 * <li>O valor é diferente de <code>null</code>.
	 * <li>Não possuem a anotação {@link javax.persistence.Transient}.
	 * </ul>
	 * 
	 * @param filter o objeto cujos campos serão usados como filtro. Caso seja <code>null</code> não será usado.
	 * @return uma lista com todas as entidades.
	 */
	List<T> find(T filter);

	/**
	 * Lista todas as entidades.
	 * <p>
	 * Os campos do parâmetro <code>filter</code> serão usados para montar a condição <code>WHERE</code>. Só serão usados os campos que:
	 * <ul>
	 * <li>Não são estáticos.
	 * <li>O valor é diferente de <code>null</code>.
	 * <li>Não possuem a anotação {@link javax.persistence.Transient}.
	 * </ul>
	 * 
	 * @param filter o objeto cujos campos serão usados como filtro. Caso seja <code>null</code> não será usado.
	 * @param offset a posição da primeira entidade retornada. Caso seja <code>null</code> não será usado.
	 * @param limit número máximo de entidades retornadas. Caso seja <code>null</code> não será usado.
	 * @return uma lista com todas as entidades.
	 */
	List<T> find(T filter, Integer offset, Integer limit);

	/**
	 * Conta a quantidade de entidades.
	 * <p>
	 * Os campos do parâmetro <code>filter</code> serão usados para montar a condição <code>WHERE</code>. Só serão usados os campos que:
	 * <ul>
	 * <li>Não são estáticos.
	 * <li>O valor é diferente de <code>null</code>.
	 * <li>Não possuem a anotação {@link javax.persistence.Transient}.
	 * </ul>
	 * 
	 * @param filter o objeto cujos campos serão usados como filtro. Caso seja <code>null</code> não será usado.
	 * @return a quantidade de entidades.
	 */
	int count(T filter);
}