package br.com.vivo.bcm.business.dto.filter;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer offset = 0;
	private Integer size = 10;

	public Page() {
	}

	public Page(Integer offset, Integer size) {
		this.offset = offset;
		this.size = size;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Page [offset=" + offset + ", size=" + size + "]";
	}
}