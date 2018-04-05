package br.com.vivo.bcm.business.transformer;

/**
 * Contrato para conversor de dados vindos do cliente para estrutura DTO.
 * 
 * @author Jean Marcel
 *
 */
public interface IDataTransformer<T, X, Y> {

	/**
	 * Transforma dados do objeto informado
	 * 
	 * @param Object dataRead
	 * @return T 
	 */
	T transform(X dataRead);
	
	/**
	 * Configura cabecalho para saber posicao das colunas.
	 * @param columnsPosition
	 */
	void setup(Y columnsPosition);

}
