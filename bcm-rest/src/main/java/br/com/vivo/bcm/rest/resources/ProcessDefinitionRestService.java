package br.com.vivo.bcm.rest.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.jersey.multipart.FormDataParam;

import br.com.vivo.bcm.business.annotation.qualifiers.SystemConfigurationQualifier;
import br.com.vivo.bcm.business.dto.ProcessInstanceStartDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.ProcessDefinitionVO;
import br.com.vivo.bcm.business.vo.UploadBPMNVO;
import br.com.vivo.bcm.rest.base.BaseRestService;
import br.com.vivo.configurationutils.IConfiguration;

@Controller
@RequestMapping(value = BaseRestService.PATH_PROCESS_DEFINITION)
public class ProcessDefinitionRestService {

	private static final Logger logger = Logger.getLogger(ProcessDefinitionRestService.class);
	
	@Inject
	@SystemConfigurationQualifier
	private IConfiguration configuration;
	
	@Inject
	@Named("listProcessDefinitionsBusinessOperation")
	private IBusinessOperation<FilterDTO, List<ProcessDefinitionVO>> listProcessDefinitionsBusinessOperation;
	
	@Inject
	@Named("getProcessStartFormBusinessOperation")
	private IBusinessOperation<String, ProcessInstanceStartDTO> getProcessStartFormBusinessOperation;
	
	@Inject
	@Named("uploadBPMNBusinessOperation")
	private IBusinessOperation<UploadBPMNVO, Void> uploadBPMNBusinessOperation;
	
	@PermitAll
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ProcessDefinitionVO> list(@RequestBody FilterDTO filterDTO) throws BusinessException {
		return this.listProcessDefinitionsBusinessOperation.execute(filterDTO);
	}
	
	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_FORM, method = RequestMethod.GET)
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public ProcessInstanceStartDTO getStartForm() throws BusinessException {
		return this.getProcessStartFormBusinessOperation.execute(new String());
	}
	
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@RequestMapping(value = BaseRestService.PATH_UPLOAD_BPMN, method = RequestMethod.POST)
	@ResponseBody
	@PermitAll
	public void list(@FormDataParam("uploadedFile") InputStream inputStream, @QueryParam("label") String label) throws BusinessException {
		

		FileOutputStream outputStream = null;

		File directory = new File(this.configuration.getProperty(ConfigurationType.BPMN_IMPORT_TEMP_FILEPATH.getKey()));
		if (!directory.exists()) {
			directory.mkdir();
		}
		String filePath = this.configuration.getProperty(ConfigurationType.BPMN_IMPORT_TEMP_FILEPATH.getKey()) + label + "_BPMN_" + System.currentTimeMillis();
		try {
			File fileOut = new File(filePath);
			outputStream = new FileOutputStream(fileOut);

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
					outputStream.flush();
				}
			} catch (Exception e) {
				logger.error(e);
				throw new BusinessException(e);
			}
		}

		UploadBPMNVO uploadBPMNVO = new UploadBPMNVO();
		uploadBPMNVO.setFilePath(filePath);
		uploadBPMNVO.setFileName(label);

		this.uploadBPMNBusinessOperation.execute(uploadBPMNVO);
	}
	
	

}