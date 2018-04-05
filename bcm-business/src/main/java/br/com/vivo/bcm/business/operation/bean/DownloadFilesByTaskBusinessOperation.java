package br.com.vivo.bcm.business.operation.bean;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.vivo.bcm.business.dao.IActivitiFormVariablesDAO;
import br.com.vivo.bcm.business.dto.GridValuesDTO;
import br.com.vivo.bcm.business.dto.LineValueDTO;
import br.com.vivo.bcm.business.dto.ValueDTO;
import br.com.vivo.bcm.business.dto.filter.FileTokenDTO;
import br.com.vivo.bcm.business.dto.filter.GenerateTokenDTO;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.enums.ErrorCode;
import br.com.vivo.bcm.business.enums.TokenType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.MissingInformationException;
import br.com.vivo.bcm.business.model.ActivitiFormVariables;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.util.ZipFolder;
import br.com.vivo.bcm.business.vo.DocumentVO;

@Named("downloadFilesByTaskBusinessOperation")
public class DownloadFilesByTaskBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<String[], File> {

	private static final String ARQUIVOS_NOVA_AREA_TYPE = "arquivosNovaArea";

	private static final Logger logger = Logger.getLogger(DownloadFilesByTaskBusinessOperation.class);

	@Inject
	@Named("activitiFormVariablesDAO")
	private IActivitiFormVariablesDAO activitiFormVariablesDAO;

	@Inject
	@Named("generateTokenBusinessOperation")
	private IBusinessOperation<GenerateTokenDTO, FileTokenDTO> generateTokenBusinessOperation;
	
	
	@Override
	public File execute(String[] processInstances) throws BusinessException {

		logger.debug("execute do DownloadFilesByTaskBusinessOperation - begin - " + processInstances);

		if (processInstances == null || processInstances.length < 1){
			logger.error("Não existe taskId para gerar arquivos");
			throw new MissingInformationException();
		}
		
		String tempDirectory = System.getProperty("java.io.tmpdir");
		
		String processInstanceId = processInstances[0];
		List<FileTokenDTO> documentsToken = new ArrayList<FileTokenDTO>();
		
		String documentManagerAppKey = super.systemConfiguration.getProperty(ConfigurationType.DM_APPLICATION_KEY.getKey());
		String documentManagerDownloadURI = this.systemConfiguration.getProperty(ConfigurationType.DOCUMENT_MANAGER_DOWNLOAD_URI.getKey());
		String[] activitiFilesVarNames = super.systemConfiguration.getProperty(ConfigurationType.ACTIVITI_FILES_VARNAMES.getKey()).split(";");
		
		try {
			
			
			//Tarefas ja salvas dessa instancia
			HistoricProcessInstance processInstance = super.getHistoryService().createHistoricProcessInstanceQuery().includeProcessVariables().processInstanceId(processInstanceId).singleResult();
			List<HistoricTaskInstance> historicTasks = super.getHistoryService().createHistoricTaskInstanceQuery().includeTaskLocalVariables().finished()
					.processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().list();

			if (historicTasks == null || historicTasks.size() < 1){
				logger.error("Erro ao baixar arquivos para Download");
				throw new BusinessException(ErrorCode.NO_FILES);
			}
			
			for (Entry<String, Object> formProperty : historicTasks.get(0).getTaskLocalVariables().entrySet()){
				
				//Acessa apenas variaveis do tipo GRID de Arquivos
				if (ARQUIVOS_NOVA_AREA_TYPE.equals(formProperty.getKey())){
		
					ActivitiFormVariables activitiFormVariables = this.activitiFormVariablesDAO.findByPrimaryKey(new Long((String)formProperty.getValue()));
					GridValuesDTO gridValuesDTO = new ObjectMapper().readValue(activitiFormVariables.getValue(), GridValuesDTO.class);
					
					for (LineValueDTO dto : gridValuesDTO.getLines()){
						for (ValueDTO valueDTO : dto.getValues()){
							if (valueDTO.getMyDocumentVO() != null){
								
								GenerateTokenDTO generateDTO = new GenerateTokenDTO();
								generateDTO.setApplicationHash(documentManagerAppKey);
								generateDTO.setAccessType(TokenType.DOWNLOAD);
								generateDTO.setDocumentVO(valueDTO.getMyDocumentVO());
								
								FileTokenDTO tokenDTO = generateTokenBusinessOperation.execute(generateDTO); 
								documentsToken.add(tokenDTO);
								
								break;
							}
						}
					}
				}
			}
			
			//Traz documents fora da grid
			if (processInstance.getProcessVariables() != null && processInstance.getProcessVariables().size() > 0){
				for (Entry<String, Object> map : processInstance.getProcessVariables().entrySet()){
					if (Arrays.asList(activitiFilesVarNames).contains(map.getKey())){
						
						if (map.getValue() == null){
							continue;
						}
						
						GenerateTokenDTO generateDTO = new GenerateTokenDTO();
						generateDTO.setApplicationHash(documentManagerAppKey);
						generateDTO.setAccessType(TokenType.DOWNLOAD);
						generateDTO.setDocumentVO(new DocumentVO());
						generateDTO.getDocumentVO().setUid(new Long((String)map.getValue()));
						
						FileTokenDTO tokenDTO = generateTokenBusinessOperation.execute(generateDTO);
						
						documentsToken.add(tokenDTO);
					}
				}
			}
			
			//Cria diretorio de destino
			Path destinationFolder = Paths.get(tempDirectory + "//" + processInstance.getBusinessKey());
			if (Files.notExists(destinationFolder)){
				Files.createDirectory(destinationFolder);
			}
			
			for (FileTokenDTO dto : documentsToken){
				
				URL website = new URL(documentManagerDownloadURI + "//" + dto.getToken());
				
				//Define o path temporario com uma pasta do processo
				Path localFileDownload = Paths.get(tempDirectory + "//" + processInstance.getBusinessKey() + "//" + dto.getMyDocumentVO().getName());
									
				logger.debug(localFileDownload);

				try (InputStream in = website.openStream()) {
					Files.copy(in, localFileDownload, StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					logger.error(e);
				}				
			}
			
			//Zip todos arquivos em FILE_TEMP_PATH + processInstance.getBusinessKey()
			Path pathZipFile = Paths.get(tempDirectory + "//" + processInstance.getBusinessKey() + "//" + processInstance.getBusinessKey() + ZipFolder.FILE_EXTENSION_ZIP);
			ZipFolder.zipFolder(pathZipFile);
			
			//Resgata nome do processo e monta zip
			return pathZipFile.toFile();
			
		} catch (Exception e) {
			logger.error("Erro ao baixar arquivos para Download", e);
			throw new BusinessException(ErrorCode.GENERIC);
		}
	}
}
