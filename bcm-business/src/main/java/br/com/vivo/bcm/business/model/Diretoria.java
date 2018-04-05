package br.com.vivo.bcm.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Jean Bacan
 */
@Entity
@Table(name = "DIRETORIA")
public class Diretoria implements IUID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long uid;

	@Column(name = "NAME")
	private String name;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
