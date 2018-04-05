package br.com.vivo.bcm.business.enums;

/**
 * Responsavel pelas chaves de parâmetros de seguranca em aplicação externa
 * @author Jean Bacan
 * @since 10/05/2017
 */
public enum SecurityType {
	
		
	READ_TASKS("READ_TASKS"),
	CANDIDATE_GROUP("CANDIDATE_GROUP");

	private String key;

	private SecurityType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}