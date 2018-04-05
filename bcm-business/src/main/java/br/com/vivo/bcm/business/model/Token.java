package br.com.vivo.bcm.business.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import br.com.vivo.bcm.business.enums.TokenType;

/**
 * @author G0029875
 */
@Entity
@Table(name = "TOKEN")
public class Token implements IUID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TOKEN")
	@SequenceGenerator(name = "SEQ_TOKEN", sequenceName = "SEQ_TOKEN", allocationSize = 1)
	private Long uid;

	@Column(name = "HASH")
	private String hash;

	@Column(name = "TOKEN_TYPE")
	@Enumerated(value = EnumType.STRING)
	private TokenType tokenType;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "EXPIRATION_DATE", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date expirationDate;

	@Override
	public Long getUid() {
		return this.uid;
	}

	@Override
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return the tokenType
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
