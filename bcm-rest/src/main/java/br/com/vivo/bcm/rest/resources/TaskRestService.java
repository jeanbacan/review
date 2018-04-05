package br.com.vivo.bcm.rest.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.dto.AssignTaskToUserDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.KanbanTaskType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.VivoTask;
import br.com.vivo.bcm.rest.base.BaseRestService;

@Controller
@RequestMapping(value = BaseRestService.PATH_TASK)
public class TaskRestService {

	private static final Logger logger = Logger.getLogger(TaskRestService.class);

	@Inject
	@Named("listKanbanTasksBusinessOperation")
	private IBusinessOperation<FilterDTO, Map<KanbanTaskType, List<VivoTask>>> listKanbanTasksBusinessOperation;

	@Inject
	@Named("assignTaskToMeBusinessOperation")
	private IBusinessOperation<String[], Void> assignTaskToMeBusinessOperation;
	
	@Inject
	@Named("downloadFilesByTaskBusinessOperation")
	private IBusinessOperation<String[], File> downloadFilesByTaskBusinessOperation;

	@Inject
	@Named("completeTaskBusinessOperation")
	private IBusinessOperation<VivoTask, Void> completeTaskBusinessOperation;

	@Inject
	@Named("assignTaskToUserBusinessOperation")
	private IBusinessOperation<AssignTaskToUserDTO, Void> assignTaskToUserBusinessOperation;

	@Inject
	@Named("unassignTaskBusinessOperation")
	private IBusinessOperation<String, Void> unassignTaskBusinessOperation;

	@Inject
	@Named("getOpenedTaskBusinessOperation")
	private IBusinessOperation<String, VivoTask> getOpenTaskBusinessOperation;

	@Inject
	@Named("getHistoricTaskBusinessOperation")
	private IBusinessOperation<String, VivoTask> getHistoricTaskBusinessOperation;

	@Inject
	@Named("saveDraftTaskBusinessOperation")
	private IBusinessOperation<VivoTask, Void> saveDraftTaskBusinessOperation;

	@PermitAll
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<KanbanTaskType, List<VivoTask>> listTasks(@RequestBody FilterDTO filterDTO) throws BusinessException {
		return this.listKanbanTasksBusinessOperation.execute(filterDTO);
	}

	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_SAVE_DRAFT, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Void saveDraft(@RequestBody VivoTask vivoTask) throws BusinessException {
		logger.info("saveDraft do TaskRestService - Inicio");
		
		return this.saveDraftTaskBusinessOperation.execute(vivoTask);
	}

	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_TASK_ASSIGN_TO_USER, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Void assignToUser(@RequestBody AssignTaskToUserDTO assignTaskToUserDTO) throws BusinessException {
		return this.assignTaskToUserBusinessOperation.execute(assignTaskToUserDTO);
	}

	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_COMPLETE_TASK, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Void completeTask(@RequestBody VivoTask vivoTask) throws BusinessException {
		return this.completeTaskBusinessOperation.execute(vivoTask);
	}

	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_TASK_ASSIGN_TO_ME, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Void assignToMe(@RequestBody String[] taskId) throws BusinessException {
		return this.assignTaskToMeBusinessOperation.execute(taskId);
	}

	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_OPEN_TASK, method = RequestMethod.GET)
	public VivoTask getTask(@PathParam(BaseRestService.PARAM_TASKID) String taskId) throws BusinessException {
		logger.info("getTask do TaskRestService - Inicio");
		logger.info("Task requisitada: " + taskId);
		return this.getOpenTaskBusinessOperation.execute(taskId);
	}

	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_HISTORIC_TASK, method = RequestMethod.GET)
	public VivoTask getHistoricTask(@PathParam(BaseRestService.PARAM_TASKID) String taskId) throws BusinessException {
		logger.info("getTask do TaskRestService - Inicio");
		logger.info("Task requisitada: " + taskId);
		return this.getHistoricTaskBusinessOperation.execute(taskId);
	}
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@RequestMapping(value = BaseRestService.PATH_DOWNLOAD, method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadAllFiles(@PathParam(BaseRestService.PARAM_PROCESS_INSTANCE_ID) String processInstanceId) throws BusinessException, FileNotFoundException {
		logger.info("downloadAllfiles do TaskRestService - Inicio");
		
		String[] ids =  new String[]{processInstanceId};
		File file = this.downloadFilesByTaskBusinessOperation.execute(ids);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
	    header.setContentDispositionFormData("attachment", file.getName().replace(" ", "_"));
	    header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName().replace(" ", "_"));
	    header.setContentLength(file.length());

	    InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
	    return new ResponseEntity<InputStreamResource>(isr, header, HttpStatus.OK);
		//return Response.ok(file).header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ).build();
	}
	
	@PermitAll
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = BaseRestService.PATH_TASK_UNASSIGN, method = RequestMethod.POST)
	public Void unassign(@RequestBody String taskId) throws BusinessException {
		return this.unassignTaskBusinessOperation.execute(taskId);
	}
}
