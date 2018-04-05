package br.com.vivo.bcm.business.util;

import java.util.Comparator;

import br.com.vivo.bcm.business.vo.ProcessInstanceVO;

public class ProcessInstanceComparator implements Comparator<ProcessInstanceVO> {

	@Override
	public int compare(ProcessInstanceVO arg0, ProcessInstanceVO arg1) {
		return arg0.getEndDate().compareTo(arg1.getEndDate());
	}

}
