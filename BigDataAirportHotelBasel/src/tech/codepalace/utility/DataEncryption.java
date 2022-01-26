package tech.codepalace.utility;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description
 * 
 * 
 *
 */
public class DataEncryption {
	
	protected static final String ALGO = "AES";
	protected byte [] keyValue;
	protected String key;
	protected String dataToEncrypt;
	
	
	
	public DataEncryption() {}
	
	
	
	
	
	/**
	 * 
	 * @param dataToEntcrypt
	 * @return
	 * @throws Exception
	 * 
	 * @description method to encrypt data
	 */
	public String encryptData(String dataToEntcrypt) throws Exception { //17
		
		/*
		 We set the encryption key, the key could also be obtained from a 
		 path located for example on a USB stick. reading the key value from a USB Stick, this would make it more complicated for hackers
		 to be able to get the key for after decryption.
		 */
	

		this.key="cuc_?42Li.CO$^/n";
		
		
		this.keyValue = this.key.getBytes(); //set value keyValue
		
		this.dataToEncrypt = dataToEntcrypt; //set the value dataToEntcrypt
		
		Key key = generateKey(); //we generate the key
		
		/*
		 * I use Cipher AES to provide the functionality(Advanced Encryption Standard(AES). 
		 */
		Cipher cipher = Cipher.getInstance(ALGO);
		
		
		/*
		 * Initializes the cipher with the our generated key
		 */
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		
		/*
		 * to the array below, we give the value cipher calling doFinal to entcrypts the data due to the Cipher Mode is ENCRYPT_MODE as the line code above.
		 */
		byte[] encVal = cipher.doFinal(this.dataToEncrypt.getBytes());
		
		
		/*
		 * New String variable encryptedValue that receives through BASE64Encoder the encVal encrypt.
		 */
		String encryptedValue = new BASE64Encoder().encode(encVal);
		
		
		//return our encryptedValue
		return encryptedValue;
		
	}
	


	
	
	/**
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 * 
	 * @description method to decrypt data
	 */
	public String decryptData(String encryptedData) throws Exception {
		
		
		/*
		 * we set the same value for the key as when we encrypted the data.
		 * 
		 * If the key was for example on a usb stick, then we could configure the program in another way to force the user to connect the usb stick 
		 * in order to decrypt the data.
		 */
		this.key="cuc_?42Li.CO$^/n";
		
		this.keyValue = this.key.getBytes();
		
		Key key = generateKey();
		
		//we pass as an argument the same constant(AES).
		Cipher cipher = Cipher.getInstance(ALGO);
		
		//We specify that now we want to decrypt
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		
		byte[]decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		
		byte[] decValue = cipher.doFinal(decordedValue);
		
		String decryptedValue = new String(decValue);
		
		//return the value
		return decryptedValue;
	}
	
	

	/**
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @description method to generate the key
	 */
	private Key generateKey() throws Exception {
		
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
		
	}
	

}


