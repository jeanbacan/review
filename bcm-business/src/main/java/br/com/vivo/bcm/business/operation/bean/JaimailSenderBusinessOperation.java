package br.com.vivo.bcm.business.operation.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.gvt.jaimail.client.JaimailClientFactory;
import br.com.gvt.jaimail.client.exception.JaimailServerException;
import br.com.gvt.jaimail.client.exception.JaimailValidationException;
import br.com.gvt.jaimail.client.service.MensagemService;
import br.com.vivo.bcm.business.dto.SenderObject;
import br.com.vivo.bcm.business.enums.ConfigurationType;
import br.com.vivo.bcm.business.exception.BusinessException;
import br.com.vivo.bcm.business.exception.ErrorSendEmailException;
import br.com.vivo.bcm.business.operation.IJaimailSenderBusinessOperation;
import br.com.vivo.configurationutils.IConfiguration;

@Named("jaimailSender")
public class JaimailSenderBusinessOperation implements IJaimailSenderBusinessOperation {

	private static final Logger logger = Logger.getLogger(JaimailSenderBusinessOperation.class);

	@Inject
	@Named("systemConfiguration")
	private IConfiguration systemConfiguration;

	@Override
	public String execute(SenderObject senderObject) throws BusinessException {

		String jaimailURL = this.systemConfiguration.getProperty(ConfigurationType.JAIMAIL_URL.getKey());
		String jaimailAccessCode = this.systemConfiguration.getProperty(ConfigurationType.JAIMAIL_ACCESS_KEY.getKey());

		MensagemService service = JaimailClientFactory.getMensagemService(jaimailURL, jaimailAccessCode);

		logger.debug("JaiMail Params:" + senderObject.getParams().toString());

		String ticket = null;

		try {
			ticket = service.sendMessage(senderObject.getTemplateCode(), senderObject.getParams());
			logger.debug("JaiMail Ticket:" + ticket);
		} catch (JaimailServerException e) {
			logger.error("Falha no servidor de email JaiMail", e);
			throw new ErrorSendEmailException();
		} catch (JaimailValidationException e) {
			logger.error("Falha ao validar dados da mensagem no JaiMail", e);
			throw new ErrorSendEmailException();
		}

		return ticket;
	}
}