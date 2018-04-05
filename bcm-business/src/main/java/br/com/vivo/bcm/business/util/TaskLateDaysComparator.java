/**
 * 
 */
package br.com.vivo.bcm.business.util;

import java.util.Comparator;

import br.com.vivo.bcm.business.vo.VivoTask;

/**
 * @author A0051460
 *
 */
public class TaskLateDaysComparator implements Comparator<VivoTask> {

	@Override
	public int compare(VivoTask arg0, VivoTask arg1) {
//		if (arg0 != null && arg1 != null) {
//			return new Long(arg1.getLateDays()).compareTo(new Long(arg0.getLateDays()));
//		}
		return 0;
	}
}