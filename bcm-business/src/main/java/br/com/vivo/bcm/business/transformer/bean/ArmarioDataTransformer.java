package br.com.vivo.bcm.business.transformer.bean;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.vivo.bcm.business.dto.extractor.ArmarioDTO;
import br.com.vivo.bcm.business.enums.ArmarioColumnsType;
import br.com.vivo.bcm.business.transformer.IColumnResolver;
import br.com.vivo.bcm.business.transformer.IDataTransformer;

/**
 * Implementação para transformação de dados de confirmação de ordem.
 * 
 * @author Jean Marcel
 *
 */
@Named("armarioDataTransformer")
public class ArmarioDataTransformer implements IDataTransformer<ArmarioDTO, String[], String[]> {

	@Inject
	@Named("armarioColumnResolver")
	private IColumnResolver<String[], ArmarioColumnsType> columnResolver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.bcm.business.transformer.IDataTransformer#transform(java.lang.Object)
	 */
	@Override
	public ArmarioDTO transform(String[] dataRead) {
		this.columnResolver.setData(dataRead);

		String columnUF = columnResolver.getValue(ArmarioColumnsType.UF);
		String columnCidade = columnResolver.getValue(ArmarioColumnsType.CIDADE);
		String columnArmario = columnResolver.getValue(ArmarioColumnsType.ARMARIO);
		String columnDiretoria = columnResolver.getValue(ArmarioColumnsType.DIRETORIA);
		String columnTecnologia = columnResolver.getValue(ArmarioColumnsType.TECNOLOGIA);
		String columnCNL = columnResolver.getValue(ArmarioColumnsType.CNL);

		ArmarioDTO dto = new ArmarioDTO();
		dto.setUf(columnUF);
		dto.setCidade(columnCidade);
		dto.setArmario(columnArmario);
		dto.setDiretoria(columnDiretoria);
		dto.setTecnologia(columnTecnologia);
		dto.setCnl(columnCNL);

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.vivo.communicator.business.transformer.IDataTransformer#setup(java.lang.Object)
	 */
	@Override
	public void setup(String[] headers) {
		this.columnResolver.setHeader(headers);
	}
}
