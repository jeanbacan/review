package br.com.vivo.bcm.activiti.execution;

import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;

import br.com.vivo.bcm.activiti.mapper.NewTaskMailMapper;
import br.com.vivo.bcm.activiti.query.NewTaskMailQuery;

/**
 * Polimorfismo para CustomSqlExecution
 * @author Jean Bacan
 *
 */
public class NewTaskMailSQLExecution extends AbstractCustomSqlExecution<NewTaskMailMapper, NewTaskMailQuery> {

	private String processInstanceId;

	public NewTaskMailSQLExecution(String processInstanceId, Class<NewTaskMailMapper> mapperClass) {
		super(mapperClass);
		this.processInstanceId = processInstanceId;
	}

	@Override
	public NewTaskMailQuery execute(NewTaskMailMapper customMapper) {
		return customMapper.getInstance(processInstanceId);		
	}

}
