package br.com.vivo.bcm.business.vo;

/**
 * COlunas da grid convertidas a partir de um DTO (grid de valores salva em base).
 * 
 * @author Jean Bacan
 *
 */
public class GridReportVO {

	private String nomeArea;

	private String armario;

	private String mediaLinhasB2C;

	private String txPenetracaoB2C;

	private String txPenetracaoPredioB2C;

	private String mediaLinhasB2B;

	private String txPenetracaoB2B;

	private String txPenetracaoPredioB2B;

	private String inicioPrevistoCM;

	private String inicioCM;

	private String fimPrevistoCM;

	private String fimCM;

	private String inicioPrevistoLM;

	private String inicioLM;

	private String fimPrevistoLM;

	private String fimLM;

	private String demanda;
	
	private String facilidade;
	
	private String observacao;

	/**
	 * @return the nomeArea
	 */
	public String getNomeArea() {
		return nomeArea;
	}

	/**
	 * @param nomeArea the nomeArea to set
	 */
	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	/**
	 * @return the armario
	 */
	public String getArmario() {
		return armario;
	}

	/**
	 * @param armario the armario to set
	 */
	public void setArmario(String armario) {
		this.armario = armario;
	}

	/**
	 * @return the mediaLinhasB2C
	 */
	public String getMediaLinhasB2C() {
		return mediaLinhasB2C;
	}

	/**
	 * @param mediaLinhasB2C the mediaLinhasB2C to set
	 */
	public void setMediaLinhasB2C(String mediaLinhasB2C) {
		this.mediaLinhasB2C = mediaLinhasB2C;
	}

	/**
	 * @return the txPenetracaoB2C
	 */
	public String getTxPenetracaoB2C() {
		return txPenetracaoB2C;
	}

	/**
	 * @param txPenetracaoB2C the txPenetracaoB2C to set
	 */
	public void setTxPenetracaoB2C(String txPenetracaoB2C) {
		this.txPenetracaoB2C = txPenetracaoB2C;
	}

	/**
	 * @return the txPenetracaoPredioB2C
	 */
	public String getTxPenetracaoPredioB2C() {
		return txPenetracaoPredioB2C;
	}

	/**
	 * @param txPenetracaoPredioB2C the txPenetracaoPredioB2C to set
	 */
	public void setTxPenetracaoPredioB2C(String txPenetracaoPredioB2C) {
		this.txPenetracaoPredioB2C = txPenetracaoPredioB2C;
	}

	/**
	 * @return the mediaLinhasB2B
	 */
	public String getMediaLinhasB2B() {
		return mediaLinhasB2B;
	}

	/**
	 * @param mediaLinhasB2B the mediaLinhasB2B to set
	 */
	public void setMediaLinhasB2B(String mediaLinhasB2B) {
		this.mediaLinhasB2B = mediaLinhasB2B;
	}

	/**
	 * @return the txPenetracaoB2B
	 */
	public String getTxPenetracaoB2B() {
		return txPenetracaoB2B;
	}

	/**
	 * @param txPenetracaoB2B the txPenetracaoB2B to set
	 */
	public void setTxPenetracaoB2B(String txPenetracaoB2B) {
		this.txPenetracaoB2B = txPenetracaoB2B;
	}

	/**
	 * @return the txPenetracaoPredioB2B
	 */
	public String getTxPenetracaoPredioB2B() {
		return txPenetracaoPredioB2B;
	}

	/**
	 * @param txPenetracaoPredioB2B the txPenetracaoPredioB2B to set
	 */
	public void setTxPenetracaoPredioB2B(String txPenetracaoPredioB2B) {
		this.txPenetracaoPredioB2B = txPenetracaoPredioB2B;
	}

	/**
	 * @return the inicioPrevistoCM
	 */
	public String getInicioPrevistoCM() {
		return inicioPrevistoCM;
	}

	/**
	 * @param inicioPrevistoCM the inicioPrevistoCM to set
	 */
	public void setInicioPrevistoCM(String inicioPrevistoCM) {
		this.inicioPrevistoCM = inicioPrevistoCM;
	}

	/**
	 * @return the inicioCM
	 */
	public String getInicioCM() {
		return inicioCM;
	}

	/**
	 * @param inicioCM the inicioCM to set
	 */
	public void setInicioCM(String inicioCM) {
		this.inicioCM = inicioCM;
	}

	/**
	 * @return the fimPrevistoCM
	 */
	public String getFimPrevistoCM() {
		return fimPrevistoCM;
	}

	/**
	 * @param fimPrevistoCM the fimPrevistoCM to set
	 */
	public void setFimPrevistoCM(String fimPrevistoCM) {
		this.fimPrevistoCM = fimPrevistoCM;
	}

	/**
	 * @return the fimCM
	 */
	public String getFimCM() {
		return fimCM;
	}

	/**
	 * @param fimCM the fimCM to set
	 */
	public void setFimCM(String fimCM) {
		this.fimCM = fimCM;
	}

	/**
	 * @return the inicioLM
	 */
	public String getInicioLM() {
		return inicioLM;
	}

	/**
	 * @param inicioLM the inicioLM to set
	 */
	public void setInicioLM(String inicioLM) {
		this.inicioLM = inicioLM;
	}

	/**
	 * @return the fimPrevistoLM
	 */
	public String getFimPrevistoLM() {
		return fimPrevistoLM;
	}

	/**
	 * @param fimPrevistoLM the fimPrevistoLM to set
	 */
	public void setFimPrevistoLM(String fimPrevistoLM) {
		this.fimPrevistoLM = fimPrevistoLM;
	}

	/**
	 * @return the fimLM
	 */
	public String getFimLM() {
		return fimLM;
	}

	/**
	 * @param fimLM the fimLM to set
	 */
	public void setFimLM(String fimLM) {
		this.fimLM = fimLM;
	}	

	/**
	 * @return the demanda
	 */
	public String getDemanda() {
		return demanda;
	}

	/**
	 * @param demanda the demanda to set
	 */
	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}

	
	/**
	 * @return the facilidade
	 */
	public String getFacilidade() {
		return facilidade;
	}

	
	/**
	 * @param facilidade the facilidade to set
	 */
	public void setFacilidade(String facilidade) {
		this.facilidade = facilidade;
	}

	
	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	
	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	
	/**
	 * @return the inicioPrevistoLM
	 */
	public String getInicioPrevistoLM() {
		return inicioPrevistoLM;
	}

	
	/**
	 * @param inicioPrevistoLM the inicioPrevistoLM to set
	 */
	public void setInicioPrevistoLM(String inicioPrevistoLM) {
		this.inicioPrevistoLM = inicioPrevistoLM;
	}

}
