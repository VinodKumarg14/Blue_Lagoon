package com.la.hotels.inroom.networks.webaccess;

import android.content.Context;

import com.la.hotels.inroom.parsers.BaseHandler;
import com.la.hotels.inroom.utils.NetworkUtils;

import java.io.InputStream;
import java.util.Vector;


public class BaseWA implements HttpListener
{
	private WebAccessListener listener;
	private Context mContext;
	
	public BaseWA(Context mContext, WebAccessListener webAccessListener)
	{
		this.mContext = mContext;
		listener = webAccessListener;
	}
	
	/**
	 * Method to start downloading the data.
	 * @param method
	 * @param requestParams
	 * @param postParams
	 * @param veConHeaders
	 * @return boolean
	 */
	public boolean startDataDownload(ServiceMethods method, String requestParams, String postParams, Vector<ConHeader> veConHeaders)
	{
		if(NetworkUtils.isNetworkConnectionAvailable(mContext))
		{
			HTTPSimulator downloader = new HTTPSimulator(this, method, requestParams,postParams,veConHeaders);
			downloader.start();
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	class HTTPSimulator extends Thread
	{
		HttpListener listener;
		ServiceMethods method;
		String requestParams,postParams;
		int rawSource;
		Vector<ConHeader> veConHeaders;
		
		public HTTPSimulator(HttpListener listener, ServiceMethods method, String requestParams, String postParams, Vector<ConHeader> veConHeaders)
		{
			this.listener = listener;
			this.method = method;
			this.requestParams = requestParams;
			this.postParams = postParams;
			this.veConHeaders = veConHeaders;
		}
		
		public HTTPSimulator(HttpListener listener, ServiceMethods method, int rawSource, String key)
		{
			this.listener = listener;
			this.method = method;
			this.rawSource = rawSource;
		}
		
		@Override
		public void run()
		{
			Response response = new Response(method, true, "Unable to connect server, please try again.");
			InputStream isResponse = null;
			
			try 
			{
				if(requestParams == null)
				  isResponse = mContext.getResources().openRawResource(rawSource);
				else
				  isResponse = new RestClient().sendRequest(method,requestParams,postParams,veConHeaders);
				
				if(isResponse != null)
				{
						BaseHandler handler = BaseHandler.getParser(method,isResponse);
						
						if(handler != null)
						{
							if(handler.getData() != null)
							{
								response.isError = false;
								response.data = handler.getData();
							}
							else
							{
								response.isError = true;
							}
						}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				// closing the stream object
		    	try
		    	{
			    	if(isResponse != null)
			    	{
			    		isResponse.close();
			    		isResponse = null;
			    	}
		    	}catch(Exception e){}
			}
			
			listener.onResponseReceived(response);
		}
	}
	
	/**
	 * Method to perform operation after receiving the response
	 */
	@Override
	public void onResponseReceived(Response response) 
	{
		this.respondWithData(response);
	}
	
	/**
	 * Method to respond for the data
	 * @param data
	 */
	protected void respondWithData(Response data)
	{
	    listener.dataDownloaded(data);
	}
}
