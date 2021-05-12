package io.itpl.microservice.utils;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AesUtils {
	
    public static SecretKey generateSecretKey(String password, byte [] iv) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), iv, 65536, 128); // AES-128
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = secretKeyFactory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }
    
    public static byte[] nonce(int size) {
    	//Prepare the nonce
        SecureRandom secureRandom = new SecureRandom();

        //Noonce should be 12 bytes
        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);
        return iv;
    }
    public static byte[] encrypt(byte[] data,String key) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    	//Prepare your key/password
    	byte[] iv = nonce(12);
        SecretKey secretKey = generateSecretKey(key, iv);


        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

        //Encryption mode on!
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        //Encrypt the data
        byte [] encryptedData = cipher.doFinal(data);
        return encryptedData;
    }
    public static byte[] decrypt(byte[] data,String key) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    	//Prepare your key/password
    	byte[] iv = nonce(12);
        SecretKey secretKey = generateSecretKey(key, iv);


        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

        //Encryption mode on!
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        //Encrypt the data
        byte [] encryptedData =  Base64.getEncoder().encodeToString(cipher.doFinal(data)).getBytes();//cipher.doFinal(data);
        return encryptedData;
    }
    public static void _main(String args) {
    	String data = "202002100001";
    	String password = "!WantUnlimited2020";
    	try {
			byte[] encrypted = encrypt(data.getBytes(),password);
			String value = new String(encrypted);
			System.out.println(value);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    

}
