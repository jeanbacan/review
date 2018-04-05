/**
 * 
 */
package br.com.vivo.bcm.business.dto;

/**
 * Retorna contadores de tasks por status (EM_ANDAMENTO, EM_ESTUDO, APROVADOS, REPROVADOS, CALENDARIZADOS, NAO_CALENDARIZADOS, OBRAS_CONCLUIDAS)
 * 
 * @author Jean Bacan
 * @since 26/12/2017
 */
public class CountEsteiraGroupDTO {

	private int totalTasks;

	private int totalFacility;

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

}
