package com.la.hotels.inroom.networks.webaccess;


import com.la.hotels.inroom.utils.LogUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;


public class HttpHelper 
{
	//Time out for the connection is set with settings CSCClientTimeout in milliseconds 
	private int TIMEOUT_CONNECT_MILLIS = (60 * 1000);
	private int TIMEOUT_READ_MILLIS = TIMEOUT_CONNECT_MILLIS - 5000;

	public DefaultHttpClient getHttpClient() {
		System.setProperty("http.keepAlive", "false");

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

		DefaultHttpClient defaultHttpClient = new DefaultHttpClient(cm, params);

		return defaultHttpClient;
	}
	
	/**
	 * 
	 * @param strPostURL
	 * @param strParamToPost
	 * @param strParamToPost
	 * @return
	 */
	public InputStream sendPOSTRequest(String strPostURL, String requestParams , String strParamToPost, Vector<ConHeader> veConHeaders)
	{
		strPostURL = strPostURL.replace(" ", "%20");
		strPostURL+=requestParams;

		DefaultHttpClient defaultHttpClient = getHttpClient();
		System.setProperty("http.keepAlive", "false");
		HttpPost httpPost = new HttpPost(strPostURL);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		addRequestHeaders(httpPost, veConHeaders);

		InputStream inputStream = null;
		
		try 
		{
			if(strParamToPost != null)
				httpPost.setEntity(new StringEntity(strParamToPost));

			HttpResponse response = defaultHttpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * 
	 * @param strGetURL
	 * @return
	 */
	public InputStream sendGETRequest(String strGetURL)
	{
		strGetURL = strGetURL.replace(" ", "%20");
		DefaultHttpClient defaultHttpClient = getHttpClient();
		System.setProperty("http.keepAlive", "false");
		HttpGet httpGet = new HttpGet(strGetURL);

		InputStream inputStream = null;
		
		try 
		{

			HttpResponse response = defaultHttpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return inputStream;
	}
	
   public InputStream sendGETRequest(String strGetURL, String params, Vector<ConHeader> veConHeaders)
   {
		strGetURL = strGetURL.replace(" ", "%20");
		strGetURL+=params;
		System.setProperty("http.keepAlive", "false");
		
		DefaultHttpClient defaultHttpClient = getHttpClient();
		HttpGet httpGet = new HttpGet(strGetURL);
		
		addRequestHeaders(httpGet, veConHeaders);
		
		InputStream inputStream = null;
		
		try 
		{
			HttpResponse response = defaultHttpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return inputStream;
	}
	
	private void addRequestHeaders(HttpRequest request, Vector<ConHeader> veConHeaders)
	{
		if(veConHeaders != null && veConHeaders.size()>0)
		{
			for(int i=0; i<veConHeaders.size(); i++)
			{
				ConHeader conHeader = veConHeaders.get(i);
				request.addHeader(conHeader.name, conHeader.value.replace("\n", ""));
			}
		}
	}
	
	/**This method will return inputStream for given url.
	 * @param url
	 * @return InputStream
	 */
	public static InputStream getInputStreamFromOtherUrl(String url)
	{
		try
		{
			HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();
			int statusCode = con.getResponseCode();
	    	
	    	if(statusCode == HttpStatus.SC_OK)
	    		return con.getInputStream();
		}
		catch(Exception e)
		{
			LogUtils.error("HttpHelper", "Error occured in getInputStreamFromOtherUrl(): "+e.toString());
		}
		return null;
	}
}
