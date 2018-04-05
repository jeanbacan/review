package br.com.vivo.bcm.business.dto.form.transformer;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.form.FormType;
import org.activiti.engine.impl.form.DataGridFormType;
import org.activiti.engine.impl.form.EnumFormType;

import br.com.vivo.bcm.activiti.formType.CheckListFormType;
import br.com.vivo.bcm.activiti.formType.ComboBoxFormType;
import br.com.vivo.bcm.activiti.formType.DocumentFormType;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;

@Named("activitiFormTransformer")
public class FormTransformerFactory {

	@Inject
	@Named("comboBoxFormTransformer")
	private IFormTransformer comboBoxFormTransformer;
	
	@Inject
	@Named("checkListFormTransformer")
	private IFormTransformer checkListFormTransformer;
	
	@Inject
	@Named("defaultFormTransformer")
	private IFormTransformer defaultFormTransformer;
	
	@Inject
	@Named("enumFormTransformer")
	private IFormTransformer enumFormTransformer;
	
	@Inject
	@Named("documentFormTransformer")
	private IFormTransformer documentFormTransformer;
	
	@Inject
	@Named("gridFormTransformer")
	private IFormTransformer gridFormTransformer;
	
	
	
	/**
	 * Cria transformer para propriedade em Runtime Task
	 * @param formProperty
	 * @return IFormTransformer
	 * @throws BusinessException
	 */
	public IFormTransformer createTransformer(FormType formType) throws BusinessException {

		if (formType instanceof EnumFormType) {
			return enumFormTransformer;

		} else if (formType instanceof CheckListFormType) {
			return checkListFormTransformer;

		} else if (formType instanceof ComboBoxFormType) {
			return comboBoxFormTransformer;

		} else if (formType instanceof DocumentFormType) {
			return documentFormTransformer;
			
		} else if (formType instanceof DataGridFormType) {
			return gridFormTransformer;
			
		}  else {
			return defaultFormTransformer;
		}
	}

	/**
	 * Cria transformer para propriedade em Historic Task
	 * @param formProperty
	 * @return IFormTransformer
	 * @throws BusinessException
	 */
	public IFormTransformer createTransformer(String formType) throws BusinessException {

		if (VivoTaskFormType.ENUM.equals(VivoTaskFormType.valueOf(formType.toUpperCase()))) {
			return enumFormTransformer;

		} else if (VivoTaskFormType.DOCUMENT.equals(VivoTaskFormType.valueOf(formType.toUpperCase()))) {
			return documentFormTransformer;

		} else if (VivoTaskFormType.CHECKLIST.equals(VivoTaskFormType.valueOf(formType.toUpperCase()))) {
			return checkListFormTransformer;
			
		} else if (VivoTaskFormType.COMBOBOX.equals(VivoTaskFormType.valueOf(formType.toUpperCase()))) {
			return comboBoxFormTransformer;
		
		} else if (VivoTaskFormType.DATAGRID.equals(VivoTaskFormType.valueOf(formType.toUpperCase()))) {
			return gridFormTransformer;
			
		} else {
			return defaultFormTransformer;
		}
	}

}
