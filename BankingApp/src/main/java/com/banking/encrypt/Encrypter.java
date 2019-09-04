package com.banking.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encrypter {

	private static final String SECRET_KEY_1 = System.getenv("SECRET_KEY1");
	private static final String SECRET_KEY_2 = System.getenv("SECRET_KEY2");

	private IvParameterSpec ivParameterSpec;
	private SecretKeySpec secretKeySpec;
	private Cipher cipher;

	public Encrypter() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		ivParameterSpec = new IvParameterSpec(SECRET_KEY_1.getBytes("UTF-8"));
		secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes("UTF-8"), "AES");
		cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	}

	public String encrypt(String toBeEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
		return Base64.encodeBase64String(encrypted);
	}

	public String decrypt(String encrypted) throws InvalidAlgorithmParameterException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
		return new String(decryptedBytes);
	}

}
