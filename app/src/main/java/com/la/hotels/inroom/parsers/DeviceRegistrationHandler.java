package com.la.hotels.inroom.parsers;

import android.util.Log;

import com.google.gson.Gson;
import com.la.hotels.inroom.objects.ApiResponseAsObject;

import java.io.InputStream;


public class DeviceRegistrationHandler extends BaseHandler
{
	private boolean status = true;
	private ApiResponseAsObject api;
	public DeviceRegistrationHandler(InputStream inputStream)
	{
		String strResponse = getStringFromInputStream(inputStream);
		Log.e("response-devicereg" ,strResponse);

		try
		{
			Gson gson = new Gson();
			api = gson.fromJson(strResponse, ApiResponseAsObject.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	  
	@Override
	public Object getData()
	{
		  return api;
	}

	@Override
	public Object getErrorData()
	{
		return null;
	}

}
