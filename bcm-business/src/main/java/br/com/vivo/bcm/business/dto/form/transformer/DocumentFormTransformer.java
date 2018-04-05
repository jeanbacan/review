package br.com.vivo.bcm.business.dto.form.transformer;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.form.FormProperty;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import br.com.vivo.bcm.activiti.formType.MetadataFormObject;
import br.com.vivo.bcm.business.dto.form.DocumentFormField;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.MyDocumentVO;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

@Named("documentFormTransformer")
public class DocumentFormTransformer implements IFormTransformer{

	@Inject	
	@Named("getMyDocumentByIdBusinessOperation")
	private IBusinessOperation<String, MyDocumentVO> getMyDocumentByIdBusinessOperation;
	
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
		
		DocumentFormField docFormField = new DocumentFormField();

		docFormField.setType(VivoTaskFormType.DOCUMENT);
		if (StringUtils.isNotBlank(formProperty.getValue())) {
			docFormField.setMyDocumentVO(this.getMyDocumentByIdBusinessOperation.execute(formProperty.getValue()));
		}
		vivoTaskFormItem = docFormField;
		
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
		
		DocumentFormField docFormField = new DocumentFormField();
		docFormField.setType(VivoTaskFormType.DOCUMENT);

		if (StringUtils.isNotBlank(value)) {
			docFormField.setMyDocumentVO(this.getMyDocumentByIdBusinessOperation.execute(value));
		}
		vivoTaskFormItem = docFormField;
		
		vivoTaskFormItem.setId(formProperty.getId());
		vivoTaskFormItem.setName(formProperty.getName());
		vivoTaskFormItem.setWritable(formProperty.isWriteable());
		vivoTaskFormItem.setRequired(formProperty.isRequired());
		vivoTaskFormItem.setReadable(formProperty.isReadable());

		return vivoTaskFormItem;
	}

}
