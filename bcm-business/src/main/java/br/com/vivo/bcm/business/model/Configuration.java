package br.com.vivo.bcm.business.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "CONFIGURATION")
public class Configuration implements IUID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFIGURATION")
	@SequenceGenerator(name = "SEQ_CONFIGURATION", sequenceName = "SEQ_CONFIGURATION", allocationSize = 1)
	private Long uid;

	@Column(name = "KEY", nullable = false)
	private String key;

	@Column(name = "VALUE", nullable = false)
	private String value;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "INSERT_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertDate;

	@Column(name = "UPDATE_DATE", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

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
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the insertDate
	 */
	public Date getInsertDate() {
		return insertDate;
	}

	/**
	 * @param insertDate the insertDate to set
	 */
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}