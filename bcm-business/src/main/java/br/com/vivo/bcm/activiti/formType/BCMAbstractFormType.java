package br.com.vivo.bcm.activiti.formType;

import org.activiti.engine.form.AbstractFormType;

/**
 * @since 30/05/2017
 * @author Marcelo Rech
 *
 */
public abstract class BCMAbstractFormType extends AbstractFormType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2885543804243612281L;

	/**
	 * Identifica se as informações do form type são preenchidas posteriormente
	 * 
	 * @return
	 */
	public abstract boolean isLazyDataLoaderRequired();

	/**
	 * Identifica se as informações do form type são preenchidas posteriormente
	 * 
	 * @return
	 */
	public abstract IFormTypeDataLoader getFormTypeDataLoader();
}
