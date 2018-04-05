package br.com.vivo.bcm.business.dto;

/**
 * Item com informação de cada candidate group para {@link CountTasksDTO}.
 * 
 * @author Jean Bacan
 *
 */
public class CountTasksItemDTO {

	private long id;

	private String name;

	private String description;

	private int taskAmount;

	private int facilityAmount;
	
	private String processKey;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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

	
	/**
	 * @return the taskAmount
	 */
	public int getTaskAmount() {
		return taskAmount;
	}

	
	/**
	 * @param taskAmount the taskAmount to set
	 */
	public void setTaskAmount(int taskAmount) {
		this.taskAmount = taskAmount;
	}

	
	/**
	 * @return the facilityAmount
	 */
	public int getFacilityAmount() {
		return facilityAmount;
	}

	
	/**
	 * @param facilityAmount the facilityAmount to set
	 */
	public void setFacilityAmount(int facilityAmount) {
		this.facilityAmount = facilityAmount;
	}



	
	/**
	 * @return the processKey
	 */
	public String getProcessKey() {
		return processKey;
	}


	
	/**
	 * @param processKey the processKey to set
	 */
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((processKey == null) ? 0 : processKey.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountTasksItemDTO other = (CountTasksItemDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (processKey == null) {
			if (other.processKey != null)
				return false;
		} else if (!processKey.equals(other.processKey))
			return false;
		return true;
	}

}
