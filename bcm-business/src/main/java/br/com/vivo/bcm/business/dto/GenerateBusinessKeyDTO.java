/**
 * 
 */
package br.com.vivo.bcm.business.dto;

import java.util.List;

/**
 * Retorna resultado da query com total de tarefas por candidate group.
 * 
 * @author Jean Bacan
 * @since 05/06/2017
 */
public class GenerateBusinessKeyDTO {

	private int totalTasks;

	private int totalFacility;

	private List<CountTasksItemDTO> countTaskItems;

	/**
	 * @return the totalTasks
	 */
	public int getTotalTasks() {
		return totalTasks;
	}

	/**
	 * @param totalTasks the totalTasks to set
	 */
	public void setTotalTasks(int totalTasks) {
		this.totalTasks = totalTasks;
	}

	/**
	 * @return the totalFacility
	 */
	public int getTotalFacility() {
		return totalFacility;
	}

	/**
	 * @param totalFacility the totalFacility to set
	 */
	public void setTotalFacility(int totalFacility) {
		this.totalFacility = totalFacility;
	}

	/**
	 * @return the countTaskItems
	 */
	public List<CountTasksItemDTO> getCountTaskItems() {
		return countTaskItems;
	}

	/**
	 * @param countTaskItems the countTaskItems to set
	 */
	public void setCountTaskItems(List<CountTasksItemDTO> countTaskItems) {
		this.countTaskItems = countTaskItems;
	}
}
