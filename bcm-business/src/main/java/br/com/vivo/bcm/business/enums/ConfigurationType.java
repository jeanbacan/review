package br.com.vivo.bcm.business.enums;

/**
 * @author P9923900
 *
 */
public enum ConfigurationType {
	
	JAIMAIL_URL("jaimail.urlService"),
	APPLICATION_KEY("application.key"),
	
	LATE_TASK_EMAIL("email.lateTask"),
	LATE_TASK_SUBJECT ("assunto.lateTask"),
	JAIMAIL_TEMPLATE_LATE_TASK("jaimail.templateCode.late.task"),
	
	NEW_TASK_EMAIL("email.newTask"),
	NEW_TASK_SUBJECT ("assunto.newTask"),
	NEW_TASK_TEMPLATE("jaimail.templateCode.newTask"),
	
	JAIMAIL_ACCESS_KEY("jaimail.accessKey"),
	DM_APPLICATION_KEY("dm.application.key"),
	DOCUMENT_MANAGER_DOWNLOAD_URI("document.manager.download"),
	DOCUMENT_MANAGER_TOKEN_URI("document.manager.token"),
	BPMN_IMPORT_TEMP_FILEPATH("bpmn.import.tempFilePath"),
	BPMN_FORM_STARTER_KEY("bpmn.form.starter.key"),
	COLUMNS_HEADERS_ARMARIO("armario.columns.header"),
	ARMARIO_FILE_PATH("armario.file.path"),
	NEW_NAME_FILE("new.name.file"),
	ACTIVITI_FILES_VARNAMES("activiti.files.varnames");

	private String key;

	private ConfigurationType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}