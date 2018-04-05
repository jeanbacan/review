package br.com.vivo.bcm.business.dto.form.transformer;

import javax.inject.Named;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.BooleanFormType;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.DoubleFormType;
import org.activiti.engine.impl.form.LongFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import br.com.vivo.bcm.activiti.formType.MetadataFormObject;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

@Named("defaultFormTransformer")
public class DefaultFormTransformer implements IFormTransformer{

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dto.form.IFormTransformer#transform(org.activiti.engine.form.FormProperty)
	 */
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
		
		if (formProperty.getType() instanceof LongFormType) {
			vivoTaskFormItem.setType(VivoTaskFormType.LONG);

		} else if (formProperty.getType() instanceof DoubleFormType) {
			vivoTaskFormItem.setType(VivoTaskFormType.DOUBLE);

		} else if (formProperty.getType() instanceof StringFormType) {
			vivoTaskFormItem.setType(VivoTaskFormType.STRING);

		} else if (formProperty.getType() instanceof DateFormType) {
			vivoTaskFormItem.setType(VivoTaskFormType.DATE);

		} else if (formProperty.getType() instanceof BooleanFormType) {
			vivoTaskFormItem.setType(VivoTaskFormType.BOOLEAN);

		} 
		
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
		
		if (VivoTaskFormType.LONG.equals(VivoTaskFormType.valueOf(formProperty.getType().toUpperCase()))) {
			vivoTaskFormItem.setType(VivoTaskFormType.LONG);

		} else if (VivoTaskFormType.STRING.equals(VivoTaskFormType.valueOf(formProperty.getType().toUpperCase()))) {
			vivoTaskFormItem.setType(VivoTaskFormType.STRING);

		} else if (VivoTaskFormType.DATE.equals(VivoTaskFormType.valueOf(formProperty.getType().toUpperCase()))) {
			vivoTaskFormItem.setType(VivoTaskFormType.DATE);

		} else if (VivoTaskFormType.BOOLEAN.equals(VivoTaskFormType.valueOf(formProperty.getType().toUpperCase()))) {
			vivoTaskFormItem.setType(VivoTaskFormType.BOOLEAN);

		} else if (VivoTaskFormType.DOUBLE.equals(VivoTaskFormType.valueOf(formProperty.getType().toUpperCase()))) {
			vivoTaskFormItem.setType(VivoTaskFormType.DOUBLE);
		} 

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

}
