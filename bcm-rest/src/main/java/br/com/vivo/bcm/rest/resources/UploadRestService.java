package br.com.vivo.bcm.rest.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vivo.bcm.business.dto.filter.FileTokenDTO;
import br.com.vivo.bcm.business.dto.filter.GenerateTokenDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.MyDocumentVO;
import br.com.vivo.bcm.rest.base.BaseRestService;

@Controller
@RequestMapping(value = BaseRestService.PATH_UPLOAD_TOKEN)
public class UploadRestService {

	private static final Logger logger = Logger.getLogger(UploadRestService.class);

	@Inject
	@Named("generateTokenBusinessOperation")
	private IBusinessOperation<GenerateTokenDTO, FileTokenDTO> generateTokenBusinessOperation;

	@Inject
	@Named("callBackSuccessUploadBusinessOperation")
	private IBusinessOperation<MyDocumentVO, Void> callBackSuccessUploadBusinessOperation;
	
	@PermitAll
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public FileTokenDTO generateUploadToken(@RequestBody GenerateTokenDTO generateTokenDTO) throws BusinessException {
		logger.info("Gerando o token");
		return this.generateTokenBusinessOperation.execute(generateTokenDTO);
	}

	@PermitAll
	@ResponseBody
	@RequestMapping(value = BaseRestService.PATH_CALLBACK_TOKEN, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response callbackSucessUpload(@RequestBody MyDocumentVO myDocumentVO) throws BusinessException {
		logger.info("Callback");
		this.callBackSuccessUploadBusinessOperation.execute(myDocumentVO);

		return Response.ok().build();
	}
}