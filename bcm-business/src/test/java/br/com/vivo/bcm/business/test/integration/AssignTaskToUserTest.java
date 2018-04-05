package br.com.vivo.bcm.business.test.integration;

import java.util.List;

import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;



/**
 * @author G0054687
 *
 */
public class AssignTaskToUserTest extends BaseIntegrationTest {

	@Test
	public void testAssign() {

		// taskAbertura
		List<Task> task = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().list();
		
		Assert.assertTrue(task.size() > 0);
		
//		System.out.println(task.getOwner());
//		System.out.println(task.getAssignee());
//		processEngineConfig.getProcessEngine().getTaskService().claim(task.getId(), "TESTE");
//		
//		task = processEngineConfig.getProcessEngine().getTaskService().createTaskQuery().taskId("115").singleResult();
//
//		System.out.println(task.getOwner());
//		System.out.println(task.getAssignee());		
	}
}
