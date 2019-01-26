package com.la.hotels.inroom.networks.webaccess;

import android.util.Log;

import com.la.hotels.inroom.objects.DeviceRegistrationDO;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

public class BuildXMLRequest
{

	public static String doLoginJsonBody(String email, String password) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("email", email);
			jsonObjSend.put("password", password);
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

    public static String roomRegistrationBody(HashMap<String,String> hm ) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("device_id", hm.get("device_id"));
			jsonObjSend.put("allocated_room",hm.get("allocated_room") );
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }

	public static String roomServicesByCategoryId(String categoryId) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("categoryId", categoryId);
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getOrderDetailsByOrderId(String orderId) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("orderId", orderId);
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String postExtendStayDetails(String days) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("days", days);
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getDeviceReg(DeviceRegistrationDO obj) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("device_id", obj.device_id);
			jsonObjSend.put("tab_model", obj.tab_model);
			jsonObjSend.put("imei1", obj.imei1);
			jsonObjSend.put("imei2", obj.imei2);
			jsonObjSend.put("freespace", obj.freespace);
			jsonObjSend.put("sim_no", obj.sim_no);
			jsonObjSend.put("mobile_no", obj.mobile_no);
			jsonObjSend.put("registred_by", obj.registred_by);
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getFCMToken(HashMap<String,String> hm) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("fcm_token", hm.get("fcm_token"));
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getCheckoutHeader(HashMap<String,String> hm) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("isEmailSend", hm.get("isEmailSend"));
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getAddCartByItemsIds(Vector<String> vecItemIds) {
		try {
			String ids = "[";
			if(vecItemIds!=null  && vecItemIds.size()>0) {
				for (int i = 0; i < vecItemIds.size(); i++) {
					ids = ids + "\"" + vecItemIds.get(i) + "\",";

				}
				ids = ids.substring(0,ids.length()-1);

			}
				ids += "]";
			JSONObject jsonObjSend;
			try {
				jsonObjSend = new JSONObject();
				jsonObjSend.put("id", ids);
				printJSONRequest(jsonObjSend.toString());
				return jsonObjSend.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


    public static String getRoomDetails(HashMap<String,String> hmDeviceAssignment) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("device_id", hmDeviceAssignment.get("device_id"));
			jsonObjSend.put("allocated_room", hmDeviceAssignment.get("allocated_room"));
			printJSONRequest(jsonObjSend.toString());
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";


    }
	public static String getApkUpdateJsonBody(String deviceid, String apkVerion) {
		JSONObject jsonObjSend;
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put("deviceid", deviceid);
			jsonObjSend.put("apkversion", apkVerion);
			return jsonObjSend.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getCurrentTimeStamp()
	{
		Long tsLong = System.currentTimeMillis()/1000;
		String ts = tsLong.toString();
		return ts;
	}

    public static void printJSONRequest(String request)
    {
        Log.e("JSON Request :",request);
    }

}
