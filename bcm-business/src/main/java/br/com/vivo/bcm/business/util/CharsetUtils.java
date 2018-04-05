package br.com.vivo.bcm.business.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.log4j.Logger;

public class CharsetUtils {

	private static final Logger logger = Logger.getLogger(CharsetUtils.class);
	
	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	/**
	 * Encoda uma string no padrão (charset) informado
	 * 
	 * @param string Texto que será encodado
	 * @param charsetEncode charset utilizado no encode
	 * @return
	 */
	public static String encodeTo(String string, String charsetEncode) {
		if (string == null) {
			return null;
		}

		Charset utf8Charset = Charset.forName(charsetEncode);
		Charset defaultCharset = Charset.defaultCharset();

		ByteBuffer inputBuffer = ByteBuffer.wrap(string.getBytes());

		// decode default
		CharBuffer data = defaultCharset.decode(inputBuffer);

		// encode UTF-8
		ByteBuffer outputBuffer = utf8Charset.encode(data);
		byte[] outputData = outputBuffer.array();

		return new String(outputData, utf8Charset);
	}
	
	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	public static byte[] encodeUTF8(String string) {
		return string.getBytes(UTF8_CHARSET);
	}
	
	public static boolean isValidUTF8( byte[] input ) {

	    CharsetDecoder cs = Charset.forName("UTF-8").newDecoder();

	    try {
	        cs.decode(ByteBuffer.wrap(input));
	        return true;
	    }
	    catch(CharacterCodingException e){
	    	logger.error("Erro ao decodificar", e);
	        return false;
	    }       
	}

	public static boolean isUTF8MisInterpreted(String input) {
		// convenience overload for the most common UTF-8 misinterpretation
		// which is also the case in your question
		return isUTF8MisInterpreted(input, "Windows-1252");
	}

	public static boolean isUTF8MisInterpreted(String input, String encoding) {

		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
		CharsetEncoder encoder = Charset.forName(encoding).newEncoder();
		ByteBuffer tmp;
		try {
			tmp = encoder.encode(CharBuffer.wrap(input));
		}

		catch (CharacterCodingException e) {
			logger.error("Erro ao fazer encode", e);
			return false;
		}

		try {
			decoder.decode(tmp);
			return true;
		} catch (CharacterCodingException e) {
			logger.error("Erro ao fazer decode", e);
			return false;
		}
	}
}