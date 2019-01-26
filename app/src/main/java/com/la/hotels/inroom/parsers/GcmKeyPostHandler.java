package com.la.hotels.inroom.parsers;

import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;


public class GcmKeyPostHandler extends BaseHandler
{
	private boolean status = true;
	private String message;
	public GcmKeyPostHandler(InputStream inputStream)
	{
		String strResponse = getStringFromInputStream(inputStream);
		Log.e("GcmKeyPostHandler",""+strResponse);
		try 
		{
			JSONObject mainObj = new JSONObject(strResponse);
			if(strResponse.contains("error"))
			{
				String strStatus = (mainObj.getString("error"));
				status = !strStatus.equalsIgnoreCase("false");
			}
			
			if(!status)
			{
				message = mainObj.optString("message");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	  
	@Override
	public Object getData()
	{
		  return (!status);
	}

	@Override
	public Object getErrorData()
	{
		return null;
	}

}
