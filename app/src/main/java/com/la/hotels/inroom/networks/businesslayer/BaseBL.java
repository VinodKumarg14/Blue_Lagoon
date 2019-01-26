package com.la.hotels.inroom.networks.businesslayer;

import android.app.Activity;
import android.content.Context;

import com.la.hotels.inroom.networks.webaccess.Response;
import com.la.hotels.inroom.networks.webaccess.WebAccessListener;


/** this class contains the control all over the Business Layer Classes **/
public class BaseBL implements WebAccessListener
{
	DataListener listener;
	public Context mContext;
	
	public BaseBL(Context mContext, DataListener listener)
	{
		this.mContext = mContext;
		this.listener = listener;
	}
	

	@Override
	public void dataDownloaded(Response data) {

		if(listener != null)
		{
			((Activity)mContext).runOnUiThread(new DataRetreivedRunnable(listener, data));
		}
	}

	/**
	 * Class to respond when data has received successfully.
	 */
	class DataRetreivedRunnable implements Runnable
	{
		DataListener listener;
		Response data;
		
		DataRetreivedRunnable(DataListener listener, Response data)
		{
			this.listener = listener;
			this.data = data;
		}
		
		@Override
		public void run() 
		{
			listener.dataRetreived(data);
		}
	}
}
