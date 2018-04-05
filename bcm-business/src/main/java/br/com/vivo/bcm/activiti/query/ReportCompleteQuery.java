package br.com.vivo.bcm.activiti.query;

import java.util.Date;

import br.com.vivo.bcm.activiti.mapper.ReportCompleteMapper;

/**
 * Bean mapeado e utilizado pelas consultas em {@link ReportCompleteMapper}
 * 
 * @author Jean Bacan
 * @since 10/07/2017
 */
public class ReportCompleteQuery {

	private String processInstanceId;
	private Date endDate;
	private String owner;
	private Date startDate;
	private Integer progress;
	private String businessKey;
	private String taskName;
	private String taskGroup;
	private Date taskDueDate;
	private Date taskEndDate;
	private Date taskStartDate;
	private String assignee;
	private String uf;
	private String cidade;
	private String gridArquivo;
	private String gridArmario;
	private String diretoria;
	private String tipoTecnologia;
	private String tipoInvestimento;
	private String tipoObra;
	private String possuiInteresse;
	private String sugerirInclusaoArea;
	private String mercadoCompletoB2C;
	private String mercadoCompletoB2B;

	private String quantidadeAreas;
	
	private String ra;
	private String rb;
	private String rc;
	private String rd;
	private String demandaB2C;
	private String na;
	private String nb;
	private String nc;
	private String pa;
	private String demandaB2B;
	private String facilidade;
	
	private String levantamentoValido;
	private String aprovacaoCDG;
	private String observacaoCDG;
	private String revisarPriorizacaoObra;
	private String observacaoPriorizacaoObra;
	private String resultadoFinanceiro;
	private String observacaoResultado;

	private String anoPriorizacaoObra;
	private String quarterPriorizacaoObra;
	private String central;
	
	// Campos Obra - Salvos em Long
	private long dataConclusaoPrevETP;
	private long dataConclusaoETP;
	private String observacaoETP;
	private long dataConclusaoPrevBase;
	private long dataConclusaoBase;
	private String observacaoBase;
	private long dataConclusaoPrevProjeto;
	private long dataConclusaoProjeto;
	private String observacaoProjeto;
	private long dataConclusaoPrevLicenca;
	private long dataConclusaoLicenca;
	private String observacaoLicenca;
	private String dataConclusaoPrevConstrucao;
	private String dataConclusaoConstrucao;
	private String observacaoConstrucao;

	private String totalFacilidadesEntregues;
	private String observacaoEntregaTotal;
  	
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the businessKey
	 */
	public String getBusinessKey() {
		return businessKey;
	}

	/**
	 * @param businessKey the businessKey to set
	 */
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the taskGroup
	 */
	public String getTaskGroup() {
		return taskGroup;
	}

	/**
	 * @param taskGroup the taskGroup to set
	 */
	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	/**
	 * @return the progress
	 */
	public Integer getProgress() {
		return progress;
	}

	/**
	 * @param progress the progress to set
	 */
	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the diretoria
	 */
	public String getDiretoria() {
		return diretoria;
	}

	/**
	 * @param diretoria the diretoria to set
	 */
	public void setDiretoria(String diretoria) {
		this.diretoria = diretoria;
	}

	/**
	 * @return the taskDueDate
	 */
	public Date getTaskDueDate() {
		return taskDueDate;
	}

	/**
	 * @param taskDueDate the taskDueDate to set
	 */
	public void setTaskDueDate(Date taskDueDate) {
		this.taskDueDate = taskDueDate;
	}

	/**
	 * @return the taskEndDate
	 */
	public Date getTaskEndDate() {
		return taskEndDate;
	}

	/**
	 * @param taskEndDate the taskEndDate to set
	 */
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	/**
	 * @return the taskStartDate
	 */
	public Date getTaskStartDate() {
		return taskStartDate;
	}

	/**
	 * @param taskStartDate the taskStartDate to set
	 */
	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the gridArquivo
	 */
	public String getGridArquivo() {
		return gridArquivo;
	}

	/**
	 * @param gridArquivo the gridArquivo to set
	 */
	public void setGridArquivo(String gridArquivo) {
		this.gridArquivo = gridArquivo;
	}

	/**
	 * @return the gridArmario
	 */
	public String getGridArmario() {
		return gridArmario;
	}

	/**
	 * @param gridArmario the gridArmario to set
	 */
	public void setGridArmario(String gridArmario) {
		this.gridArmario = gridArmario;
	}

	/**
	 * @return the tipoTecnologia
	 */
	public String getTipoTecnologia() {
		return tipoTecnologia;
	}

	/**
	 * @param tipoTecnologia the tipoTecnologia to set
	 */
	public void setTipoTecnologia(String tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}

	/**
	 * @return the tipoInvestimento
	 */
	public String getTipoInvestimento() {
		return tipoInvestimento;
	}

	/**
	 * @param tipoInvestimento the tipoInvestimento to set
	 */
	public void setTipoInvestimento(String tipoInvestimento) {
		this.tipoInvestimento = tipoInvestimento;
	}

	/**
	 * @return the possuiInteresse
	 */
	public String getPossuiInteresse() {
		return possuiInteresse;
	}

	/**
	 * @param possuiInteresse the possuiInteresse to set
	 */
	public void setPossuiInteresse(String possuiInteresse) {
		this.possuiInteresse = possuiInteresse;
	}

	/**
	 * @return the sugerirInclusaoArea
	 */
	public String getSugerirInclusaoArea() {
		return sugerirInclusaoArea;
	}

	/**
	 * @param sugerirInclusaoArea the sugerirInclusaoArea to set
	 */
	public void setSugerirInclusaoArea(String sugerirInclusaoArea) {
		this.sugerirInclusaoArea = sugerirInclusaoArea;
	}

	/**
	 * @return the mercadoCompletoB2C
	 */
	public String getMercadoCompletoB2C() {
		return mercadoCompletoB2C;
	}

	/**
	 * @param mercadoCompletoB2C the mercadoCompletoB2C to set
	 */
	public void setMercadoCompletoB2C(String mercadoCompletoB2C) {
		this.mercadoCompletoB2C = mercadoCompletoB2C;
	}

	/**
	 * @return the mercadoCompletoB2B
	 */
	public String getMercadoCompletoB2B() {
		return mercadoCompletoB2B;
	}

	/**
	 * @param mercadoCompletoB2B the mercadoCompletoB2B to set
	 */
	public void setMercadoCompletoB2B(String mercadoCompletoB2B) {
		this.mercadoCompletoB2B = mercadoCompletoB2B;
	}

	/**
	 * @return the levantamentoValido
	 */
	public String getLevantamentoValido() {
		return levantamentoValido;
	}

	/**
	 * @param levantamentoValido the levantamentoValido to set
	 */
	public void setLevantamentoValido(String levantamentoValido) {
		this.levantamentoValido = levantamentoValido;
	}

	/**
	 * @return the dataConclusaoPrevETP
	 */
	public long getDataConclusaoPrevETP() {
		return dataConclusaoPrevETP;
	}

	/**
	 * @param dataConclusaoPrevETP the dataConclusaoPrevETP to set
	 */
	public void setDataConclusaoPrevETP(long dataConclusaoPrevETP) {
		this.dataConclusaoPrevETP = dataConclusaoPrevETP;
	}

	/**
	 * @return the dataConclusaoETP
	 */
	public long getDataConclusaoETP() {
		return dataConclusaoETP;
	}

	/**
	 * @param dataConclusaoETP the dataConclusaoETP to set
	 */
	public void setDataConclusaoETP(long dataConclusaoETP) {
		this.dataConclusaoETP = dataConclusaoETP;
	}

	/**
	 * @return the dataConclusaoPrevBase
	 */
	public long getDataConclusaoPrevBase() {
		return dataConclusaoPrevBase;
	}

	/**
	 * @param dataConclusaoPrevBase the dataConclusaoPrevBase to set
	 */
	public void setDataConclusaoPrevBase(long dataConclusaoPrevBase) {
		this.dataConclusaoPrevBase = dataConclusaoPrevBase;
	}

	/**
	 * @return the dataConclusaoBase
	 */
	public long getDataConclusaoBase() {
		return dataConclusaoBase;
	}

	/**
	 * @param dataConclusaoBase the dataConclusaoBase to set
	 */
	public void setDataConclusaoBase(long dataConclusaoBase) {
		this.dataConclusaoBase = dataConclusaoBase;
	}

	/**
	 * @return the dataConclusaoPrevProjeto
	 */
	public long getDataConclusaoPrevProjeto() {
		return dataConclusaoPrevProjeto;
	}

	/**
	 * @param dataConclusaoPrevProjeto the dataConclusaoPrevProjeto to set
	 */
	public void setDataConclusaoPrevProjeto(long dataConclusaoPrevProjeto) {
		this.dataConclusaoPrevProjeto = dataConclusaoPrevProjeto;
	}

	/**
	 * @return the dataConclusaoProjeto
	 */
	public long getDataConclusaoProjeto() {
		return dataConclusaoProjeto;
	}

	/**
	 * @param dataConclusaoProjeto the dataConclusaoProjeto to set
	 */
	public void setDataConclusaoProjeto(long dataConclusaoProjeto) {
		this.dataConclusaoProjeto = dataConclusaoProjeto;
	}

	/**
	 * @return the dataConclusaoPrevLicenca
	 */
	public long getDataConclusaoPrevLicenca() {
		return dataConclusaoPrevLicenca;
	}

	/**
	 * @param dataConclusaoPrevLicenca the dataConclusaoPrevLicenca to set
	 */
	public void setDataConclusaoPrevLicenca(long dataConclusaoPrevLicenca) {
		this.dataConclusaoPrevLicenca = dataConclusaoPrevLicenca;
	}

	/**
	 * @return the dataConclusaoLicenca
	 */
	public long getDataConclusaoLicenca() {
		return dataConclusaoLicenca;
	}

	/**
	 * @param dataConclusaoLicenca the dataConclusaoLicenca to set
	 */
	public void setDataConclusaoLicenca(long dataConclusaoLicenca) {
		this.dataConclusaoLicenca = dataConclusaoLicenca;
	}


	/**
	 * @return the resultadoFinanceiro
	 */
	public String getResultadoFinanceiro() {
		return resultadoFinanceiro;
	}

	/**
	 * @param resultadoFinanceiro the resultadoFinanceiro to set
	 */
	public void setResultadoFinanceiro(String resultadoFinanceiro) {
		this.resultadoFinanceiro = resultadoFinanceiro;
	}

	/**
	 * @return the observacaoResultado
	 */
	public String getObservacaoResultado() {
		return observacaoResultado;
	}

	/**
	 * @param observacaoResultado the observacaoResultado to set
	 */
	public void setObservacaoResultado(String observacaoResultado) {
		this.observacaoResultado = observacaoResultado;
	}

	/**
	 * @return the tipoObra
	 */
	public String getTipoObra() {
		return tipoObra;
	}

	/**
	 * @param tipoObra the tipoObra to set
	 */
	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	/**
	 * @return the aprovacaoCDG
	 */
	public String getAprovacaoCDG() {
		return aprovacaoCDG;
	}

	/**
	 * @param aprovacaoCDG the aprovacaoCDG to set
	 */
	public void setAprovacaoCDG(String aprovacaoCDG) {
		this.aprovacaoCDG = aprovacaoCDG;
	}

	/**
	 * @return the observacaoCDG
	 */
	public String getObservacaoCDG() {
		return observacaoCDG;
	}

	/**
	 * @param observacaoCDG the observacaoCDG to set
	 */
	public void setObservacaoCDG(String observacaoCDG) {
		this.observacaoCDG = observacaoCDG;
	}

	/**
	 * @return the revisarPriorizacaoObra
	 */
	public String getRevisarPriorizacaoObra() {
		return revisarPriorizacaoObra;
	}

	/**
	 * @param revisarPriorizacaoObra the revisarPriorizacaoObra to set
	 */
	public void setRevisarPriorizacaoObra(String revisarPriorizacaoObra) {
		this.revisarPriorizacaoObra = revisarPriorizacaoObra;
	}

	/**
	 * @return the observacaoPriorizacaoObra
	 */
	public String getObservacaoPriorizacaoObra() {
		return observacaoPriorizacaoObra;
	}

	/**
	 * @param observacaoPriorizacaoObra the observacaoPriorizacaoObra to set
	 */
	public void setObservacaoPriorizacaoObra(String observacaoPriorizacaoObra) {
		this.observacaoPriorizacaoObra = observacaoPriorizacaoObra;
	}

	
	/**
	 * @return the ra
	 */
	public String getRa() {
		return ra;
	}

	
	/**
	 * @param ra the ra to set
	 */
	public void setRa(String ra) {
		this.ra = ra;
	}

	
	/**
	 * @return the rb
	 */
	public String getRb() {
		return rb;
	}

	
	/**
	 * @param rb the rb to set
	 */
	public void setRb(String rb) {
		this.rb = rb;
	}

	
	/**
	 * @return the rc
	 */
	public String getRc() {
		return rc;
	}

	
	/**
	 * @param rc the rc to set
	 */
	public void setRc(String rc) {
		this.rc = rc;
	}

	
	/**
	 * @return the rd
	 */
	public String getRd() {
		return rd;
	}

	
	/**
	 * @param rd the rd to set
	 */
	public void setRd(String rd) {
		this.rd = rd;
	}

	
	/**
	 * @return the demandaB2C
	 */
	public String getDemandaB2C() {
		return demandaB2C;
	}

	
	/**
	 * @param demandaB2C the demandaB2C to set
	 */
	public void setDemandaB2C(String demandaB2C) {
		this.demandaB2C = demandaB2C;
	}

	
	/**
	 * @return the na
	 */
	public String getNa() {
		return na;
	}

	
	/**
	 * @param na the na to set
	 */
	public void setNa(String na) {
		this.na = na;
	}

	
	/**
	 * @return the nb
	 */
	public String getNb() {
		return nb;
	}

	
	/**
	 * @param nb the nb to set
	 */
	public void setNb(String nb) {
		this.nb = nb;
	}

	
	/**
	 * @return the nc
	 */
	public String getNc() {
		return nc;
	}

	
	/**
	 * @param nc the nc to set
	 */
	public void setNc(String nc) {
		this.nc = nc;
	}

	
	/**
	 * @return the pa
	 */
	public String getPa() {
		return pa;
	}

	
	/**
	 * @param pa the pa to set
	 */
	public void setPa(String pa) {
		this.pa = pa;
	}

	
	/**
	 * @return the demandaB2B
	 */
	public String getDemandaB2B() {
		return demandaB2B;
	}

	
	/**
	 * @param demandaB2B the demandaB2B to set
	 */
	public void setDemandaB2B(String demandaB2B) {
		this.demandaB2B = demandaB2B;
	}

	
	/**
	 * @return the facilidade
	 */
	public String getFacilidade() {
		return facilidade;
	}

	
	/**
	 * @param facilidade the facilidade to set
	 */
	public void setFacilidade(String facilidade) {
		this.facilidade = facilidade;
	}

	
	/**
	 * @return the anoPriorizacaoObra
	 */
	public String getAnoPriorizacaoObra() {
		return anoPriorizacaoObra;
	}

	
	/**
	 * @param anoPriorizacaoObra the anoPriorizacaoObra to set
	 */
	public void setAnoPriorizacaoObra(String anoPriorizacaoObra) {
		this.anoPriorizacaoObra = anoPriorizacaoObra;
	}

	
	/**
	 * @return the quarterPriorizacaoObra
	 */
	public String getQuarterPriorizacaoObra() {
		return quarterPriorizacaoObra;
	}

	
	/**
	 * @param quarterPriorizacaoObra the quarterPriorizacaoObra to set
	 */
	public void setQuarterPriorizacaoObra(String quarterPriorizacaoObra) {
		this.quarterPriorizacaoObra = quarterPriorizacaoObra;
	}

	
	/**
	 * @return the central
	 */
	public String getCentral() {
		return central;
	}

	
	/**
	 * @param central the central to set
	 */
	public void setCentral(String central) {
		this.central = central;
	}

	
	/**
	 * @return the observacaoETP
	 */
	public String getObservacaoETP() {
		return observacaoETP;
	}

	
	/**
	 * @param observacaoETP the observacaoETP to set
	 */
	public void setObservacaoETP(String observacaoETP) {
		this.observacaoETP = observacaoETP;
	}

	
	/**
	 * @return the observacaoBase
	 */
	public String getObservacaoBase() {
		return observacaoBase;
	}

	
	/**
	 * @param observacaoBase the observacaoBase to set
	 */
	public void setObservacaoBase(String observacaoBase) {
		this.observacaoBase = observacaoBase;
	}

	
	/**
	 * @return the observacaoProjeto
	 */
	public String getObservacaoProjeto() {
		return observacaoProjeto;
	}

	
	/**
	 * @param observacaoProjeto the observacaoProjeto to set
	 */
	public void setObservacaoProjeto(String observacaoProjeto) {
		this.observacaoProjeto = observacaoProjeto;
	}

	
	/**
	 * @return the observacaoLicenca
	 */
	public String getObservacaoLicenca() {
		return observacaoLicenca;
	}

	
	/**
	 * @param observacaoLicenca the observacaoLicenca to set
	 */
	public void setObservacaoLicenca(String observacaoLicenca) {
		this.observacaoLicenca = observacaoLicenca;
	}

	
	/**
	 * @return the dataConclusaoPrevConstrucao
	 */
	public String getDataConclusaoPrevConstrucao() {
		return dataConclusaoPrevConstrucao;
	}

	
	/**
	 * @param dataConclusaoPrevConstrucao the dataConclusaoPrevConstrucao to set
	 */
	public void setDataConclusaoPrevConstrucao(String dataConclusaoPrevConstrucao) {
		this.dataConclusaoPrevConstrucao = dataConclusaoPrevConstrucao;
	}

	
	/**
	 * @return the dataConclusaoConstrucao
	 */
	public String getDataConclusaoConstrucao() {
		return dataConclusaoConstrucao;
	}

	
	/**
	 * @param dataConclusaoConstrucao the dataConclusaoConstrucao to set
	 */
	public void setDataConclusaoConstrucao(String dataConclusaoConstrucao) {
		this.dataConclusaoConstrucao = dataConclusaoConstrucao;
	}

	
	/**
	 * @return the observacaoConstrucao
	 */
	public String getObservacaoConstrucao() {
		return observacaoConstrucao;
	}

	
	/**
	 * @param observacaoConstrucao the observacaoConstrucao to set
	 */
	public void setObservacaoConstrucao(String observacaoConstrucao) {
		this.observacaoConstrucao = observacaoConstrucao;
	}

	
	/**
	 * @return the totalFacilidadesEntregues
	 */
	public String getTotalFacilidadesEntregues() {
		return totalFacilidadesEntregues;
	}

	
	/**
	 * @param totalFacilidadesEntregues the totalFacilidadesEntregues to set
	 */
	public void setTotalFacilidadesEntregues(String totalFacilidadesEntregues) {
		this.totalFacilidadesEntregues = totalFacilidadesEntregues;
	}

	
	/**
	 * @return the observacaoEntregaTotal
	 */
	public String getObservacaoEntregaTotal() {
		return observacaoEntregaTotal;
	}

	
	/**
	 * @param observacaoEntregaTotal the observacaoEntregaTotal to set
	 */
	public void setObservacaoEntregaTotal(String observacaoEntregaTotal) {
		this.observacaoEntregaTotal = observacaoEntregaTotal;
	}

	
	/**
	 * @return the quantidadeAreas
	 */
	public String getQuantidadeAreas() {
		return quantidadeAreas;
	}

	
	/**
	 * @param quantidadeAreas the quantidadeAreas to set
	 */
	public void setQuantidadeAreas(String quantidadeAreas) {
		this.quantidadeAreas = quantidadeAreas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessKey == null) ? 0 : businessKey.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportCompleteQuery other = (ReportCompleteQuery) obj;
		if (businessKey == null) {
			if (other.businessKey != null)
				return false;
		} else if (!businessKey.equals(other.businessKey))
			return false;
		return true;
	}

}
