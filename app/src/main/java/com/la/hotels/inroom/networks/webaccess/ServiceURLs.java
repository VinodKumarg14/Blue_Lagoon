package com.la.hotels.inroom.networks.webaccess;


import com.la.hotels.inroom.constants.AppConstants;

public class ServiceURLs
{

    private static final String DEVICE_REG_API = "http://staging.orb-in-rooms-tab-api.myryd.com/orbtab/api/v1/device-registration";
	private static final String ROOM_REGISTRATION_API = "http://staging.orb-in-rooms-tab-api.myryd.com/orbtab/api/v1/room-registration";
	private static final String HOME_SERVICE_API = "http://staging.orb-in-rooms-tab-api.myryd.com/orbtab/api/v1/home";
	private static final String ROOM_SERVICE_IEMS_API = "http://staging.orb-in-rooms-tab-api.myryd.com/orbtab/api/v1/services";


	public static int getRequestType(ServiceMethods wsMethod)
	{
		switch (wsMethod) 
		{
			case WS_ROOM_REGISTRATION:
			case WS_HOME_SERVICE:
			case WS_ROOM_SERVICES:
			case WS_AVAILABLE_ROOMS:
			case WS_DEVICE_REGISTRATION:
				return AppConstants.POST;
//			case WS_GET_WEATHER_DETAILS:
//				return AppConstants.GET;

		}
		return AppConstants.POST;
		
	}

	public static String getRequestedURL(ServiceMethods wsMethod)
	{
		switch(wsMethod)
		{
			case WS_ROOM_REGISTRATION:
				return ServiceURLs.ROOM_REGISTRATION_API;
            case WS_DEVICE_REGISTRATION:
				return ServiceURLs.DEVICE_REG_API;
			case WS_HOME_SERVICE:
				return ServiceURLs.HOME_SERVICE_API;
			case WS_ROOM_SERVICES:
				return ServiceURLs.ROOM_SERVICE_IEMS_API;



        }
		return null;
	}
}
