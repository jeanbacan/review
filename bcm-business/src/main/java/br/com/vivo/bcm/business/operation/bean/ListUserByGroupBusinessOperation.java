package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.rubeus.client.vo.GroupVO;
import br.com.vivo.rubeus.client.vo.UserVO;

@Named("listUserByGroupBusinessOperation")
public class ListUserByGroupBusinessOperation extends BaseBusinessOperation	implements IBusinessOperation<String, List<UserVO>> {

	private static final Logger logger = Logger.getLogger(ListUserByGroupBusinessOperation.class);

	@Override
	public List<UserVO> execute(String candidateGroup) throws BusinessException {
		logger.info("Execute do ListUserByGroupBusinessOperation - Inicio");
		List<UserVO> userVOs = null;

		GroupVO group = super.securityHelper.getLoggedGroupByCadidateGroup(candidateGroup);

		if (group != null) {
			userVOs = new ArrayList<UserVO>();
			userVOs = super.securityHelper.getUsersByGroupId(group.getUid());
		}

		logger.info("Execute do ListUserByGroupBusinessOperation - Fim");

		return userVOs;
	}
}
