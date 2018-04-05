package br.com.vivo.bcm.rest.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Listener responsavel pelas configuracoes do Log4j.
 * 
 * @author G0029875
 * 
 */
@WebListener
public class Log4jListener implements ServletContextListener {

	private static final String LOG4J_PATH_PARAM = "/home/app/bcm/config/log4j.xml";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			System.out.println("Configurando log4j..");

			DOMConfigurator.configure(LOG4J_PATH_PARAM);

			System.out.println("Log4j configurado..");

		} catch (Exception e) {
			throw new IllegalStateException("Erro ao configurar log4j", e);
		}
	}

	public static void reload() {
		Logger logger = Logger.getLogger(Log4jListener.class);

		logger.info("Reloading das configurações do log4j..");
		new DOMConfigurator().doConfigure(LOG4J_PATH_PARAM, LogManager.getLoggerRepository());
		logger.info("Configurações atualizadas com sucesso.");
	}
}
