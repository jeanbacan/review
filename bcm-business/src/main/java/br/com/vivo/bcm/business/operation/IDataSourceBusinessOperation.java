package br.com.vivo.bcm.business.operation;

import java.util.List;

import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;

/**
 * Interface para operações de negócio DS.
 * 
 * @author Jean Bacan
 */
public interface IDataSourceBusinessOperation extends IBusinessOperation<ComboBoxDTO, List<ComboBoxValueDTO>>{

	/**
	 * Utilizado para buscar apenas resultado selecionado.
	 * @param Long id
	 * @return List<ComboBoxValueDTO> Retorna Apenas a opção escolhida
	 */
	public List<ComboBoxValueDTO> findByKey(Long id);
	
	/**
	 * Utilizado para buscar apenas resultado selecionado.
	 * @param Long id
	 * @return List<ComboBoxValueDTO> Retorna Apenas a opção escolhida
	 */
	public List<ComboBoxValueDTO> findByKeys(List<Long> ids);
	
}