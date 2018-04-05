package br.com.vivo.bcm.business.search;

import java.util.Map;

public class PageInfo {

	@SuppressWarnings("rawtypes")
	private Map map;
	private Integer offset;
	private Integer size;

	public PageInfo() {
	}

	public PageInfo(Integer offset, Integer size) {
		this.offset = offset;
		this.size = size;
	}

	@SuppressWarnings("rawtypes")
	public PageInfo(Map map, Integer offset, Integer size) {
		this.map = map;
		this.offset = offset;
		this.size = size;
	}

	/**
	 * @return the map
	 */
	@SuppressWarnings("rawtypes")
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	@SuppressWarnings("rawtypes")
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}
}