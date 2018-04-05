package br.com.vivo.bcm.business.dto;

import java.util.Map;

public class SenderObject {

	private String templateCode;

	private Map<String, Object> params;

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getValue(String key) {
		if (params != null) {
			for (Map.Entry<String, Object> mapEntry : params.entrySet()) {
				if (mapEntry.getKey().equals(key)) {
					return mapEntry.getValue().toString();
				}
			}
		}
		return null;
	}

}
