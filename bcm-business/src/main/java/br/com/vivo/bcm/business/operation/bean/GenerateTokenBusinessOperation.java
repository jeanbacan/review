package br.com.vivo.bcm.business.operation.bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.vivo.bcm.business.dao.IMyDocumentDAO;
import br.com.vivo.bcm.business.dto.filter.FileTokenDTO;
import br.com.vivo.bcm.business.dto.filter.GenerateTokenDTO;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.enums.TokenType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.model.MyDocument;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.DocumentVO;
import br.com.vivo.bcm.business.vo.MyDocumentVO;
import br.com.vivo.rubeus.client.security.Principal;

/**
 * @author A0051460
 *
 */
@Named("generateTokenBusinessOperation")
public class GenerateTokenBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<GenerateTokenDTO, FileTokenDTO> {

	private static final Logger logger = Logger.getLogger(GenerateTokenBusinessOperation.class);

	@Inject
	@Named("myDocumentDAO")
	private IMyDocumentDAO myDocumentDAO;

	@Override
	@Transactional
	public FileTokenDTO execute(GenerateTokenDTO generateTokenDTO) throws BusinessException {
		logger.info("execute do generateTokenBusinessOperation - inicio");

		FileTokenDTO fileTokenDTO = null;
		Principal principal = (Principal) securityHelper.getLoggedPrincipal();

		MyDocument myDocument = null;

		if (TokenType.UPLOAD.equals(generateTokenDTO.getAccessType())) {

			String[] newNameFileList = super.systemConfiguration.getProperty(ConfigurationType.NEW_NAME_FILE.getKey()).split(";");

			String newName = "";

			// Altera o nome do arquivo selecionado pelo nome padronizado.
			for (String newNameFile : newNameFileList) {
				String[] nameFileValue = newNameFile.split("=");
				// Compara o tipo de upload selecionado vindo da web com o que está no banco.
				if (generateTokenDTO.getDocumentVO().getFileName().equals(nameFileValue[0])) {
					// Pega o nome padrão para o arquivo que esta no banco.
					newName = nameFileValue[1] + generateTokenDTO.getDocumentVO().getBusinessKey();

					// Separa o nome da extensão do arquivo.
					String[] fileExtension = generateTokenDTO.getDocumentVO().getName().split("\\.");

					// concatena novo nome do arquivo com a extensão.
					generateTokenDTO.getDocumentVO().setName(newName + "." + fileExtension[1]);

				}
			}

			fileTokenDTO = this.getTokenDocumentManager(generateTokenDTO);

			myDocument = new MyDocument();

			myDocument.setName(fileTokenDTO.getDocumentVO().getName());
			myDocument.setDocumentManagerId(fileTokenDTO.getDocumentVO().getUid());
			myDocument.setIsUploaded(fileTokenDTO.getDocumentVO().isUploaded());
			myDocument.setUser(principal.getUid());
			myDocument.setDocumentPath(fileTokenDTO.getDocumentVO().getDocumentPath());

			myDocument = this.myDocumentDAO.save(myDocument);
		} else {
			myDocument = this.myDocumentDAO.findByDocumentManagerId(generateTokenDTO.getDocumentVO().getUid());
			DocumentVO documentVO = new DocumentVO();
			documentVO.setName(myDocument.getDocumentPath());

			generateTokenDTO.setDocumentVO(documentVO);

			fileTokenDTO = this.getTokenDocumentManager(generateTokenDTO);
		}

		fileTokenDTO.setMyDocumentVO(new MyDocumentVO(myDocument));

		logger.info("execute do generateTokenBusinessOperation - fim");
		return fileTokenDTO;
	}

	private FileTokenDTO getTokenDocumentManager(GenerateTokenDTO generateTokenDTO) {

		logger.info("getTokenDocumentManager do generateTokenBusinessOperation - inicio");

		generateTokenDTO.setApplicationHash(this.systemConfiguration.getProperty(ConfigurationType.DM_APPLICATION_KEY.getKey()));
		PostMethod postMethod = new PostMethod(this.systemConfiguration.getProperty(ConfigurationType.DOCUMENT_MANAGER_TOKEN_URI.getKey()));

		RequestEntity requestEntity = null;
		FileTokenDTO fileTokenDTO = null;
		HttpClient httpClient = new HttpClient();
		String result = "";
		try {

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(generateTokenDTO);

			requestEntity = new StringRequestEntity(json, MediaType.APPLICATION_JSON, "UTF-8");

			postMethod.setRequestEntity(requestEntity);
			logger.info("inicio - Chamada para o Document-Manager");
			int rawResponse = httpClient.executeMethod(postMethod);
			logger.info("fim - Chamada para o Document-Manager");

			if (rawResponse == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
				ObjectMapper mapper = new ObjectMapper();
				fileTokenDTO = mapper.readValue(result, FileTokenDTO.class);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (HttpException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		logger.info("getTokenDocumentManager do generateTokenBusinessOperation - fim");
		return fileTokenDTO;
	}
}
