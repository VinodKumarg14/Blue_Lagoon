package com.la.hotels.inroom.utils;

import android.util.Base64;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils 
{
	 //String to Int
	public static int getInt(String str)
	{
		int value = 0;
		if (str == null || str.equalsIgnoreCase(""))
			return value;
		try 
		{
			value = Integer.parseInt(str);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}


	public static boolean isDouble(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	//String to long
	public static long getLong(String string)
	{
		long value = 0;
		
		if (string == null || string.equalsIgnoreCase(""))
			return value;
		
		try 
		{
			value = Long.parseLong(string);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return value;
	}
	
	//String to float
	public static float getFloat(String string)
	{
		float value = 0f;
		
		if (string == null || string.equalsIgnoreCase("")
				|| string.equalsIgnoreCase("."))
			return value;
		
		try 
		{
			value = Float.parseFloat(string);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return value;
		}
		
		return value;
	}
	
	//String to double
	public static double getDouble(String str)
	{
		double value = 0;
		
		if (str == null || str.equalsIgnoreCase(""))
			return value;
		
		try 
		{
			value = Double.parseDouble(str);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}
	
	//String to boolean
	public static boolean getBoolean(String str)
	{
		boolean value = false;
		if (str == null || str.equalsIgnoreCase(""))
			return value;
		try 
		{
			value = Boolean.parseBoolean(str);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}

	//String to boolean
	public static int getIntFromBoolean(boolean b)
	{
		boolean value = false;
		if (b)
			return 1;

		return 0;
	}

	//Method to check email validation
	public static boolean isValidEmail(String string)
	{
		final Pattern EMAIL_ADDRESS_PATTERN = Pattern
				.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
		Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(string);
		boolean value = matcher.matches();
		return value;
	}

	public static String encodeString(String str)
	{
		String encodedString = "";
		
		try 
		{
			byte[] data = str.getBytes("UTF-8");
			encodedString = Base64.encodeToString(data, Base64.DEFAULT);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return encodedString;
	}

	public static String saveString(String str)
	{	
		int len = str.length();
		int sum = 100;
		char[] encryptedChars = new char[len];
		for (int i=0; i<len; i++) 
			encryptedChars[i] = (char)((int)str.charAt(i) + sum);
		char[] newNameStr = new char[len];
		for (int i=0; i<len; i++) 
			newNameStr[i] = encryptedChars[len-1-i];
		for (int i=0; i<len; i++) 
			encryptedChars[i] = newNameStr[i];
		return new String(encryptedChars);
	}	
	public static String getString(String encrptredString)
	{	
	    int len = encrptredString.length();
	    char[] decryptedChars = new char[len];
	    for (int i=0; i<len; i++) 
	        decryptedChars[i] = (char)((int)encrptredString.charAt(i) - 100);
	    char[] newNameStr = new char[len];
	    for (int i=0; i<len; i++) 
	        newNameStr[i] = decryptedChars[len-1-i];
	    for (int i=0; i<len; i++) 
	        decryptedChars[i] = newNameStr[i];
	    return (new String(decryptedChars));
	}

	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
