/**
 * 
 */
package br.com.vivo.bcm.business.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Resgata descrições utilizadas no activiti Utilizando para perfomarce. Senão seria necessário consultar todo form de atividade no XML
 * 
 * @author Jean Bacan
 *
 */
public class ComboDescriptions {

	public static String getTipoTecnologiaDescription(String comboId) {

		if (comboId == null || comboId.length() < 1) {
			return "";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("idCobre", "Cobre");
		map.put("idFibra", "Fibra");
		return map.get(comboId);
	}

	public static String getSimNaoDescription(String comboId) {

		if (comboId == null || comboId.length() < 1) {
			return "";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("idSim", "Sim");
		map.put("idNao", "Não");
		return map.get(comboId);
	}

	public static String getResultadoDescription(String comboId) {

		if (comboId == null || comboId.length() < 1) {
			return "";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("idRecomendado", "Recomendado");
		map.put("idNaoRecomendado", "Não Recomendado");
		map.put("idRevisar", "Revisar");
		map.put("idNaoRecomendadoObrigatorio", "Não Recomendado - Obra obrigatória");
		return map.get(comboId);
	}
	
	public static String getAprovacaoCDGDescription(String comboId) {

		if (comboId == null || comboId.length() < 1) {
			return "";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("idAprovado", "Aprovado");
		map.put("idReprovado", "Reprovado");
		map.put("idRevisar", "Revisar");		
		return map.get(comboId);
	}

	public static String getTipoObraDescription(String comboId) {

		if (comboId == null || comboId.length() < 1) {
			return "";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("idAlivioLogradouro", "Alívio de Logradouro");
		map.put("idAlivioFTTH", "Alívio FTTH");
		map.put("idAlivio", "Alívios");
		map.put("idPequenoAlivio", "Pequenos Alívios");
		map.put("idAteiMSAN", "ATEI com MSAN");
		map.put("idAteiMetalico", "ATEI Metálico");
		map.put("idCondominio", "Condomínio");
		map.put("idIndustrial", "Distrito Industrial");
		map.put("idExpansao", "Expansão");
		map.put("idMicro", "Micro Poligono");
		map.put("idNovaArea", "Nova Área");
		map.put("idShopping", "Shopping");
		map.put("idSobreposicao", "Sobreposição");
		map.put("idExpansaoSobreposicao", "Sobreposição + Expansão");
		return map.get(comboId);
	}

	public static String getTipoInvestimentoDescription(String comboId) {

		if (comboId == null || comboId.length() < 1) {
			return "";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("idCentral", "Central");
		map.put("idOutros", "Pequenas Obras");		
		map.put("idAlivioLogradouro", "Alívio de Logradouro");
		map.put("idAlivioFTTH", "Alívio FTTH");
		map.put("idAlivio", "Alívios");
		map.put("idPequenoAlivio", "Pequenos Alívios");
		map.put("idAteiMSAN", "ATEI com MSAN");
		map.put("idAteiMetalico", "ATEI Metálico");
		map.put("idCondominio", "Condomínio");
		map.put("idIndustrial", "Distrito Industrial");
		map.put("idExpansao", "Expansão");
		map.put("idMicro", "Micro Poligono");
		map.put("idNovaArea", "Nova Área");
		map.put("idShopping", "Shopping");
		map.put("idSobreposicao", "Sobreposição");
		return map.get(comboId);
	}

}
