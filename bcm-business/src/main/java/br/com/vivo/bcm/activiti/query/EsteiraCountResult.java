package br.com.vivo.bcm.activiti.query;

/**
 * Bean mapeado e utilizado pelas consultas de totalizadores da esteira
 * 
 * @author Jean Bacan
 * @since 26/12/2017
 */
public class EsteiraCountResult {

	private int count;
	
	private int facilidades;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	
	/**
	 * @return the facilidades
	 */
	public int getFacilidades() {
		return facilidades;
	}

	
	/**
	 * @param facilidades the facilidades to set
	 */
	public void setFacilidades(int facilidades) {
		this.facilidades = facilidades;
	}

}
