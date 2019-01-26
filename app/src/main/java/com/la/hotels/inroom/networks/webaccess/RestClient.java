package com.la.hotels.inroom.networks.webaccess;

import com.la.hotels.inroom.constants.AppConstants;

import java.io.InputStream;
import java.util.Vector;


public class RestClient 
{
	/**
	 * Method to send the request to the server.
	 * @param method
	 * @param parameters
	 * @return InputStream
     */
	public InputStream sendRequest(ServiceMethods method, String parameters, String postParams, Vector<ConHeader> veConHeaders) {
		int reqType = ServiceURLs.getRequestType(method);
		
		if(reqType == AppConstants.GET)
		{
			return new HttpHelper().sendGETRequest(ServiceURLs.getRequestedURL(method),parameters,veConHeaders);
		}
		else if(reqType == AppConstants.POST)
		{
			return new HttpHelper().sendPOSTRequest(ServiceURLs.getRequestedURL(method),parameters,postParams,veConHeaders);
		}
		
		return null;
	}
}
