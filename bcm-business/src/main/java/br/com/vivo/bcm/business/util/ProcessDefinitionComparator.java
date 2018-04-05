package br.com.vivo.bcm.business.util;

import java.util.Comparator;

import br.com.vivo.bcm.business.vo.ProcessDefinitionVO;

public class ProcessDefinitionComparator implements Comparator<ProcessDefinitionVO> {

	@Override
	public int compare(ProcessDefinitionVO arg0, ProcessDefinitionVO arg1) {
		return arg0.getLastUpdate().before(arg1.getLastUpdate()) ? 1 : -1;
	}
}