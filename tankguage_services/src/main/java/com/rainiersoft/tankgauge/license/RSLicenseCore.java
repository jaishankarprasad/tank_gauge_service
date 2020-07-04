package com.rainiersoft.tankgauge.license;

import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;


import java.security.*;
import javax.crypto.*;

public class RSLicenseCore 
{
	final static Logger logger=Logger.getLogger(RSLicenseCore.class);
	private RSLicense licObj = null;
	
    private String productKey = null; // 128 bit key
    
    public static String licGenString = null;
    
    private final String productKeyFileOnDisk = System.getProperty("user.home") + File.separator + "product.key";
    
    private String licenceFile = System.getProperty("user.home") + File.separator + "rslicense.txt";
    
    
	public RSLicenseCore (String licenseFile)
	{
		this.licenceFile = licenseFile;
 	}
	
	public RSLicenseCore ()
	{
		licObj = new RSLicense ();
 	}
	
	public String createLicenseFile() throws Exception
	{
		 String plainString = licObj.toString();
		 return	GenerateAndWriteToFile(plainString);
	}
	
	public  RSLicense createLicenseObjectFromFile(String licenceFile) throws Exception
	{
		 return  getLicenseObject(ReadFromFile(licenceFile));
 	}
	
	
	public String GenerateAndWriteToFile(String plainString) throws Exception
	{
		String text=new String();
		text= plainString;

		byte[] plainText = text.getBytes("UTF8");

		// get a DES private key
		logger.info("\nStart generating DES key");
		System.out.println( "\nStart generating DES key" );
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56);
		Key key = keyGen.generateKey();
		logger.info("Finish generating DES key");
		System.out.println( "Finish generating DES key" );
		//
		// get a DES cipher object and print the provider
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		System.out.println( "\n" + cipher.getProvider().getInfo() );
		//
		// encrypt using the key and the plaintext
		logger.info("\nStart encryption");
		System.out.println( "\nStart encryption" );
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText);
		logger.info("Finish encryption: ");
		System.out.println( "Finish encryption: " );
		logger.info(new String(cipherText, "UTF8"));
		System.out.println( new String(cipherText, "UTF8") );

		//Now writing to an ouput file the cipherText
		try
		{
			FileOutputStream fs=new FileOutputStream(licenceFile);
			fs.write(cipherText);

			//Write your key to an output file.
			byte[] keyAsByte = key.getEncoded();
			FileOutputStream keyfos = new FileOutputStream("key.txt");
			keyfos.write(keyAsByte);
			keyfos.close();
			
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		
		return licenceFile;
	}
	
	
	public  RSLicense getLicenseObject(String plainString)
	{
		
		RSLicense licObj = new RSLicense ();
		
 	    String tokens[]= plainString.split(";");
	    
	       for (int i= 0; i < tokens.length; i++)
	       {
		      KeyValue kv = new KeyValue (tokens[i]);
		      
		      if (kv.getKey().equalsIgnoreCase("version"))
		      {
		    	  licObj.version=kv.getValue();
		      }
		      if (kv.getKey().equalsIgnoreCase("product"))
		      {
		    	  licObj.product=kv.getValue();
		      }
		      if (kv.getKey().equalsIgnoreCase("mac"))
		      {
		    	  licObj.setMac(kv.getValue());
		      }
		      if (kv.getKey().equalsIgnoreCase("noOfDevices"))
		      {
		    	  licObj.setNoOfDevices(Integer.parseInt((kv.getValue())));
		      }
		      if (kv.getKey().equalsIgnoreCase("noOfTags"))
		      {
		    	  licObj.setNoOfTags(Integer.parseInt((kv.getValue())));
		      }
		      if (kv.getKey().equalsIgnoreCase("duration"))
		      {
		    	  licObj.setDuration((Integer.parseInt((kv.getValue()))));
		      }
		      if (kv.getKey().equalsIgnoreCase("activationDate"))
		      {
		  		try 
		  		{
		  			licObj.setActivationDate(new SimpleDateFormat("dd/MM/yyyy").parse(kv.getValue()));
				}
		  		catch (ParseException e) 
		  		{
		  			logger.error(e);
					e.printStackTrace();
				}
 		      }
  
 	       }
	       
		
		return licObj;
		
	}

	public static  String ReadFromFile(String licenceFile) throws Exception
	{

		//Read your key
		FileInputStream keyFis = new FileInputStream("key.txt");
		byte[] encKey = new byte[keyFis.available()];
		keyFis.read(encKey);
		keyFis.close();
		Key keyFromFile = new SecretKeySpec(encKey, "DES");
		//Read your text

		FileInputStream encryptedTextFis = new FileInputStream(licenceFile);
		byte[] encText = new byte[encryptedTextFis.available()];
		encryptedTextFis.read(encText);
		encryptedTextFis.close();
		//Decrypt
		Cipher decrypter = Cipher.getInstance("DES/ECB/PKCS5Padding");
		decrypter.init(Cipher.DECRYPT_MODE, keyFromFile);
		byte[] decryptedText = decrypter.doFinal(encText);
		//Print result
		System.out.println("Decrypted Text: " + new String(decryptedText));
		logger.info("Decrypted Text: " + new String(decryptedText));
			
 		return new String(decryptedText);
		
	}
	
	public RSLicense getLicObj() {
		return licObj;
	}

	public void setLicObj(RSLicense licObj) {
		this.licObj = licObj;
	}

	
	  
	  public static void main (String args[]) throws Exception
	  {
		  RSLicenseCore gr = new RSLicenseCore ();
		  
	//	  gr.ProcessLicense("ABCD", false);
		  
	  }
	  
	  

		class KeyValue
		{
			String key;
			String value;
			
			public KeyValue(String Key, String Value)
			{
				this.key = Key;
				this.value = Value;
			}
			
			public KeyValue (String keyValue)
			{

				String tokens[]= keyValue.split("=");
	 			this.key = tokens[0].trim();
				this.value = tokens[1].trim();
				
			}
			
			public String getKey ()
			{
				return key;
			}
			public String getValue()
			{
				return value;
			}
		}
 	
}