package br.com.vivo.bcm.business.enums;

public enum ProcessInstanceStatusType {
	
	ACTIVE("Em andamento"),
	FINISHED("Concluído"),
	CANCELED("Cancelado");
	
	private String key;

	private ProcessInstanceStatusType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}