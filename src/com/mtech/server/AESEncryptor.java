package com.mtech.server;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Base64;
import android.util.Base64;
/**
 *  
 * @author User
 */
public class AESEncryptor {


private static final String ALGO = "AES";
public static String encrypt(String Data, String secretKey){
	String encryptedValue="";
	try{
		Key key = generateKey(secretKey.getBytes());
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        //String encryptedValue = new BASE64Encoder().encode(encVal);
        encryptedValue=AESEncryptor.encode(encVal);        
	}catch(Exception e){
		e.printStackTrace();
	}
        return encryptedValue;
    }

    public static String decrypt(String encryptedData, String secretKey){
    	String decryptedValue="";
    	try{
    		Key key = generateKey(secretKey.getBytes());
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
          //  byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
            byte[] decordedValue=AESEncryptor.decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            decryptedValue = new String(decValue);
            System.out.println(decryptedValue);
    	}catch(Exception e){
    		e.printStackTrace();
    	}        
        return decryptedValue;
    }
    private static Key generateKey(byte[] bt) throws Exception {
        Key key = new SecretKeySpec(bt, ALGO);
        return key;
}
    public static void main(String[] args) {
        try {
            String enc=AESEncryptor.encrypt("India is My Country. All Indians are my Brothers and Sisters. I Love My Country.", "AbCdEfGhIjKlMnOp");
            System.out.println("Encrypted Data>>>>>>"+enc);
            String dec=AESEncryptor.decrypt(enc, "AbCdEfGhIjKlMnOp");
            System.out.println("Decrypted Data>>>>>>"+dec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String encode(byte[] data){
//    return Base64.encodeBase64String(data);
    	return Base64.encodeToString(data, Base64.DEFAULT);
}
public static byte[] decode(String input){
//	  return Base64.decodeBase64(input);
    return Base64.decode(input, Base64.DEFAULT);
}
}
