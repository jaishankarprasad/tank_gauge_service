package com.rainiersoft.tankgauge.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

import javafish.clients.opc.exception.VariantTypeException;
import javafish.clients.opc.lang.Translate;
import javafish.clients.opc.variant.Variant;

public class RSUtil 
{
 
	final static Logger logger=Logger.getLogger(RSUtil.class);
 	public static final HashMap<Integer, String> variantMap = new HashMap<Integer, String>();
 	public static final HashMap<String, Integer> tagMap = new HashMap<String, Integer>();
 	
 	
 	static 
 	{
 		variantMap.put(new Integer(Variant.VT_I2), "16bit Int");
 		variantMap.put(new Integer(Variant.VT_I4), "32bit Int");
 		variantMap.put(new Integer(Variant.VT_R4), "32bit Float");
 		variantMap.put(new Integer(Variant.VT_R8), "64bit Float");
 		variantMap.put(new Integer(Variant.VT_BOOL), "1bit boolean");
 		variantMap.put(new Integer(Variant.VT_BSTR), "String");
 		variantMap.put(new Integer(Variant.VT_INT), "16bit Int");

 		tagMap.put("16bit Int", new Integer(Variant.VT_I2));
 		tagMap.put("32bit Int", new Integer(Variant.VT_I4));
 		tagMap.put("32bit Float", new Integer(Variant.VT_R4));
 		tagMap.put("64bit Float", new Integer(Variant.VT_R8));
 		tagMap.put("1bit boolean", new Integer(Variant.VT_BOOL));
 		tagMap.put("String", new Integer(Variant.VT_BSTR));
 		
 		
 		tagMap.put("VT_I2", new Integer(Variant.VT_I2));
 		tagMap.put("VT_I4", new Integer(Variant.VT_I4));
 		tagMap.put("VT_R4", new Integer(Variant.VT_R4));
 		tagMap.put("VT_R8", new Integer(Variant.VT_R8));
 		tagMap.put("VT_BOOL", new Integer(Variant.VT_BOOL));
 		tagMap.put("VT_BSTR", new Integer(Variant.VT_BSTR));
 		
 
 	}
 	
	public static int getVariantType (String tagType)
	{		
		Integer intObj = tagMap.get(tagType); 
		return intObj.intValue();		
	}
	
	public static String getTagType(int variantType)
	{
		return (String) variantMap.get(new Integer(variantType)); 		
	}
	
	public static Variant getVariantValue(int tagType, Object value)
	{
		
		 switch (tagType) 
		 {
	      case Variant.VT_I4:
		        return new Variant(Double.parseDouble((String)value));
	      case Variant.VT_I2:	  
	    	  return new Variant(Integer.parseInt((String)value));
	      case Variant.VT_BOOL:
	    	  return new Variant(Boolean.parseBoolean((String)value));
	      case Variant.VT_R4:
	    	  return new Variant(Float.parseFloat((String)value));
	      case Variant.VT_R8:
	    	  return new Variant(Double.parseDouble((String)value));
	      case Variant.VT_BSTR:
	    	  return new Variant((String)value);
	      case Variant.VT_ERROR:
	      case Variant.VT_NULL:
	      case Variant.VT_EMPTY:
	        return new Variant();
	      default :
	    	 logger.error(Translate.getString("VARIANT_TYPE_EXCEPTION"));
	         throw new VariantTypeException(Translate.getString("VARIANT_TYPE_EXCEPTION"));
	    }		
	}
	
	public static String getTagValue(Variant varObj)
	{
	    int varianType = varObj.getVariantType();

	      switch(varianType)
	      {
	 	  case Variant.VT_I2 :
	 		  return ""+varObj.getWord();	 		 
	      case Variant.VT_INT :
	 		  return ""+varObj.getInteger();
	      case Variant.VT_R4 :
	    	  return ""+varObj.getFloat();
	      case Variant.VT_BOOL :
	    	  return ""+varObj.getBoolean();
	      case Variant.VT_R8:
	    	  return ""+varObj.getDouble();
	      case Variant.VT_BSTR:
	    	  return ""+varObj.getString();
	      default :
		        return "";
	      
	      }
	}	
	
}
