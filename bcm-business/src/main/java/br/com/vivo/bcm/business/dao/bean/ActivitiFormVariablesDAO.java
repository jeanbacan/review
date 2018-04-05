/**
 * 
 */
package br.com.vivo.bcm.business.dao.bean;

import javax.inject.Named;

import br.com.vivo.bcm.business.dao.IActivitiFormVariablesDAO;
import br.com.vivo.bcm.business.model.ActivitiFormVariables;

/**
 * @author A0051460
 *
 */
@Named("activitiFormVariablesDAO")
public class ActivitiFormVariablesDAO extends JPABaseDAO<Long, ActivitiFormVariables> implements IActivitiFormVariablesDAO {

}