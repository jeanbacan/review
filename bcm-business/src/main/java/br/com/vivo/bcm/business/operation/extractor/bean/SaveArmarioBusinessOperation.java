/**
 * 
 */
package br.com.vivo.bcm.business.operation.extractor.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.vivo.bcm.business.dao.IArmarioDAO;
import br.com.vivo.bcm.business.dao.ICidadeDAO;
import br.com.vivo.bcm.business.dao.IUFDAO;
import br.com.vivo.bcm.business.dto.extractor.ArmarioDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.Armario;
import br.com.vivo.bcm.business.model.Cidade;
import br.com.vivo.bcm.business.model.UF;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.operation.bean.BaseBusinessOperation;

/**
 * Salva Armario vindo da planilha
 * 
 * @author Jean Bacan
 * @since 30/06/2017
 *
 */
@Named("saveArmarioBusinessOperation")
public class SaveArmarioBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<ArmarioDTO, Boolean> {

	private static final Logger logger = Logger.getLogger(SaveArmarioBusinessOperation.class);

	@Inject
	@Named("armarioDAO")
	private IArmarioDAO armarioDAO;
	
	@Inject
	@Named("cidadeDAO")
	private ICidadeDAO cidadeDAO;
	
	@Inject
	@Named("ufDAO")
	private IUFDAO ufDAO;
	
	@Override
	@Transactional
	public Boolean execute(ArmarioDTO param) throws BusinessException {
		
		Boolean inserted = false;
		
		//CIdade nao encontrada, busca UF
		UF ufFilter = new UF();
		ufFilter.setName(param.getUf());
		List<UF> ufs = ufDAO.find(ufFilter);
		
		Cidade cidadeFilter = new Cidade();
		cidadeFilter.setName(param.getCidade());
		cidadeFilter.setUf(new UF());
		cidadeFilter.getUf().setUid(ufs.get(0).getUid());
		
		//Valida cidade
		List<Cidade> cidadesFound = cidadeDAO.find(cidadeFilter);
		
		if (cidadesFound.isEmpty()){
			
			Cidade cidade = new Cidade();
			cidade.setName(param.getCidade());
			cidade.setUf(new UF());
			cidade.setCnl(param.getCnl());
			cidade.getUf().setUid(ufs.get(0).getUid());
			
			cidade = cidadeDAO.save(cidade);

			cidadesFound.add(cidade);
		}
		
		//Valida armario
		Armario filter = new Armario();
		filter.setName(param.getArmario());
		
		List<Armario> armarioFound = armarioDAO.find(filter);
		
		//Define cidade
		filter.setCidade(cidadesFound.get(0));
		
		if (armarioFound.isEmpty()){
			armarioDAO.save(filter);
			logger.debug("Armário adicionado: " + param.getArmario());
					
			inserted = true;
		} else {
			//Caso cidade para o armario existente seja diferente do que veio na planilha, atualiza
			Armario armarioExistente = armarioFound.get(0);
			Cidade cidadePlanilha = cidadesFound.get(0);
			
			if (!(armarioExistente.getCidade().getUid().equals(cidadePlanilha.getUid()))){
				armarioExistente.setCidade(cidadePlanilha);
				armarioDAO.save(armarioExistente);
				
				inserted = true;
			}
		}
		return inserted;
	}

}
