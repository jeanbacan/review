package br.com.vivo.bcm.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Jean Bacan
 */
@Entity
@Table(name = "UF")
public class UF implements IUID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long uid;

	@Column(name = "NAME")
	private String name;

	@ManyToOne
	@JoinColumn(name = "DIRETORIA_ID", nullable = false)
	private Diretoria diretoria;

	@Override
	public Long getUid() {
		return this.uid;
	}

	@Override
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the diretoria
	 */
	public Diretoria getDiretoria() {
		return diretoria;
	}

	/**
	 * @param diretoria the diretoria to set
	 */
	public void setDiretoria(Diretoria diretoria) {
		this.diretoria = diretoria;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
