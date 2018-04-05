package br.com.vivo.bcm.activiti.formType;

public class ComboBoxFormType extends BCMAbstractFormType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5738857845034205176L;

	@Override
	public String getName() {
		return "combobox";
	}

	@Override
	public boolean isLazyDataLoaderRequired() {
		return true;
	}

	@Override
	public Object convertFormValueToModelValue(String propertyValue) {
		return propertyValue;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		return (String) modelValue;
	}

	@Override
	public IFormTypeDataLoader getFormTypeDataLoader() {
		// TODO Auto-generated method stub
		return null;
	}
}
