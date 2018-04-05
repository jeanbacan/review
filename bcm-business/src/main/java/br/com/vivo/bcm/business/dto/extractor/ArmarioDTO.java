package br.com.vivo.bcm.business.dto.extractor;

/**
 * DTO para converter Planilha de armarios
 * 
 * @author Jean Marcel
 *
 */
public class ArmarioDTO {

	private String uf;

	private String cidade;

	private String armario;

	private String diretoria;

	private String tecnologia;
	
	private String cnl;

	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
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
	 * @return the diretoria
	 */
	public String getDiretoria() {
		return diretoria;
	}

	/**
	 * @param diretoria the diretoria to set
	 */
	public void setDiretoria(String diretoria) {
		this.diretoria = diretoria;
	}

	/**
	 * @return the tecnologia
	 */
	public String getTecnologia() {
		return tecnologia;
	}

	/**
	 * @param tecnologia the tecnologia to set
	 */
	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

	
	/**
	 * @return the cnl
	 */
	public String getCnl() {
		return cnl;
	}

	
	/**
	 * @param cnl the cnl to set
	 */
	public void setCnl(String cnl) {
		this.cnl = cnl;
	}

}
