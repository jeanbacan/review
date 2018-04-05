/**
 * 
 */
package br.com.vivo.bcm.business.vo;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vivo.bcm.business.dto.GridValuesDTO;
import br.com.vivo.bcm.business.dto.form.DataGridDTO;
import br.com.vivo.bcm.business.enums.VivoTaskFormType;

/**
 * @author A0051460
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class VivoTaskFormItem {

	private String id;
	private String name;
	private String value;
	private String metadata;
	//Permite alterar o valor ou não. Campo sempre visível. Propriedade enabled CSS
	private boolean isWritable;
	private boolean isHeader = false;
	private boolean isRequired;
	//Permite visualizar o valor ou não. Propriedade visible / show CSS
	private boolean isReadable;
	private VivoTaskFormType type;
	private DataGridDTO dataGridDTO;
	private GridValuesDTO gridValues;
	private GridValuesDTO gridDefaultValues;
	private LinkedHashMap<String, String> possibleValues;

	public VivoTaskFormItem(){
		
	}
	public VivoTaskFormItem(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the dataGridDTO
	 */
	public DataGridDTO getDataGridDTO() {
		return dataGridDTO;
	}
	/**
	 * @param dataGridDTO the dataGridDTO to set
	 */
	public void setDataGridDTO(DataGridDTO dataGridDTO) {
		this.dataGridDTO = dataGridDTO;
	}
	/**
	 * @return the gridValues
	 */
	public GridValuesDTO getGridValues() {
		return gridValues;
	}
	/**
	 * @param gridValues the gridValues to set
	 */
	public void setGridValues(GridValuesDTO gridValues) {
		this.gridValues = gridValues;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the type
	 */
	public VivoTaskFormType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(VivoTaskFormType type) {
		this.type = type;
	}

	/**
	 * @return the possibleValues
	 */
	public LinkedHashMap<String, String> getPossibleValues() {
		return possibleValues;
	}
	
	/**
	 * @return the gridDefaultValues
	 */
	public GridValuesDTO getGridDefaultValues() {
		return gridDefaultValues;
	}
	/**
	 * @param gridDefaultValues the gridDefaultValues to set
	 */
	public void setGridDefaultValues(GridValuesDTO gridDefaultValues) {
		this.gridDefaultValues = gridDefaultValues;
	}
	/**
	 * @param possibleValues the possibleValues to set
	 */
	public void setPossibleValues(LinkedHashMap<String, String> possibleValues) {
		this.possibleValues = possibleValues;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the isWritable
	 */
	public boolean isWritable() {
		return isWritable;
	}

	/**
	 * @param isWritable
	 *            the isWritable to set
	 */
	public void setWritable(boolean isWritable) {
		this.isWritable = isWritable;
	}

	/**
	 * @return the isRequired
	 */
	public boolean isRequired() {
		return isRequired;
	}

	/**
	 * @param isRequired
	 *            the isRequired to set
	 */
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the isReadable
	 */
	public boolean isReadable() {
		return isReadable;
	}
	
	/**
	 * @param isReadable the isReadable to set
	 */
	public void setReadable(boolean isReadable) {
		this.isReadable = isReadable;
	}
	
	/**
	 * @return the isHeader
	 */
	public boolean isHeader() {
		return isHeader;
	}
	
	/**
	 * @param isHeader the isHeader to set
	 */
	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}

}
