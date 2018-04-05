package br.com.vivo.bcm.business.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/*
 * @author Jean Bacan
 */

@JsonInclude(value = Include.NON_NULL)
public class VivoTaskUser {

	private String name;

	private String id;

	public VivoTaskUser() {
		// TODO Auto-generated constructor stub
	}

	public VivoTaskUser(String id, String name) {
		this.id = id;
		this.name = name;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
