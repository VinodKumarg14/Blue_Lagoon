package com.la.hotels.inroom.ui.activities;

import android.app.Application;
import android.content.Context;

public class SofitelInroomApplication extends Application
{
	public static String MyLock = "Lock";
	public static Context context;
	
	


	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		if(context ==null)
			context = SofitelInroomApplication.this;
	}
	
}