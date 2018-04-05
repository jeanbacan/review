package br.com.vivo.bcm.business.dto.form.transformer;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Named;

import org.activiti.bpmn.model.FormValue;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.EnumFormType;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import br.com.vivo.bcm.activiti.formType.MetadataFormObject;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

@Named("enumFormTransformer")
public class EnumFormTransformer implements IFormTransformer {

	private final String VALUES_INFO_KEY = "values";
	
	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dto.form.IFormTransformer#transform(org.activiti.engine.form.FormProperty)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public VivoTaskFormItem transform(FormProperty formProperty) throws BusinessException {
		MetadataFormObject metadataObject = null;
		VivoTaskFormItem vivoTaskFormItem = new VivoTaskFormItem();

		if (formProperty.getMetadata() != null){
			metadataObject = new Gson().fromJson(formProperty.getMetadata(), MetadataFormObject.class);
			
			if (metadataObject != null && metadataObject.isHeader()){
				vivoTaskFormItem.setHeader(Boolean.TRUE);
			}
		}
		
		vivoTaskFormItem.setType(VivoTaskFormType.ENUM);

		EnumFormType enumFormType = (EnumFormType) formProperty.getType();
		LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) enumFormType.getInformation(VALUES_INFO_KEY);
		vivoTaskFormItem.setPossibleValues(map);
		
		vivoTaskFormItem.setId(formProperty.getId());
		vivoTaskFormItem.setName(formProperty.getName());
		vivoTaskFormItem.setValue(formProperty.getValue());
		vivoTaskFormItem.setWritable(formProperty.isWritable());
		vivoTaskFormItem.setRequired(formProperty.isRequired());
		vivoTaskFormItem.setReadable(formProperty.isReadable());

		return vivoTaskFormItem;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dto.form.IFormTransformer#transform(org.activiti.bpmn.model.FormProperty, java.lang.String)
	 */
	@Override
	public VivoTaskFormItem transform(org.activiti.bpmn.model.FormProperty formProperty, String value) throws BusinessException {
		
		MetadataFormObject metadataObject = null;
		VivoTaskFormItem vivoTaskFormItem = new VivoTaskFormItem();

		if (formProperty.getMetadata() != null){
			metadataObject = new Gson().fromJson(formProperty.getMetadata(), MetadataFormObject.class);
			
			if (metadataObject != null && metadataObject.isHeader()){
				vivoTaskFormItem.setHeader(Boolean.TRUE);
			}
		}
		
		vivoTaskFormItem.setType(VivoTaskFormType.ENUM);

		LinkedHashMap<String, String> map = this.getHistoricEnumPossibleValues(formProperty.getFormValues());
		vivoTaskFormItem.setPossibleValues(map);
		
		if (StringUtils.isNotBlank(value)) {
			vivoTaskFormItem.setValue(value);
		} else if (StringUtils.isNotBlank(formProperty.getDefaultExpression())) {
			vivoTaskFormItem.setValue(formProperty.getDefaultExpression());
		}

		vivoTaskFormItem.setId(formProperty.getId());
		vivoTaskFormItem.setName(formProperty.getName());
		vivoTaskFormItem.setWritable(formProperty.isWriteable());
		vivoTaskFormItem.setRequired(formProperty.isRequired());
		vivoTaskFormItem.setReadable(formProperty.isReadable());

		return vivoTaskFormItem;
	}
	

	/**
	 * Converte List<FormValue> do histórico para lista de valores possiveis para enum
	 * 
	 * @param List<FormValue> Objeto retornado da propriedade do historico
	 * @return LinkedHashMap<String, String> possible values
	 */
	private LinkedHashMap<String, String> getHistoricEnumPossibleValues(List<FormValue> formValues) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		for (FormValue form : formValues) {
			map.put(form.getId(), form.getName());
		}
		return map;
	}
}
