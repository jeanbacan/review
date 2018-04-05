package br.com.vivo.bcm.business.enums;

/**
 * Type para tipo de dados a ser extraido
 * 
 * @author G0054687
 *
 */
public enum ExtractorType {

	ARMARIOS("Armários");

	private String description;

	private ExtractorType(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
