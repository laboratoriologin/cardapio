<<<<<<< HEAD:CardapioWS/src/br/com/login/cardapio/beachstop/ws/util/CryptoUtil.java
/**
 * 
 */
package br.com.login.cardapio.beachstop.ws.util;

import java.security.MessageDigest;

/**
 * @author Ricardo
 *
 */
public class CryptoUtil {
	/**
	 * Method gerarHash
	 * 
	 * @author Henrique Machado
	 * @param plainText
	 * @param algorithm
	 *            The algorithm to use like MD2, MD5, SHA-1, etc.
	 * @return String
	 * @throws Exception 
	 * @throws NoSuchAlgorithmException
	 */
	public static String gerarHash(String plainText, String algorithm) throws Exception {
	
		MessageDigest mdAlgorithm;

		StringBuffer hexString = new StringBuffer();

		try {

			mdAlgorithm = MessageDigest.getInstance(algorithm);

			mdAlgorithm.update(plainText.getBytes());

			byte[] digest = mdAlgorithm.digest();

			for (int i = 0; i < digest.length; i++) {

				plainText = Integer.toHexString(0xFF & digest[i]);

				if (plainText.length() < 2) {

					plainText = "0" + plainText;
				}

				hexString.append(plainText);
			}

		} catch (Exception e) {

			throw new Exception(e);
		}

		return hexString.toString();
	}
}
=======
/**
 * 
 */
package br.com.login.cardapio.ws.util;

import java.security.MessageDigest;

/**
 * @author Ricardo
 *
 */
public class CryptoUtil {
	/**
	 * Method gerarHash
	 * 
	 * @author Henrique Machado
	 * @param plainText
	 * @param algorithm
	 *            The algorithm to use like MD2, MD5, SHA-1, etc.
	 * @return String
	 * @throws Exception 
	 * @throws NoSuchAlgorithmException
	 */
	public static String gerarHash(String plainText, String algorithm) throws Exception {
	
		MessageDigest mdAlgorithm;

		StringBuffer hexString = new StringBuffer();

		try {

			mdAlgorithm = MessageDigest.getInstance(algorithm);

			mdAlgorithm.update(plainText.getBytes());

			byte[] digest = mdAlgorithm.digest();

			for (int i = 0; i < digest.length; i++) {

				plainText = Integer.toHexString(0xFF & digest[i]);

				if (plainText.length() < 2) {

					plainText = "0" + plainText;
				}

				hexString.append(plainText);
			}

		} catch (Exception e) {

			throw new Exception(e);
		}

		return hexString.toString();
	}
}
>>>>>>> 7977dc9ec146544ee8c770337f780d242577bc13:CardapioWS/src/br/com/login/cardapio/ws/util/CryptoUtil.java
