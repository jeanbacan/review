package br.com.vivo.bcm.activiti.formType;


/**
 * @author Marcelo Rech
 *
 */
public interface IFormTypeDataLoader {

	/**
	 * Retorna as informações do para o component 
	 * 
	 * @param argument
	 * @return
	 */
	public String loadContent(String...argument);
}
