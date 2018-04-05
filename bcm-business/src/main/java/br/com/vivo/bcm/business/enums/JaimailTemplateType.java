package br.com.vivo.bcm.business.enums;

public enum JaimailTemplateType {

	ASSUNTO("assunto"),
	ORIGEM("origem"),
	DESTINO("destino"),
	USER_NAME("username"),
	OWNER("owner"),
	BUSINESS_KEY("businessKey"),	
	DUE_DATE("dueDate"),
	CANDIDATE_GROUP("candidateGroup"),
	TASK_NAME("taskName"),
	TIPO_INVESTIMENTO("tipoInvestimento"),
	TIPO_TECNOLOGIA("tipoTecnologia"),
	TIPO_OBRA("tipoObra"),
	ID_USER_GROUP("idUserGroup"),
	DIRETORIA("diretoria"),
	UF("uf"),
	CIDADE("cidade");
	
	private String value;

	private JaimailTemplateType(String key) {
		this.value = key;
	}

	public String getValue() {
		return value;
	}
}