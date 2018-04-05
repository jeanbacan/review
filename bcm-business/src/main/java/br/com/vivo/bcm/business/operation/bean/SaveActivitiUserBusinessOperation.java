/**
 * 
 */
package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Named;

import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.rubeus.client.security.IPrincipal;
import br.com.vivo.rubeus.client.vo.UserVO;

/**
 * Salva usuário caso não exista na base do activiti
 * 
 * @author Jean Bacan
 * @since 18/05/2017
 *
 */
@Named("saveActivitiUserBusinessOperation")
public class SaveActivitiUserBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<Void, Void> {

	private static final Logger logger = Logger.getLogger(SaveActivitiUserBusinessOperation.class);

	@Override
	@Transactional
	public Void execute(Void param) throws BusinessException {
		logger.info("execute do SaveActivitiUserBusinessOperation.");
		
		IPrincipal principal = this.securityHelper.getLoggedPrincipal();
		
		if (getIdentityService().createUserQuery().userId(principal.getUid().toString()).singleResult() == null){
			User user = getIdentityService().newUser(principal.getUid().toString());
			
			UserVO userVO = this.securityHelper.findUserById(principal.getUid());
			
			user.setFirstName(userVO.getUsername());
			user.setLastName(userVO.getName());
			
			getIdentityService().saveUser(user);
		}
		return null;
	}

}
