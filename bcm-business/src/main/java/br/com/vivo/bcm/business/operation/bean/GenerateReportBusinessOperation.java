package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.vivo.bcm.business.dto.filter.FilterDTO;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.operation.IBusinessOperation;
import br.com.vivo.bcm.business.vo.ReportVO;

/**
 * BO que gera os relatórios
 * 
 * @author Jean Bacan?
 */
@Named("generateReportBusinessOperation")
public class GenerateReportBusinessOperation implements IBusinessOperation<FilterDTO, ReportVO> {

	private static final Logger logger = Logger.getLogger(GenerateReportBusinessOperation.class);

	@Override
	public ReportVO execute(FilterDTO generateReportVO) throws BusinessException {
		logger.debug("execute do GenerateReportBusinessOperation - inicio");

		ReportVO reportVO = new ReportVO();

		return reportVO;
	}

}
