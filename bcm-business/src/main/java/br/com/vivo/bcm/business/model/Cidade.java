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
@Table(name = "CIDADE")
public class Cidade implements IUID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long uid;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CNL")
	private String cnl;

	@ManyToOne
	@JoinColumn(name = "UF_ID", nullable = false)
	private UF uf;

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
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
	 * @return the uf
	 */
	public UF getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(UF uf) {
		this.uf = uf;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
