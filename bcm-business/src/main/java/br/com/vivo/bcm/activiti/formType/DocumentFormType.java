package br.com.vivo.bcm.activiti.formType;

import org.activiti.engine.form.AbstractFormType;

/**
 * @author P9923900
 *
 */
public class DocumentFormType extends AbstractFormType {

	private static final long serialVersionUID = 1L;

	public String getName() {
		return "document";
	}

	public String getMimeType() {
		return "text/plain";
	}

	public Object convertFormValueToModelValue(String propertyValue) {
		return propertyValue;
	}

	public String convertModelValueToFormValue(Object modelValue) {
		return (String) modelValue;
	}
}