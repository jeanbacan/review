package br.com.vivo.bcm.business.enums;

public enum ConstraintType {

	LIKE(" like ", false),
	LIKE_IGNORECASE(" like ", true),
	EQUAL(" = ", false),
	EQUAL_IGNORECASE(" = ", true),
	GREATER(" > ", false),
	LOWER(" < ", false),
	GREATER_EQUAL(" >= ", false),
	LOWER_EQUAL(" <= ", false);

	private final String operator;
	private final boolean ignoreCase;

	private ConstraintType(String operator, boolean lowercase) {
		this.operator = operator;
		this.ignoreCase = lowercase;
	}

	public String getOperator() {
		return operator;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}
}