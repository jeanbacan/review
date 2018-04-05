/**
 * 
 */
package br.com.vivo.bcm.business.operation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.util.ApplicationConstants;
import br.com.vivo.rubeus.client.vo.GroupVO;

/**
 * @author A0051460
 *
 */
@Named("listGroupBusinessOperation")
public class ListGroupBusinessOperation extends BaseBusinessOperation	implements IBusinessOperation<Void, List<GroupVO>> {

	@Override
	public List<GroupVO> execute(Void t) throws BusinessException {

		List<GroupVO> groupVOs = new ArrayList<GroupVO>();

		for (GroupVO groupVO : super.securityHelper.getLoggedGroups()) {
			if (!ApplicationConstants.SECURITY_GROUP.equals(groupVO.getGroupType().getName())) {
				groupVOs.add(groupVO);
			}
		}

		return groupVOs;
	}

}
