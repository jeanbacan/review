package br.com.vivo.bcm.business.operation.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.configuration.bean.ProcessEngineConfiguration;
import br.com.vivo.bcm.business.dao.IBusinessKeyDAO;
import br.com.vivo.bcm.business.dao.ICidadeDAO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.MissingInformationException;
import br.com.vivo.bcm.business.model.Cidade;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

@Named("generateBusinessKeyBusinessOperation")
public class GenerateBusinessKeyBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<List<VivoTaskFormItem>, String> {

	private static final String FIELD_CIDADE = "cidade";
	private static final String FIELD_INVESTIMENTO = "tipoInvestimento";
	
	private static final String VALUE_INVESTIMENTO_OBRAS = "idOutros";

	private static final Logger logger = Logger.getLogger(GenerateBusinessKeyBusinessOperation.class);

	@Inject
	@Named("cidadeDAO")
	private ICidadeDAO cidadeDAO;
	
	@Inject
	@Named("businessKeyDAO")
	private IBusinessKeyDAO businessKeyDAO;
	
	@Inject
	@Named("processEngineConfiguration")
	private ProcessEngineConfiguration processEngineConfiguration;

	/**
	 * Gera BusinessKey (Nome da instancia, processo ou BC) para um investimento.
	 * Regra definida pelo cliente: UF + CNL + FluxodoInvestimento + 300000_ + 0
	 * FluxoInvestimento deve ser 1 para fluxo normal, e 2 para pequenas obras.
	 * 30000 é um numero incremental que se inicia neste valor devido a projetos antigos de investimento
	 * 0: Posição inicial para todo investimento, a partir do 2 fluxo é incrementado um para cada BC aberto.
	 * 
	 * Ex fluxo normal: PRCTB1_300001_0, a partir do segundo com 3 bc's teriamos: PRCTB1_300001_1, PRCTB1_300001_2, PRCTB1_300001_3
	 * Ex peq obras: PRCTB2_300002_1, visto que esse abre um fluxo direto com apenas 1 BC. 
	 */
	@Override
	public String execute(List<VivoTaskFormItem> formItens) throws BusinessException {

		logger.info("execute do GenerateBusinessKeyBusinessOperation .");
		
		String cidade = "";
		String businessKey = "";
		String numeroInvestimento = "";
				
		for (VivoTaskFormItem item : formItens){
			
			if (FIELD_CIDADE.equals(item.getId())){
				
				cidade = item.getValue();
			} else if (FIELD_INVESTIMENTO.equals(item.getId())){
				
				numeroInvestimento = "1";
				if (VALUE_INVESTIMENTO_OBRAS.equals(item.getValue())){
					numeroInvestimento = "2";					
				}
			}
		}		
		
		Cidade city = cidadeDAO.findByPrimaryKey(new Long(cidade));
		
		//Valida se esta faltando algum dos campos obrigatórios para gerar o numero da instancia
		if (cidade == null || StringUtils.isEmpty(numeroInvestimento)){
			logger.error("Não foi possivel gerar BusinessKey para instancia.");
			logger.debug("cidade: " + cidade + " invest: " + numeroInvestimento);
			throw new MissingInformationException();
		}
		
		Long sequenceId = businessKeyDAO.nextval();
		
		businessKey = city.getUf().getName() + city.getCnl() + numeroInvestimento + sequenceId.toString() + "_0"; 
		logger.debug(businessKey);
		
		return businessKey;
	}
}
