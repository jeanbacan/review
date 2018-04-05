package br.com.vivo.bcm.business.dao.bean;

import java.io.Serializable;

import br.com.vivo.bcm.business.dao.IBaseDAO;
import br.com.vivo.bcm.business.model.IUID;

/**
 * @author Deividi Cavarzan
 */
public abstract class BaseDAO<B extends Serializable, T extends IUID<B>> implements IBaseDAO<B, T> {
}