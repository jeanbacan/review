package br.com.vivo.bcm.business.dto.form.transformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.bpmn.model.DataGridCell;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.DataGridFormType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.vivo.bcm.business.dao.IActivitiFormVariablesDAO;
import br.com.vivo.bcm.business.dto.GridValuesDTO;
import br.com.vivo.bcm.business.dto.LineValueDTO;
import br.com.vivo.bcm.business.dto.ValueDTO;
import br.com.vivo.bcm.business.dto.form.ComboBoxFormField;
import br.com.vivo.bcm.business.dto.form.DataGridDTO;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.ActivitiFormVariables;
import br.com.vivo.bcm.business.operation.bean.BaseBusinessOperation;
import br.com.vivo.bcm.business.util.ApplicationConstants;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

@Named("gridFormTransformer")
public class GridFormTransformer extends BaseBusinessOperation implements IFormTransformer {

	@Inject
	@Named("activitiFormVariablesDAO")
	private IActivitiFormVariablesDAO activitiFormVariablesDAO;

	private static final Logger logger = Logger.getLogger(GridFormTransformer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.dto.form.IFormTransformer#transform(org.activiti .engine.form.FormProperty)
	 */
	@Override
	public VivoTaskFormItem transform(FormProperty formProperty) throws BusinessException {

		VivoTaskFormItem vivoTaskFormItem = new VivoTaskFormItem();

		try {

			// Captura a estrutura do Grid do formProperties Grid e converte em
			// DTO (Utilizado no cabeçalho)
			GridValuesDTO gridValuesDTO = new ObjectMapper().readValue(formProperty.getMetadata(), GridValuesDTO.class);

			vivoTaskFormItem.setType(VivoTaskFormType.DATAGRID);
			vivoTaskFormItem.setDataGridDTO(this.copyProperties(((DataGridFormType)formProperty.getType()).getCells(), gridValuesDTO));

			// Captura os valores default de cada linha nova,
			if (formProperty.getMetadata() != null) {
				vivoTaskFormItem.setGridDefaultValues(gridValuesDTO);
			}

			// Busca na base apenas os valores da Grid., senão adiciona uma
			// linha vazia
			if (StringUtils.isNumeric(formProperty.getValue())) {
				ActivitiFormVariables activitiFormVariables = this.activitiFormVariablesDAO.findByPrimaryKey(new Long(formProperty.getValue()));
				gridValuesDTO = new ObjectMapper().readValue(activitiFormVariables.getValue(), GridValuesDTO.class);
			}

			vivoTaskFormItem.setGridValues(gridValuesDTO);

		} catch (Exception e) {
			logger.error("Erro ao converter valor JSON da propriedade: " + formProperty.getName(), e);
		}

		vivoTaskFormItem.setId(formProperty.getId());
		vivoTaskFormItem.setName(formProperty.getName());
		vivoTaskFormItem.setValue(formProperty.getValue());
		vivoTaskFormItem.setWritable(formProperty.isWritable());
		vivoTaskFormItem.setRequired(formProperty.isRequired());
		vivoTaskFormItem.setReadable(formProperty.isReadable());

		return vivoTaskFormItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.dto.form.IFormTransformer#transform(org.activiti .bpmn.model.FormProperty, java.lang.String)
	 */
	@Override
	public VivoTaskFormItem transform(org.activiti.bpmn.model.FormProperty formProperty, String value) throws BusinessException {

		VivoTaskFormItem vivoTaskFormItem = new VivoTaskFormItem();

		try {

			// Captura a estrutura do Grid do formProperties Grid e converte em
			// DTO (Utilizado no cabeçalho)
			GridValuesDTO gridValuesDTO = new ObjectMapper().readValue(formProperty.getMetadata(), GridValuesDTO.class);

			vivoTaskFormItem.setType(VivoTaskFormType.DATAGRID);
			vivoTaskFormItem.setDataGridDTO(this.copyHistoricProperties(formProperty.getDataGridCells(), gridValuesDTO));

			// Captura os valores default de cada linha nova,
			if (formProperty.getMetadata() != null) {
				vivoTaskFormItem.setGridDefaultValues(gridValuesDTO);
			}

			// Busca na base apenas os valores da Grid., senão adiciona uma
			// linha vazia
			if (StringUtils.isNumeric(value)) {
				ActivitiFormVariables activitiFormVariables = this.activitiFormVariablesDAO.findByPrimaryKey(new Long(value));
				gridValuesDTO = new ObjectMapper().readValue(activitiFormVariables.getValue(), GridValuesDTO.class);
			}

			vivoTaskFormItem.setGridValues(gridValuesDTO);

		} catch (Exception e) {
			logger.error("Erro ao converter valor JSON da propriedade: " + formProperty.getName(), e);
		}

		vivoTaskFormItem.setId(formProperty.getId());
		vivoTaskFormItem.setName(formProperty.getName());
		vivoTaskFormItem.setValue(value);
		vivoTaskFormItem.setWritable(formProperty.isWriteable());
		vivoTaskFormItem.setRequired(formProperty.isRequired());
		vivoTaskFormItem.setReadable(formProperty.isReadable());
		return vivoTaskFormItem;
	}

	/**
	 * @param formDataGridCells Lista de DataGridCell do formProperty no XML
	 * @param gridValuesDTO Modelo da Grid salva no metada do formProperty
	 * @return DataGridDTO
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private DataGridDTO copyHistoricProperties(List<DataGridCell> formDataGridCells, GridValuesDTO gridValuesDTO) throws JsonParseException, JsonMappingException, IOException {
		DataGridDTO dataGridDTO = new DataGridDTO();
		List<VivoTaskFormItem> vivoTaskFormItems = new ArrayList<VivoTaskFormItem>();

		for (DataGridCell dataGridCell : formDataGridCells) {
			VivoTaskFormItem formItem = new VivoTaskFormItem();
			formItem.setType(VivoTaskFormType.valueOf(dataGridCell.getFormType().toUpperCase()));

			if (VivoTaskFormType.COMBOBOX.equals(formItem.getType())) {

				for (LineValueDTO lineValueDTO : gridValuesDTO.getLines()) {
					for (ValueDTO valueDTO : lineValueDTO.getValues()) {
						if (dataGridCell.getObjAttribute().equals(valueDTO.getKey())) {
							if (valueDTO.getMetadata().isLazy()) {

								ComboBoxFormField comboBoxFormField = new ComboBoxFormField();
								comboBoxFormField.setLazy(Boolean.TRUE);
								comboBoxFormField.setDataSourceName(valueDTO.getMetadata().getBeanName());
								comboBoxFormField.setListening(valueDTO.getMetadata().getListening());
								comboBoxFormField.setType(VivoTaskFormType.COMBOBOX);

								formItem = comboBoxFormField;
							}
						}
					}
				}
			} else if (VivoTaskFormType.ENUM.equals(formItem.getType())){
			
				String[] values = dataGridCell.getValuesAvailables().split(ApplicationConstants.COMMA_SEPARATOR);
				LinkedHashMap<String, String> map = new LinkedHashMap<>();
				
				for (String val :values){
					map.put(val, val);
				}
				
				formItem.setPossibleValues(map);
			}

			formItem.setName(dataGridCell.getHeaderName());
			formItem.setWritable(false);
			formItem.setRequired(dataGridCell.isRequired());
			formItem.setReadable(dataGridCell.isReadable());

			vivoTaskFormItems.add(formItem);
		}
		dataGridDTO.setValues(vivoTaskFormItems);
		return dataGridDTO;
	}
	
	private DataGridDTO copyProperties(List<org.activiti.engine.impl.form.DataGridCell> formDataGridCells, GridValuesDTO gridValuesDTO) throws JsonParseException, JsonMappingException, IOException {
		DataGridDTO dataGridDTO = new DataGridDTO();
		List<VivoTaskFormItem> vivoTaskFormItems = new ArrayList<VivoTaskFormItem>();

		for (org.activiti.engine.impl.form.DataGridCell dataGridCell : formDataGridCells) {
			VivoTaskFormItem formItem = new VivoTaskFormItem();
			formItem.setType(VivoTaskFormType.valueOf(dataGridCell.getFormType().toUpperCase()));

			if (VivoTaskFormType.COMBOBOX.equals(formItem.getType())) {

				for (LineValueDTO lineValueDTO : gridValuesDTO.getLines()) {
					for (ValueDTO valueDTO : lineValueDTO.getValues()) {
						if (dataGridCell.getObjAttribute().equals(valueDTO.getKey())) {
							
							if (valueDTO.getMetadata().isLazy()) {

								ComboBoxFormField comboBoxFormField = new ComboBoxFormField();
								comboBoxFormField.setLazy(Boolean.TRUE);
								comboBoxFormField.setDataSourceName(valueDTO.getMetadata().getBeanName());
								comboBoxFormField.setListening(valueDTO.getMetadata().getListening());
								comboBoxFormField.setType(VivoTaskFormType.COMBOBOX);

								formItem = comboBoxFormField;
							}
						}
					}
				}
			} else if (VivoTaskFormType.ENUM.equals(formItem.getType())){
			
				String[] values = dataGridCell.getValuesAvailables().split(ApplicationConstants.COMMA_SEPARATOR);
				LinkedHashMap<String, String> map = new LinkedHashMap<>();
				
				for (String val :values){
					map.put(val, val);
				}
				
				formItem.setPossibleValues(map);
			}

			formItem.setName(dataGridCell.getHeaderName());
			formItem.setWritable(dataGridCell.isWritable());
			formItem.setRequired(dataGridCell.isRequired());
			formItem.setReadable(dataGridCell.isReadable());

			vivoTaskFormItems.add(formItem);
		}
		dataGridDTO.setValues(vivoTaskFormItems);
		return dataGridDTO;
	}
}
