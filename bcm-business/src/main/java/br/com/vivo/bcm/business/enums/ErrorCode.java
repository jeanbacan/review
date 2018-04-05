package br.com.vivo.bcm.business.enums;

/**
 * Códigos para identificar os erros lançados por {@link Exception}. Os códigos
 * devem ser equivalentes àqueles cadastrados na tabela ERROR_DESCRIPTION - na
 * base Oracle.
 * 
 * @author Clebersander Rech, Jefferson
 */
public enum ErrorCode {

	USER_NOT_FOUND("1001", "Usuário não encontrado", "Verifique seu email de acesso"), 
	ROLE_NOT_INFORMED("1002", "Role não informada ou não encontrada", "Ao menos uma role válida deve ser informada para o usuário"), 
	EMPTY_FIELDS("1003", "Campos obrigatórios não informados", "Preencha todos os campos obrigatórios"), 
	USER_INACTIVE("1011", "O usuário não está ativo", "Utilize um usuário ativo para efetuar o login"),
	INVALID_TOKEN("1020", "Token Inválido", "Seu token de autenticação expirou"),
	GROUP_NOT_FOUND("1022", "Grupo não selecionado", "Selecione um grupo de atendimento"),
	GROUP_NOT_INFORMED("1024", "Grupo não informado ou não encontrado", "Ao menos um grupo váido deve ser informado para o usuário"),
	INVALID_OR_EXPIRED_TOKEN("1025", "Token para troca de senha inválido", "Seu token para troca de senha é inválido ou expirou"),
	ERROR_SEND_EMAIL("1027", "Erro no envio de e-mail", "Erro no envio de e-mail, favor tente novamente"),
	EMPTY_TMA("1028", "O TMA é obrigatório para grupo CRM", "Favor definir um tempo em minutos"),
	NOT_ALLOWED("1029", "Operação não permitida.", "Operação não permitida."),
	ASSIGN_NOT_ALLOWED("1034", "Encaminhamento de tarefa não permitido.", "O usuário não tem permissão para encaminhar esta tarefa."),
	ACTIVITI_OPERATION_NOT_ALLOWED("1035", "Operação não permitida.", "Verifique os dados e tente novamente."),
	MISSING_TYPES_GROUPS("1039","Selecione os grupos","É necessário pelo menos 1 grupo do tipo SEGURANÇA  e 1 do tipo NEGÓCIO "),
	PROCESS_DEFINITION_EMPTY("1040","Processo não encontrado","Verifique se existe um processo configurado para iniciar."),
	USER_NOT_FOUND_IN_GROUP("1042","Usuário não encontrado","Usuário não pertence ao grupo da Tarefa"),
	INSTANCE_DETAIL_ERROR("1100","Erro ao abrir instância","Erro ao abrir detalhes do BC."),
	GENERIC("1500", "Ocorreu um erro interno", "Ocorreu um erro ao acessar o serviço. Informe a equipe de suporte."),
	EXTRACT_DATA_ERROR("1600", "Não foi possível importar documento.", "Não foi possível importar documento."),
	STATEMENT_GROUP_REQUIRED("1700", "Erro em filtros obrigatórios para consulta.", "Não existem os filtros obrigatórios para construção de statement."),
	ERROR_CONVERT_GRID("1800", "Erro ao converter tabela.", "Não foi possível converter grid para json."),
	MISSING_INFORMATION("1900", "Erro ao realizar operação", "Informações incompletas para realizar operação"),
	NO_FILES("2000", "Não existem anexos a serem baixados.", "Não existem anexos a serem baixados.");

	/** Código do erro */
	private String code;

	/** Descrição do erro (técnico). */
	private String description;

	/** Comentários */
	private String comments;

	private ErrorCode(String code, String description, String comments) {
		this.code = code;
		this.description = description;
		this.comments = comments;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

	public String getComments() {
		return comments;
	}
}