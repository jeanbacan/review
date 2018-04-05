package br.com.vivo.bcm.business.operation.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.vivo.bcm.activiti.execution.ReportCompleteSQLExecution;
import br.com.vivo.bcm.activiti.mapper.ReportCompleteMapper;
import br.com.vivo.bcm.activiti.query.ReportCompleteQuery;
import br.com.vivo.bcm.business.dao.IArmarioDAO;
import br.com.vivo.bcm.business.dto.GridValuesDTO;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.NotAllowedException;
import br.com.vivo.bcm.business.model.Armario;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.util.ApplicationConstants;
import br.com.vivo.bcm.business.util.ComboDescriptions;
import br.com.vivo.bcm.business.util.ConverterUtil;
import br.com.vivo.bcm.business.vo.GridReportVO;
import br.com.vivo.bcm.business.vo.ReportColumnVO;
import br.com.vivo.bcm.business.vo.ReportRowVO;
import br.com.vivo.bcm.business.vo.ReportVO;

/**
 * BO que gera os relatórios
 * 
 * @author Jean Bacan?
 */
@Named("reportCompleteBusinessOperation")
public class ReportCompleteBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<FilterDTO, ReportVO> {

	private static final String ARMARIO_HEADER = "ARMARIO";

	private static final Logger logger = Logger.getLogger(ReportCompleteBusinessOperation.class);

	@Inject
	@Named("armarioDAO")
	private IArmarioDAO armarioDAO;

	@Override
	public ReportVO execute(FilterDTO filterDTO) throws BusinessException {
		logger.debug("execute do ReportCompleteBusinessOperation - inicio");

		 boolean isAdmin = super.securityHelper.isLoggedUserAdmin();
		 if (!isAdmin) {
		 throw new NotAllowedException();
		 }

		String statusFilter = this.getStatusFilter(filterDTO);
		String ufFilter = this.getUFFilter(filterDTO);

		// Efetua replaces
		ReportCompleteSQLExecution customSqlExecution = new ReportCompleteSQLExecution(ReportCompleteMapper.class);
		customSqlExecution.setStatusFilter(statusFilter);
		customSqlExecution.setUfFilter(ufFilter);

		List<ReportCompleteQuery> results = getManagementService().executeCustomSql(customSqlExecution);

		Set<Long> armariosIds = new TreeSet<Long>();
		List<ReportRowVO> rows = new ArrayList<ReportRowVO>();

		for (ReportCompleteQuery processVO : results) {
			
			List<GridReportVO> gridArmariosVOList = new ArrayList<GridReportVO>();
			List<GridReportVO> gridArquivosVOList = new ArrayList<GridReportVO>();

			try {

				// Caso exista grid de armarios, Converte
				if (processVO.getGridArmario() != null) {
					GridValuesDTO gridArmarios = new ObjectMapper().readValue(processVO.getGridArmario(), GridValuesDTO.class);
					gridArmariosVOList = ConverterUtil.transformToGridVOLines(gridArmarios);
				}

				// Caso exista grid de arquivos, Converte
				if (processVO.getGridArquivo() != null) {
					GridValuesDTO gridArquivos = new ObjectMapper().readValue(processVO.getGridArquivo(), GridValuesDTO.class);
					gridArquivosVOList = ConverterUtil.transformToGridVOLines(gridArquivos);
				}

				// Se nao houver registro de grid, adicionar um para que mostre a instancia vazia no relatorio
				if (gridArquivosVOList.size() < 1 && gridArmariosVOList.size() < 1) {
					gridArquivosVOList.add(new GridReportVO());
				}

			} catch (Exception e) {
				logger.error("Não foi possivel converter grid", e);
			}

			// Preenche linhas conforme linhas do grid de armarios
			for (GridReportVO gridArmariosVO : gridArmariosVOList) {
				List<ReportColumnVO> columnsVO = this.getColumns(processVO, gridArmariosVO);

				ReportRowVO rowVO = new ReportRowVO();
				rowVO.setProcessInstanceId(processVO.getProcessInstanceId());
				rowVO.setProcessInstanceColumns(columnsVO);

				rows.add(rowVO);

				if (gridArmariosVO.getArmario() != null) {
					armariosIds.add(Long.parseLong(gridArmariosVO.getArmario()));
				}
			}

			// Preenche linhas conforme linhas do grid de novas areas
			for (GridReportVO gridArquivosVO : gridArquivosVOList) {
				List<ReportColumnVO> columnsVO = this.getColumns(processVO, gridArquivosVO);

				ReportRowVO rowVO = new ReportRowVO();
				rowVO.setProcessInstanceId(processVO.getProcessInstanceId());
				rowVO.setProcessInstanceColumns(columnsVO);

				rows.add(rowVO);
			}
		}

		List<Armario> armarios = new ArrayList<>();
		if (armariosIds != null && armariosIds.size() > 0){
			armarios = armarioDAO.findByPrimaryKeys(new ArrayList<Long>(armariosIds));
		}

		// Troca ids dos armários
		for (ReportRowVO vo : rows) {
			for (ReportColumnVO columnVO : vo.getProcessInstanceColumns()) {

				if (ARMARIO_HEADER.equals(columnVO.getColumnHeader()) && StringUtils.isNotEmpty(columnVO.getColumnValue())) {
					Armario armarioModel = new Armario();

					try {
						armarioModel.setUid(Long.parseLong(columnVO.getColumnValue()));

						int nameIndex = armarios.indexOf(armarioModel);
						String armarioName = armarios.get(nameIndex).getName();

						columnVO.setColumnValue(armarioName);

					} catch (Exception e) {
						logger.error("Erro ao converter armário", e);
					}
					break;
				}
			}
		}

		ReportVO reportVO = new ReportVO();

		String[] headers = new String[] { "Número BC", "Início Proc.", "Fim Proc.", "Aberto por", "Progresso", "Diretoria", "UF", "Cidade", "Tarefa", "Grupo", 
				"Inicio", "Fim",
				"Previsto", "Concluído por", "Tipo Tecnologia", "Fluxo Investimento", "Tipo Investimento", "Possui Interesse", "Sugerir Inclusão Area", "Mercado Completo B2C", 
				"Mercado Completo B2B", "Levantamento Válido", "Nova area", "Armário", "Tx. Penetração B2C", "Tx. Pen. Prédio B2C", 
				"Tx. Penetração B2B", "Tx. Pen. Prédio B2B", "Inicio Prev. CM", "Início CM", "Fim Prev. CM", "Fim CM", "Inicio Prev. LM", "Início LM", "Fim Prev. LM", "Fim LM",
				
				"Quantidade Areas",
				
				"RA","RB","RC","RD","Demanda B2C","NA","NB","NC","PA","Demanda B2B","Facilidades", 
				
				"Resultado Financeiro", "Observação Resultado", "Aprovação CDG", "Observação CDG", "Revisar Priorização Obra", "Observação Priorização",
				"Ano Priorização", "Quarter Priorização", "Central",
				
				"Conclusão Prevista ETP", "Conclusão ETP", "Observação ETP",
				"Conclusão Prevista Base", "Conclusão Base", "Observação Base",
				"Conclusão Prevista Projeto", "Conclusão Projeto", "Observação Projeto",
				"Conclusão Prevista Licença", "Conclusão Licença", "Observação Licença",
				"Conclusão Construção", "Conclusão Prevista Construção", "Observação Construção",
				"Total facilidades entregues", "Observação entrega total" };

		reportVO.setHeaders(headers);
		reportVO.setRows(rows);

		return reportVO;
	}
	
	/**
	 * Retorna colunas do relatorio com valores do processo e suas tasks.
	 * 
	 * @param processVO
	 * @return List<ReportColumnVO> colunas
	 */
	private List<ReportColumnVO> getColumns(ReportCompleteQuery processVO, GridReportVO gridVO) {
		
		List<ReportColumnVO> columns = new ArrayList<ReportColumnVO>();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");		
		SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");		
		
		// Por enquanto, header unico será utilizado.
		columns.add(new ReportColumnVO("", processVO.getBusinessKey()));
		columns.add(new ReportColumnVO("", (processVO.getStartDate() != null ? format.format(processVO.getStartDate()) : "")));
		columns.add(new ReportColumnVO("", (processVO.getEndDate() != null ? format.format(processVO.getEndDate()) : "")));
		columns.add(new ReportColumnVO("", processVO.getOwner()));
		columns.add(new ReportColumnVO("", (processVO.getProgress() != null ? processVO.getProgress().toString() : "0")));
		columns.add(new ReportColumnVO("", processVO.getDiretoria()));
		columns.add(new ReportColumnVO("", processVO.getUf()));
		columns.add(new ReportColumnVO("", processVO.getCidade()));
		columns.add(new ReportColumnVO("", processVO.getTaskName()));
		columns.add(new ReportColumnVO("", processVO.getTaskGroup()));		
		columns.add(new ReportColumnVO("", (processVO.getTaskStartDate() != null ? format.format(processVO.getTaskStartDate()) : "")));
		columns.add(new ReportColumnVO("", (processVO.getTaskEndDate() != null ? format.format(processVO.getTaskEndDate()) : "")));
		columns.add(new ReportColumnVO("", (processVO.getTaskDueDate() != null ? format.format(processVO.getTaskDueDate()) : "")));
		columns.add(new ReportColumnVO("", (processVO.getAssignee() == null ? "" : processVO.getAssignee())));
		
		columns.add(new ReportColumnVO("", (processVO.getTipoTecnologia() == null ? ""      : ComboDescriptions.getTipoTecnologiaDescription(processVO.getTipoTecnologia()))));
		columns.add(new ReportColumnVO("", (processVO.getTipoInvestimento() == null ? ""    : ComboDescriptions.getTipoInvestimentoDescription(processVO.getTipoInvestimento()))));
		columns.add(new ReportColumnVO("", (processVO.getTipoObra() == null ? ""  : ComboDescriptions.getTipoObraDescription(processVO.getTipoObra()))));
		columns.add(new ReportColumnVO("", (processVO.getPossuiInteresse() == null ? ""     : ComboDescriptions.getSimNaoDescription(processVO.getPossuiInteresse()))));
		columns.add(new ReportColumnVO("", (processVO.getSugerirInclusaoArea() == null ? "" : ComboDescriptions.getSimNaoDescription(processVO.getSugerirInclusaoArea()))));
		columns.add(new ReportColumnVO("", (processVO.getMercadoCompletoB2C() == null ? ""  : ComboDescriptions.getSimNaoDescription(processVO.getMercadoCompletoB2C()))));
		columns.add(new ReportColumnVO("", (processVO.getMercadoCompletoB2B() == null ? ""  : ComboDescriptions.getSimNaoDescription(processVO.getMercadoCompletoB2B()))));
		columns.add(new ReportColumnVO("", (processVO.getLevantamentoValido() == null ? ""  : ComboDescriptions.getSimNaoDescription(processVO.getLevantamentoValido()))));		
				
		//Colunas grid de armario - Posição do armario é utilizada para troca de nome
		columns.add(new ReportColumnVO("", (gridVO.getNomeArea() == null ? "" : gridVO.getNomeArea())));
		columns.add(new ReportColumnVO(ARMARIO_HEADER, (gridVO.getArmario() == null ? "" : gridVO.getArmario())));
		
		columns.add(new ReportColumnVO("", (gridVO.getTxPenetracaoB2C() == null ? "" : gridVO.getTxPenetracaoB2C().toString())));
		columns.add(new ReportColumnVO("", (gridVO.getTxPenetracaoPredioB2C() == null ? "" : gridVO.getTxPenetracaoPredioB2C().toString())));
		columns.add(new ReportColumnVO("", (gridVO.getTxPenetracaoB2B() == null ? "" : gridVO.getTxPenetracaoB2B().toString())));
		columns.add(new ReportColumnVO("", (gridVO.getTxPenetracaoPredioB2B() == null ? "" : gridVO.getTxPenetracaoPredioB2B().toString())));
		
		try {
			
			//Efetua parse da data do banco "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" e posteriormente para relatorio "dd/MM/yyyy"
			
			columns.add(new ReportColumnVO("", (gridVO.getInicioPrevistoCM() == null ? "" : format.format(databaseFormat.parse(gridVO.getInicioPrevistoCM())))));
			columns.add(new ReportColumnVO("", (gridVO.getInicioCM() == null ? "" : format.format(databaseFormat.parse(gridVO.getInicioCM())))));
			columns.add(new ReportColumnVO("", (gridVO.getFimPrevistoCM() == null ? "" : format.format(databaseFormat.parse(gridVO.getFimPrevistoCM())))));
			columns.add(new ReportColumnVO("", (gridVO.getFimCM() == null ? "" : format.format(databaseFormat.parse(gridVO.getFimCM())))));
			
			columns.add(new ReportColumnVO("", (gridVO.getInicioPrevistoLM() == null ? "" : format.format(databaseFormat.parse(gridVO.getInicioPrevistoLM())))));
			columns.add(new ReportColumnVO("", (gridVO.getInicioLM() == null ? "" : format.format(databaseFormat.parse(gridVO.getInicioLM())))));
			columns.add(new ReportColumnVO("", (gridVO.getFimPrevistoLM() == null ? "" : format.format(databaseFormat.parse(gridVO.getFimPrevistoLM())))));
			columns.add(new ReportColumnVO("", (gridVO.getFimLM() == null ? "" : format.format(databaseFormat.parse(gridVO.getFimLM())))));
			
		} catch (Exception e) {
			logger.error("Não foi possível formatar data.", e);
			columns.add(new ReportColumnVO("", ""));
		}
		
		columns.add(new ReportColumnVO("", (processVO.getQuantidadeAreas() == null ? "" : processVO.getQuantidadeAreas())));
				
		columns.add(new ReportColumnVO("", (processVO.getRa() == null ? "" : processVO.getRa())));
		columns.add(new ReportColumnVO("", (processVO.getRb() == null ? "" : processVO.getRb())));
		columns.add(new ReportColumnVO("", (processVO.getRc() == null ? "" : processVO.getRc())));
		columns.add(new ReportColumnVO("", (processVO.getRd() == null ? "" : processVO.getRd())));
		columns.add(new ReportColumnVO("", (processVO.getDemandaB2C() == null ? "" : processVO.getDemandaB2C())));
		columns.add(new ReportColumnVO("", (processVO.getNa() == null ? "" : processVO.getNa())));
		columns.add(new ReportColumnVO("", (processVO.getNb() == null ? "" : processVO.getNb())));
		columns.add(new ReportColumnVO("", (processVO.getNc() == null ? "" : processVO.getNc())));
		columns.add(new ReportColumnVO("", (processVO.getPa() == null ? "" : processVO.getPa())));
		columns.add(new ReportColumnVO("", (processVO.getDemandaB2B() == null ? "" : processVO.getDemandaB2B())));
		columns.add(new ReportColumnVO("", (processVO.getFacilidade() == null ? "" : processVO.getFacilidade())));
	
		//Resultado financeiro, aprovacao CDG, priorizacao obras
		columns.add(new ReportColumnVO("", (processVO.getResultadoFinanceiro() == null ? "" : ComboDescriptions.getResultadoDescription(processVO.getResultadoFinanceiro().toString()))));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoResultado() == null ? "" : processVO.getObservacaoResultado().toString())));
		columns.add(new ReportColumnVO("", (processVO.getAprovacaoCDG() == null ? "" : ComboDescriptions.getAprovacaoCDGDescription(processVO.getAprovacaoCDG().toString()))));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoCDG() == null ? "" : processVO.getObservacaoCDG().toString())));
		columns.add(new ReportColumnVO("", (processVO.getRevisarPriorizacaoObra() == null ? "" : ComboDescriptions.getSimNaoDescription(processVO.getRevisarPriorizacaoObra().toString()))));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoPriorizacaoObra() == null ? "" : processVO.getObservacaoPriorizacaoObra().toString())));
		
		columns.add(new ReportColumnVO("", (processVO.getAnoPriorizacaoObra() == null ? "" : processVO.getAnoPriorizacaoObra())));
		columns.add(new ReportColumnVO("", (processVO.getQuarterPriorizacaoObra() == null ? "" : processVO.getQuarterPriorizacaoObra())));
		columns.add(new ReportColumnVO("", (processVO.getCentral() == null ? "" : processVO.getCentral())));
		
		//Datas de Obra
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoPrevETP() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoPrevETP())))));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoETP() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoETP())))));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoETP() == null ? "" : processVO.getObservacaoETP())));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoPrevBase() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoPrevBase())))));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoBase() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoBase())))));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoBase() == null ? "" : processVO.getObservacaoBase())));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoPrevProjeto() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoPrevProjeto())))));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoProjeto() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoProjeto())))));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoProjeto() == null ? "" : processVO.getObservacaoProjeto())));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoPrevLicenca() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoPrevLicenca())))));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoLicenca() < 1 ? "" : format.format(new Date(processVO.getDataConclusaoLicenca())))));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoLicenca() == null ? "" : processVO.getObservacaoLicenca())));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoPrevConstrucao() == null ? "" : processVO.getDataConclusaoPrevConstrucao())));
		columns.add(new ReportColumnVO("", (processVO.getDataConclusaoConstrucao() == null ? "" : processVO.getDataConclusaoConstrucao())));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoConstrucao() == null ? "" : processVO.getObservacaoConstrucao())));
		
		columns.add(new ReportColumnVO("", (processVO.getTotalFacilidadesEntregues() == null ? "" : processVO.getTotalFacilidadesEntregues())));
		columns.add(new ReportColumnVO("", (processVO.getObservacaoEntregaTotal() == null ? "" : processVO.getObservacaoEntregaTotal())));
		
		return columns;
	}

	/**
	 * Retorna filtro de Status
	 * 
	 * @param filterDTO
	 * @return String valor selecionado ou vazio ''. Ibatis não trata null
	 */
	private String getStatusFilter(FilterDTO filterDTO) {
		String returnValue = "";
		if (filterDTO.getConstraints() != null) {
			if (filterDTO.getConstraints().get(ApplicationConstants.FILTER_PROCESS_STATUS) != null) {
				returnValue = filterDTO.getConstraints().get(ApplicationConstants.FILTER_PROCESS_STATUS).getValue();
			}
		}
		return (returnValue != null ? returnValue : "%");
	}

	/**
	 * Retorna filtro de Estado
	 * 
	 * @param filterDTO
	 * @return String valor selecionado ou vazio ''. Ibatis não trata null
	 */
	private String getUFFilter(FilterDTO filterDTO) {
		String returnValue = "";
		if (filterDTO.getConstraints() != null) {
			if (filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_UF) != null) {
				returnValue = filterDTO.getConstraints().get(ApplicationConstants.FILTER_SELECTED_UF).getValue();
			}
		}
		return (returnValue != null ? returnValue : "%");
	}

}