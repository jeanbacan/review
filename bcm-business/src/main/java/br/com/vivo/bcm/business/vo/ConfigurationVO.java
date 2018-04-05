package br.com.vivo.bcm.business.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ConfigurationVO {
	private Long uid;
	private String key;
	private String value;
	private String comments;
	private Date insertDate;
	private Date updateDate;

	/**
	 * default constructor
	 */
	public ConfigurationVO() {
	}

	/**
	 * @param uid
	 * @param key
	 * @param value
	 * @param comments
	 * @param insertDate
	 * @param updateDate
	 */
	public ConfigurationVO(Long uid, String key, String value, String comments, Date insertDate, Date updateDate) {
		this.uid = uid;
		this.key = key;
		this.value = value;
		this.comments = comments;
		this.insertDate = insertDate;
		this.updateDate = updateDate;
	}

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