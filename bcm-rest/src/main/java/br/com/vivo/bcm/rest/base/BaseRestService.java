package br.com.vivo.bcm.rest.base;

/**
 * Classe abstrata para gerenciar os serviços que serão carregados pelos endpoints REST.
 * 
 * @author G0029875
 */
public abstract class BaseRestService {
	
	public static final String QUERY_OFFSET = "offset";
	public static final String QUERY_OFFSET_DEFAULT = "0";
	public static final String QUERY_SIZE = "size";
	public static final String QUERY_SIZE_DEFAULT = "1000";
	public static final String QUERY_UID = "uid";

	public static final String PARAM_GROUP = "group";
	public static final String PARAM_TOKEN = "token";
	public static final String PARAM_TASKID = "taskId";
	public static final String PARAM_TTPE = "type";
	public static final String PARAM_CANDIDATE_GRUP = "candidateGroup";
	public static final String PARAM_PROCESS_INSTANCE_ID = "processInstanceId";
	public static final String PARAM_PROCESS_DEFINITION_ID = "processDefinitionId";
	
	public static final String PATH_PROCESS_INSTANCE_LIST = "/processInstanceList";
	public static final String PATH_UPLOAD_TOKEN = "/upload";
	public static final String PATH_CALLBACK_TOKEN = "/callback";
	public static final String PATH_COMBO = "/combo";
	public static final String PATH_EXTRACT = "/extract";
	public static final String PATH_ARMARIO = "/armario";
	public static final String PATH_DOWNLOAD = "/download";

	public static final String PATH_SAVE_INSTANCE = "/saveInstance";
	public static final String PATH_PARAM_PROCESS_INSTANCE_ID = "/instanceDetail";
	public static final String PATH_CREATE_TOKEN = "/token";
	public static final String PATH_QUALIFIERS = "/qualifiers";
	public static final String PATH_QUALIFYING = "/qualifying";
	public static final String PATH_CONFIGURATION = "/configuration";
	public static final String PATH_REPORT = "/report";
	public static final String PATH_PROGRESS = "/progress";
	public static final String PATH_COMPLETE = "/complete";
	public static final String PATH_TASK = "/task";
	public static final String PATH_COUNT = "/count";
	public static final String PATH_COUNT_PARAM = "/{" + PARAM_TTPE + "}";
	public static final String PATH_PROCESS_INSTANCE = "/processInstance";
	public static final String PATH_PROCESS_DEFINITION = "/processDefinition";
	public static final String PATH_FORM = "/form";
	public static final String PATH_TASK_ASSIGN_TO_ME = "/assignToMe";
	public static final String PATH_COMPLETE_TASK = "/complete";
	public static final String PATH_SAVE_DRAFT = "/saveDraft";
	public static final String PATH_TASK_ASSIGN_TO_USER = "/assignToUser";
	public static final String PATH_TASK_UNASSIGN = "/unassign";
	public static final String PATH_GET_LOG = "/projectLog";
	public static final String PATH_ACTIVATE = "/activate";
	public static final String PATH_SUSPEND = "/suspend";
	public static final String PATH_CANCEL = "/cancel";
	public static final String PATH_UPLOAD_BPMN = "/uploadBPMN";	
	public static final String PATH_GET_TASK = "/getTask";
	public static final String PATH_CREATE_TASK = "/createTask";
	public static final String PATH_NOTIFY = "/notify";
	public static final String PATH_USERS = "/users";
	public static final String PATH_GROUPS = "/groups";
	public static final String PATH_OPEN_TASK = "/openTask";
	public static final String PATH_HISTORIC_TASK = "/historicTask";

}