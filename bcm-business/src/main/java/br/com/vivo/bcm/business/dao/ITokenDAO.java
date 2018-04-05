package br.com.vivo.bcm.business.dao;

import java.util.List;

import br.com.vivo.bcm.business.model.Token;

/**
 * @author G0029875
 */
public interface ITokenDAO extends IJPABaseDAO<Long, Token> {
	
	List<Token> findAll();

	Token validadeTokenByHash(String tokenHash);
}