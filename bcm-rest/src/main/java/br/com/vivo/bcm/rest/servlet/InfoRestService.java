package br.com.vivo.bcm.rest.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Retorna informações sobre a API.
 * 
 * @author G0029875
 */
public class InfoRestService extends HttpServlet {

	private static final Logger logger = Logger.getLogger(InfoRestService.class);
	
	private static final long serialVersionUID = 1L;

	private static final String METAINF_FILE = "META-INF/MANIFEST.MF";
	private static final String IMPLEMENTATION_VERSION = "Implementation-Version";
	private static final String IMPLEMENTATION_BUILD = "Implementation-Build";

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			httpServletResponse.getWriter().write(this.showInfo(httpServletRequest));
		} catch (IOException e) {
			logger.error("Não foi possivel obter ServletResponse", e);			
		}
	}

	private String showInfo(HttpServletRequest httpServletRequest) {
		InputStream resourceAsStream = httpServletRequest.getSession().getServletContext().getResourceAsStream(METAINF_FILE);

		if (resourceAsStream == null) {
			return "Arquivo " + METAINF_FILE + " não localizado.";
		}

		try {
			Manifest manifest = new Manifest(resourceAsStream);
			
			Attributes attributes = manifest.getMainAttributes();

			return "Versão " + attributes.getValue(IMPLEMENTATION_VERSION) + " " + attributes.getValue(IMPLEMENTATION_BUILD);
		} catch (IOException e) {
			logger.error(e);
			return "Não foi possível abrir manifest: \n" + METAINF_FILE;
		}
	}
}