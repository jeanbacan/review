/**
 * 
 */
package br.com.vivo.bcm.business.transformer;

/**
 * Contrato para resolução de colunas dos arquivos de FTP.
 * 
 * @author Jean Marcel
 *
 */
public interface IColumnResolver<X, Y> {

	/**
	 * Define dados de uma linha lida do arquivo.
	 * 
	 * @param X linha de dados.
	 */
	void setData(X x);

	/**
	 * Define cabeçalhos existestes no arquivo atual.
	 * 
	 * @param X Cabecalhos
	 */
	void setHeader(X x);

	/**
	 * Busca valor nos dados setados para respectiva coluna.
	 * 
	 * @param OrderConfirmationColumnsType
	 * @return String Valor da setado em setData(). Retorna String vazia quando coluna não encontrada no arquivo.
	 */
	String getValue(Y columnsType);
}
