/**
 * 
 */
package br.com.vivo.bcm.activiti.formType;

import org.activiti.engine.form.AbstractFormType;

/**
 * @author A0051460
 *
 */
public class GridFormType extends AbstractFormType {

	private static final long serialVersionUID = 1L;

	private String metadata;

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getName() {
		return "grid";
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