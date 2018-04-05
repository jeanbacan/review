package br.com.vivo.bcm.business.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ResultPageDTO {
	private List<?> listResult;
	private long resultCount;

	public List<?> getListResult() {
		return listResult;
	}

	public void setListResult(List<?> listResult) {
		this.listResult = listResult;
	}

	public long getResultCount() {
		return resultCount;
	}

	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}

}
