package br.com.vivo.bcm.business.builder.bean;

import java.util.Arrays;
import java.util.List;

import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.util.ApplicationConstants;

/**
 * POssui metodos de base para construções de SQL statement
 * @author Jean Marcel
 * @since 18/07/17
 *
 */
public class BaseSQLBuilder {

	/**
	 * Resgata nome digitado para ser filtrado
	 * 
	 * @param FilterDTO Objecto com parâmetros de filtro
	 * @return String Nome da tarefa
	 */
	@Deprecated
	protected String getBusinessKey(FilterDTO filterDTO) {
		if (filterDTO.getConstraints() != null){
			if (filterDTO.getConstraints().get(ApplicationConstants.FILTER_BUSINESS_KEY) != null) {
				return filterDTO.getConstraints().get(ApplicationConstants.FILTER_BUSINESS_KEY).getValue();
			}	
		}
		return null;
	}
	
	/**
	 * Resgata parâmetro UF
	 * @param FilterDTO Objeto filtro
	 * @return String UF Selecionada
	 */
	@Deprecated
	protected String getUFFilter(FilterDTO filterDTO) {
		if (filterDTO.getConstraints() != null) {
			if (filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_UF) != null) {
				return filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_UF).getValue();
			}
		}
		return null;
	}
	
	
	/**
	 * Resgata grupos de negocio 
	 * @param FilterDTO Objeto filtro
	 * @return List<String> Grupos de negocio a serem filtrados
	 */
	@Deprecated
	protected List<String> getGroupsFilter(FilterDTO filterDTO) {
		if (filterDTO.getConstraints() != null) {
			if (filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_GROUP) != null) {
				String[] values = filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_GROUP).getValue().split(",");
				return Arrays.asList(values); 
			}
		}
		return null;
	}	
	
	@Deprecated
	protected String getGroupName(FilterDTO filterDTO) {
		if (filterDTO.getConstraints() != null) {
			if (filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_GROUP) != null) {
				return filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_GROUP).getValue();
			}
		}
		return null;
	}

	@Deprecated
	protected String getStatusFilter(FilterDTO filterDTO) {
		if (filterDTO.getConstraints() != null) {
			if (filterDTO.getConstraints().get(ApplicationConstants.FILTER_PROCESS_STATUS) != null) {
				return filterDTO.getConstraints().get(ApplicationConstants.FILTER_PROCESS_STATUS).getValue();
			}
		}
		return null;
	}
}
