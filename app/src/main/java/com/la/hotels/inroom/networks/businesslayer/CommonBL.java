package com.la.hotels.inroom.networks.businesslayer;

import android.content.Context;

import com.la.hotels.inroom.constants.AppConstants;
import com.la.hotels.inroom.networks.webaccess.BaseWA;
import com.la.hotels.inroom.networks.webaccess.BuildXMLRequest;
import com.la.hotels.inroom.networks.webaccess.ConHeader;
import com.la.hotels.inroom.networks.webaccess.ServiceMethods;
import com.la.hotels.inroom.objects.DeviceRegistrationDO;

import java.util.Vector;

public class CommonBL extends BaseBL
{
	public CommonBL(Context mContext, DataListener listener)
	{
		super(mContext, listener);
	}


	public boolean deviceRegistration(DeviceRegistrationDO deviceRegistrationDO) {
		return new BaseWA(mContext,this).startDataDownload(ServiceMethods.WS_DEVICE_REGISTRATION,"",BuildXMLRequest.getDeviceReg(deviceRegistrationDO),getBasicOauth(null));

	}
	public Vector<ConHeader> getBasicOauth(String oauthToken){
		Vector<ConHeader> vecConHeaders = new Vector<>();

		if(oauthToken != null) {
			ConHeader conHeader2 = new ConHeader(AppConstants.AUTHORIZATION, "Bearer "+oauthToken);
			vecConHeaders.add(conHeader2);

			ConHeader conHeader1 = new  ConHeader(AppConstants.CONTENT_TYPE, "application/x-www-form-urlencoded");
			vecConHeaders.add(conHeader1);

		}
		return vecConHeaders;
	}



}
