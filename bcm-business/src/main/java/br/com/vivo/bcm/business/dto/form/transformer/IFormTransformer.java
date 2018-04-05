package br.com.vivo.bcm.business.dto.form.transformer;

import org.activiti.engine.form.FormProperty;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

public interface IFormTransformer {

	public VivoTaskFormItem transform(FormProperty formProperty) throws BusinessException;

	public VivoTaskFormItem transform(org.activiti.bpmn.model.FormProperty formProperty, String value) throws BusinessException;
}
