package br.com.vivo.bcm.business.dto.filter;

import java.io.Serializable;

import br.com.vivo.bcm.business.enums.ConstraintType;

public class Constraint implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value;
	private ConstraintType type;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ConstraintType getType() {
		return type;
	}

	public void setType(ConstraintType type) {
		this.type = type;
	}
}