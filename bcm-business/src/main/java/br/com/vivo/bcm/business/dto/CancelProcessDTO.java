package br.com.vivo.bcm.business.dto;

/**
 * Transfer a CancelProcess Request businessLayer  
 * @author Jean Bacan
 *
 */
public class CancelProcessDTO {
	
	private String processInstanceId;
	private String reason;

	protected CancelProcessDTO(){
		
	}
	
	public CancelProcessDTO(String processInstanceId, String reason){
		this.reason = reason;
		this.processInstanceId = processInstanceId;
	}

	
	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

}
