package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.configuration.bean.ProcessEngineConfiguration;
import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.ReportColumnVO;
import br.com.vivo.bcm.business.vo.ReportRowVO;
import br.com.vivo.bcm.business.vo.ReportVO;

@Named("downloadReportBusinessOperation")
public class DownloadReportBusinessOperation extends BaseBusinessOperation implements IBusinessOperation<FilterDTO, String> {

	private static final Logger logger = Logger.getLogger(DownloadReportBusinessOperation.class);

	@Inject
	@Named("processEngineConfiguration")
	private ProcessEngineConfiguration processEngineConfiguration;

	@Inject
	@Named("reportCompleteBusinessOperation")
	private IBusinessOperation<FilterDTO, ReportVO> generateReportBusinessOperation;

	@Override
	public String execute(FilterDTO filterDTO) throws BusinessException {

		logger.debug("execute do DownloadReportBusinessOperation - begin - " + filterDTO);

		StringBuffer stringBuffer = new StringBuffer();

		ReportVO reportVO = this.generateReportBusinessOperation.execute(filterDTO);

		StringBuilder strbRows = new StringBuilder();
		StringBuilder strbHeader = new StringBuilder();

		for (String header : reportVO.getHeaders()) {
			strbHeader.append(header + ";");
		}

		strbHeader.delete(strbHeader.length() - 1, strbHeader.length());
		strbHeader.append("\n");

		for (ReportRowVO entry : reportVO.getRows()) {
			for (ReportColumnVO column : entry.getProcessInstanceColumns()) {
				strbRows.append(column.getColumnValue() + ";");
			}
			strbRows.delete(strbRows.length() - 1, strbRows.length());
			strbRows.append("\n");
		}
		stringBuffer.append(strbHeader.toString());
		stringBuffer.append(strbRows.toString());
		stringBuffer.append("\n");

		logger.debug("execute do DownloadReportBusinessOperation - end");
		return stringBuffer.toString();
	}
}
