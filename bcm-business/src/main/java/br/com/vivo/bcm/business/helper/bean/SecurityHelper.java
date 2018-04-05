package br.com.vivo.bcm.business.helper.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.annotation.qualifiers.SystemConfigurationQualifier;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.enums.SecurityType;
import br.com.vivo.bcm.business.helper.ISecurityHelper;
import br.com.vivo.bcm.business.util.ApplicationConstants;
import br.com.vivo.configurationutils.IConfiguration;
import br.com.vivo.rubeus.client.dto.ListGroupsByFilterInDTO;
import br.com.vivo.rubeus.client.dto.ListGroupsByFilterOutDTO;
import br.com.vivo.rubeus.client.dto.ListUsersByFilterInDTO;
import br.com.vivo.rubeus.client.dto.ListUsersByFilterOutDTO;
import br.com.vivo.rubeus.client.exception.RubeusException;
import br.com.vivo.rubeus.client.security.IPrincipal;
import br.com.vivo.rubeus.client.security.ISecurityContext;
import br.com.vivo.rubeus.client.security.PrincipalAuthorization;
import br.com.vivo.rubeus.client.security.PrincipalAuthorizationParam;
import br.com.vivo.rubeus.client.service.GroupService;
import br.com.vivo.rubeus.client.service.UserService;
import br.com.vivo.rubeus.client.vo.GroupVO;
import br.com.vivo.rubeus.client.vo.UserVO;

/**
 * Metodos utilitarios com Security
 * 
 * @author Jean Bacan
 * @since 10/05/2017
 */
@Named("securityHelper")
public class SecurityHelper implements ISecurityHelper {
	
	private static final Logger logger = Logger.getLogger(SecurityHelper.class);

	@Inject
	@SystemConfigurationQualifier
	private IConfiguration configuration;

	@Inject
	private ISecurityContext securityContext;

	@Inject
	private GroupService groupService;

	@Inject
	private UserService userService;

	public IPrincipal getLoggedPrincipal() {
		
		logger.info("getLoggedPrincipal");
		return this.securityContext.getPrincipal();
	}

	@Override
	public List<GroupVO> getLoggedGroups() {
		Long userId = getLoggedPrincipal().getUid();
		return getGroupsByUserId(userId);
	}

	@Override
	public List<GroupVO> getGroupsByUserId(Long userId) {
		logger.info("getLoggedGroups - Inicio");
		
		ListGroupsByFilterInDTO listGroupsByFilterInDTO = new ListGroupsByFilterInDTO();
		ListGroupsByFilterOutDTO groupsByFilterOutDTO = new ListGroupsByFilterOutDTO();

		listGroupsByFilterInDTO.setIdUser(userId);
		listGroupsByFilterInDTO.setApplicationKey(this.configuration.getProperty(ConfigurationType.APPLICATION_KEY.getKey()));

		try {
			groupsByFilterOutDTO = this.groupService.listGroupsByFilter(listGroupsByFilterInDTO);

		} catch (RubeusException e) {
			logger.error(e);
		}
		logger.info("getLoggedGroups - Fim");
		return groupsByFilterOutDTO.getGroups();
	}
	
	@Override
	public GroupVO getGroupByCandidateGroup(String candidateGroup) {
		logger.info("getGroupByCandidateGroup - Inicio");
		
		ListGroupsByFilterInDTO listGroupsByFilterInDTO = new ListGroupsByFilterInDTO();
		ListGroupsByFilterOutDTO groupsByFilterOutDTO = new ListGroupsByFilterOutDTO();

		listGroupsByFilterInDTO.setApplicationKey(this.configuration.getProperty(ConfigurationType.APPLICATION_KEY.getKey()));

		try {
			groupsByFilterOutDTO = this.groupService.listGroupsByFilter(listGroupsByFilterInDTO);

			for (GroupVO groupVO : groupsByFilterOutDTO.getGroups()) {
				if (candidateGroup.equals(groupVO.getName())) {
					return groupVO;
				}
			} 
			
		} catch (RubeusException e) {
			logger.error(e);
		}
				
		return null;
	}

	public List<String> getLoggedCadidateGroups() {
		logger.info("getLoggedCadidateGroups - Inicio");
		
		List<PrincipalAuthorization> authorizationVOs = getLoggedPrincipal().getAuthorizations();
		List<String> myGroups = new ArrayList<String>();

		for (PrincipalAuthorization auth : authorizationVOs) {
			if (SecurityType.READ_TASKS.getKey().equals(auth.getType())) {
				List<PrincipalAuthorizationParam> authParams = auth.getAuthorizationParams();
				for (PrincipalAuthorizationParam param : authParams) {
					if (SecurityType.CANDIDATE_GROUP.getKey().equals(param.getKey())) {
						myGroups.add(param.getValue());
						break;
					}
				}
			}
		}
		
		logger.info("getLoggedCadidateGroups - Fim");
		return myGroups;
	}

	@Override
	public GroupVO getLoggedGroupByCadidateGroup(String candidateGroup) {
		
		logger.info("getLoggedGroupByCadidateGroup - Inicio");
		logger.debug("getLoggedGroupByCadidateGroup - Inicio, " + candidateGroup);

		List<GroupVO> groups = this.getLoggedGroups();

		for (GroupVO groupVO : groups) {
			if (candidateGroup.equals(groupVO.getName())) {
				return groupVO;
			}
		}
		return null;
	}

	@Override
	public List<UserVO> getUsersByGroupId(Long groupId) {
		
		logger.info("getUsersByGroupId - Inicio");

		ListUsersByFilterInDTO listUsersByFilterInDTO = new ListUsersByFilterInDTO();
		ListUsersByFilterOutDTO listUsersByFilterOutDTO = new ListUsersByFilterOutDTO();

		listUsersByFilterInDTO.setGroupId(groupId);
		listUsersByFilterInDTO.setApplicationKey(this.configuration.getProperty(ConfigurationType.APPLICATION_KEY.getKey()));

		try {
			listUsersByFilterOutDTO = this.userService.listUsersByFilter(listUsersByFilterInDTO);

		} catch (RubeusException e) {
			logger.error(e);
			return new ArrayList<UserVO>();
		}

		logger.info("getUsersByGroupId - Fim");
		
		return listUsersByFilterOutDTO.getUsers();
	}
	
	public List<UserVO> getUsersByGroupName(String groupName) {
		
		logger.info("getUsersByGroupName - Inicio");

		ListUsersByFilterInDTO listUsersByFilterInDTO = new ListUsersByFilterInDTO();
		ListUsersByFilterOutDTO listUsersByFilterOutDTO = new ListUsersByFilterOutDTO();

		listUsersByFilterInDTO.setGroupName(groupName);
		listUsersByFilterInDTO.setApplicationKey(this.configuration.getProperty(ConfigurationType.APPLICATION_KEY.getKey()));

		try {
			listUsersByFilterOutDTO = this.userService.listUsersByFilter(listUsersByFilterInDTO);
			
		} catch (RubeusException e) {
			logger.error(e);
			return new ArrayList<UserVO>();
		}

		logger.info("getUsersByGroupName - Fim");
		
		return listUsersByFilterOutDTO.getUsers();
	}
	

	public UserVO findUserById(Long uid) {
		logger.info("findUserById - Inicio");

		List<GroupVO> groupVOs = this.getGroupsByUserId(uid);

		Long groupId = groupVOs.get(0).getUid();

		List<UserVO> userVOs = this.getUsersByGroupId(groupId);

		for (UserVO userVO : userVOs) {
			if (userVO.getUid().equals(new Long(uid))) {
				
				logger.info("findUserById - Fim");
				
				return userVO;
			}
		}

		logger.info("findUserById - Fim");
		return null;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.helper.ISecurityHelper#isLoggedUserAdmin()
	 */
	@Override
	public boolean isLoggedUserAdmin() {
		logger.info("hasAdminAuthorization - Inicio");
		List<PrincipalAuthorization> authorizationVOs = getLoggedPrincipal().getAuthorizations();

		for (PrincipalAuthorization principalAuthorization : authorizationVOs) {
			if (principalAuthorization.getCode().equals(ApplicationConstants.PERMIT_ALL.toString())) {
				
				logger.info("hasAdminAuthorization - Fim");
				return true;
			}
		}

		logger.info("hasAdminAuthorization - Fim");
		
		return false;
	}

	/* (non-Javadoc)
	 * @see br.com.vivo.bcm.business.helper.ISecurityHelper#getLoggedStartProcessCandidateGroup()
	 */
	@Override
	public String getLoggedStartProcessCandidateGroup() {
		String candidateGroup = null;
		int isB2C = this.getLoggedCadidateGroups().indexOf("B2C");
		int isB2B = this.getLoggedCadidateGroups().indexOf("B2B");
		if (isB2C >= 0){
			candidateGroup = "B2C";	
		} else if (isB2B >= 0){
			candidateGroup = "B2B";	
		}
		return candidateGroup;
	}
	
}
