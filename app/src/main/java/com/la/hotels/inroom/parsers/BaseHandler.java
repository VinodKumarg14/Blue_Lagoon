package com.la.hotels.inroom.parsers;

import com.la.hotels.inroom.networks.webaccess.ServiceMethods;

import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseHandler extends DefaultHandler
{
	public Boolean currentElement = false;
	public String currentValue = "";
	public abstract Object getData();
	public abstract Object getErrorData();
	
	@SuppressWarnings("incomplete-switch")
	public static BaseHandler getParser(ServiceMethods wsMethod, InputStream inputStream)
	{
		switch(wsMethod)
		{
			case WS_DEVICE_REGISTRATION:
				return new DeviceRegistrationHandler(inputStream);

		}
		return null;
	}
	
	
	public static String getStringFromInputStream(InputStream inputStream)
	{
	  if(inputStream != null)
	  {
	     BufferedReader br = null;
	     StringBuilder sb = new StringBuilder();
	     String line;
	    try 
	    {
	       br = new BufferedReader(new InputStreamReader(inputStream));
	       while ((line = br.readLine()) != null) 
	       {
	         sb.append(line);
	       }

	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    } 
	    finally 
	    {
	      if (br != null) 
	      {
	        try 
	        {
	         br.close();
	        } 
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	    }
	   }

	   return sb.toString();
	  }
	  else
	  {
	   return "";
	  }
	 }
	

}
