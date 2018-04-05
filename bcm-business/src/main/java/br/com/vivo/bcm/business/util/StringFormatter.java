package br.com.vivo.bcm.business.util;

import java.text.MessageFormat;

/**
 * @author P9909123
 *
 */
public class StringFormatter {

	/**
	 * Adiciona o texto nas {} correspondentes
	 * 
	 * @param args
	 * @param text
	 * @return
	 */
	public static String format(String[] args, String text) {
		MessageFormat fmt = new MessageFormat(text);
		return fmt.format(args);
	}
	
	public static String extractVarName(String variable){
		return variable.replace("}", "").replace("$", "").replace("{", "");
	}
	
}