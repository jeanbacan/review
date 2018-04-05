package br.com.vivo.bcm.business.enums;

public enum ArmarioParamsType {

	CLIENT("client"),
	
	ASSUNTO("assunto"),

	ORIGEM("origem"),

	DESTINO("destino"),

	OSF("osf"),

	PERIOD("period"),

	SCHEDULED_DATE("scheduledDate"),

	ADDRESS("address"),

	COMPLEMENT("complement"),

	CITY("city"),

	STATE("state"),
	
	ZIP_CODE("zipCode"),

	NEIGHBORHOOD("neighborhood"),
	
	REPLAY_TO("replayTo"),
	
	VIVO_CHAT_TOKEN("vivoChatToken"),
	
	MANUALS_REGULATIONS_TOKEN("manualsRegulationsToken"),
	
	BROWSE_MAIL_TOKEN("browseMailToken");

	private String param;

	private ArmarioParamsType(String param) {
		this.param = param;
	}

	public String getNameParam() {
		return this.param;
	}

}
