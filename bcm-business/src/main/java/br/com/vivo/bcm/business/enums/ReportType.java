package br.com.vivo.bcm.business.enums;

/**
 * Type para tipo form VOLines
 * 
 * @author 80477699
 *
 */
public enum ReportType {

	ARMARIO("Armario"),
	NOME_AREA("NomeArea"),
	
	MEDIA_LINHA_B2C("MediaLinha(B2C)"),
	TX_PENETRACAO_PREDIO_B2C("TxPenetracaoPredio(B2C)"),
	TX_PENETRACAO_B2C("TxPenetracao(B2C)"),
	TX_PENETRACAO_PREDIO_B2B("TxPenetracaoPredio(B2B)"),
	TX_PENETRACAO_B2B("TxPenetracao(B2B)"),
	DT_INICIO_PREVISAO_CM("DtInicioPrevisaoCM"),
	DT_INICIO_CM("DtInicioCM"),
	DT_FIM_PREVISAO_CM("DtFimPrevisaoCM"),
	DT_FIM_CM("DtFimCM"),
	DT_INICIO_PREVISAO_LM("DtInicioPrevisaoLM"),
	DT_INICIO_LM("DtInicioLM"),
	DT_FIM_PREVISAO_LM("DtFimPrevisaoLM"),
	DT_FIM_LM("DtFimLM"),
	FACILIDADE("Facilidade");

	private String description;

	private ReportType(String description) {
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
