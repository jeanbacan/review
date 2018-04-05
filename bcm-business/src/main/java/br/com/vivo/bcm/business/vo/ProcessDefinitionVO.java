package br.com.vivo.bcm.business.vo;

import java.util.Date;

public class ProcessDefinitionVO {
	private String id;
	private String name;
	private int version;
	private long qtdProjectsDescendent;
	private Date lastUpdate;
	private String key;
	private boolean lastestVersion;

	public ProcessDefinitionVO() {
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
	 * @return the qtdProjectsDescendent
	 */
	public long getQtdProjectsDescendent() {
		return qtdProjectsDescendent;
	}

	/**
	 * @param qtdProjectsDescendent the qtdProjectsDescendent to set
	 */
	public void setQtdProjectsDescendent(long qtdProjectsDescendent) {
		this.qtdProjectsDescendent = qtdProjectsDescendent;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the lastestVersion
	 */
	public boolean isLastestVersion() {
		return lastestVersion;
	}

	/**
	 * @param lastestVersion the lastestVersion to set
	 */
	public void setLastestVersion(boolean lastestVersion) {
		this.lastestVersion = lastestVersion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessDefinitionVO other = (ProcessDefinitionVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	

}
