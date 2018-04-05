package br.com.vivo.bcm.business.helper;

import java.util.List;

import br.com.vivo.bcm.business.exception.NotAllowedException;
import br.com.vivo.rubeus.client.security.IPrincipal;
import br.com.vivo.rubeus.client.vo.GroupVO;
import br.com.vivo.rubeus.client.vo.UserVO;

/**
 * Interface para metodos utilidores com Security
 * @author Jean Bacan
 *
 */
public interface ISecurityHelper {

	
	/**
	 * @return IPrincipal Credenciais usuario logado
	 */
	IPrincipal getLoggedPrincipal();
	
	/**
	 * @return List<String> Lista de candidate group do usuario logado
	 */
	List<String> getLoggedCadidateGroups();
	
	/**
	 * @return List<String> Lista de candidate group do usuario logado
	 */
	GroupVO getLoggedGroupByCadidateGroup(String candidateGroup);
	

	/**
	 * @return List<UserVO> Lista de usuários através do id docandidate group
	 */
	List<UserVO> getUsersByGroupId(Long groupId);

	/**
	 * @return List<GroupVO> Lista de grupos através do id usuario logado
	 */
	List<GroupVO> getLoggedGroups();

	
	/**
	 * @return List<GroupVO> Lista de grupos através do id usuario
	 */
	List<GroupVO> getGroupsByUserId(Long userId);
	
	/**
	 * @return List<UserVO> Lista de usuários através do nome do grupo
	 */
	List<UserVO> getUsersByGroupName(String groupName);

	/**
	 * @return UserVO através do id usuario
	 */
	UserVO findUserById(Long uid);
	
	/**
	 * Verifica se o usuário logado é admin
	 * @return boolean true quando possuir a authorization AplicationConstants.PERMIT_ALL
	 */
	boolean isLoggedUserAdmin();
	
	/**
	 * Retorna grupo de negocio do usuario logado para a abertura de um projeto
	 * @return String candidateGroup
	 * @throws NotAllowedException 
	 */
	String getLoggedStartProcessCandidateGroup();

	/**
	 * @param candidateGroup
	 * @return
	 */
	GroupVO getGroupByCandidateGroup(String candidateGroup);
}
