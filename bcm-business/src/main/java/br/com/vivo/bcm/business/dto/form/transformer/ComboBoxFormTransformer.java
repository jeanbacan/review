package br.com.vivo.bcm.business.dto.form.transformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.activiti.engine.form.FormProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.gson.Gson;

import br.com.vivo.bcm.activiti.formType.MetadataFormObject;
import br.com.vivo.bcm.business.dto.filter.ComboBoxDTO;
import br.com.vivo.bcm.business.dto.filter.ComboBoxValueDTO;
import br.com.vivo.bcm.business.dto.form.ComboBoxFormField;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IDataSourceBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

@Named("comboBoxFormTransformer")
public class ComboBoxFormTransformer implements IFormTransformer{

	@Autowired
	private ApplicationContext applicationContext;
	
	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dto.form.IFormTransformer#transform(org.activiti.engine.form.FormProperty)
	 */
	@Override
	public VivoTaskFormItem transform(FormProperty formProperty) throws BusinessException {
		ComboBoxFormField comboFormField = new ComboBoxFormField();
		comboFormField.setType(VivoTaskFormType.COMBOBOX);

		MetadataFormObject metadataObject = new Gson().fromJson(formProperty.getMetadata(), MetadataFormObject.class);

		if (metadataObject != null && metadataObject.isHeader()){
			comboFormField.setHeader(Boolean.TRUE);
		}
		if (metadataObject.isLazy()) {
			comboFormField.setLazy(Boolean.TRUE);
			comboFormField.setDataSourceName(metadataObject.getBeanName());
			comboFormField.setListening(metadataObject.getListening());

		} else {
			IDataSourceBusinessOperation dsBO = (IDataSourceBusinessOperation) applicationContext.getBean(metadataObject.getBeanName());

			 List<ComboBoxValueDTO> values = new ArrayList<ComboBoxValueDTO>();
			
			if (formProperty.getValue() != null && StringUtils.isNotEmpty(formProperty.getValue())) {
				values = dsBO.findByKey(new Long(formProperty.getValue()));
			} else {
				values = dsBO.execute(new ComboBoxDTO());
			}

			comboFormField.setComboBoxPossibleValues(values);
		}
		comboFormField.setId(formProperty.getId());
		comboFormField.setName(formProperty.getName());
		comboFormField.setWritable(formProperty.isWritable());
		comboFormField.setRequired(formProperty.isRequired());
		comboFormField.setReadable(formProperty.isReadable());
		comboFormField.setValue(formProperty.getValue());

		return comboFormField;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.dto.form.IFormTransformer#transform(org.activiti.bpmn.model.FormProperty, java.lang.String)
	 */
	@Override
	public VivoTaskFormItem transform(org.activiti.bpmn.model.FormProperty formProperty, String value) throws BusinessException {
		ComboBoxFormField comboFormField = new ComboBoxFormField();
		comboFormField.setType(VivoTaskFormType.COMBOBOX);

		MetadataFormObject metadataObject = new Gson().fromJson(formProperty.getMetadata(), MetadataFormObject.class);

		if (metadataObject != null && metadataObject.isHeader()){
			comboFormField.setHeader(Boolean.TRUE);
		}
		if (metadataObject.isLazy()) {
			comboFormField.setLazy(Boolean.TRUE);
			comboFormField.setDataSourceName(metadataObject.getBeanName());
			comboFormField.setListening(metadataObject.getListening());

		} else {
			IDataSourceBusinessOperation dsBO = (IDataSourceBusinessOperation) applicationContext.getBean(metadataObject.getBeanName());

			 List<ComboBoxValueDTO> values = new ArrayList<ComboBoxValueDTO>();
			
			if (value != null && StringUtils.isNotEmpty(value)) {
				values = dsBO.findByKey(new Long(value));
			} else {
				values = dsBO.execute(new ComboBoxDTO());
			}

			comboFormField.setComboBoxPossibleValues(values);
		}
		comboFormField.setId(formProperty.getId());
		comboFormField.setName(formProperty.getName());
		comboFormField.setWritable(formProperty.isWriteable());
		comboFormField.setRequired(formProperty.isRequired());
		comboFormField.setReadable(formProperty.isReadable());
		comboFormField.setValue(value);

		return comboFormField;
	}
}
