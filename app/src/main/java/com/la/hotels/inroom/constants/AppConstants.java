package com.la.hotels.inroom.constants;

import android.os.Environment;

import java.io.File;
import java.math.BigInteger;

public class AppConstants 
{

	public static final int GET  = 1;
	public static final int POST = 2;
	public static final long DOUBLE_CLICK_ESCPE_TIME = 800;
	public static final String DEVICE_REGISTRATION = "Device Registration";
	public static String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString() + File.separator;
	public static String DATABASE_PATH 	= "";


	public static final String DATABASE_NAME = "bluelagoon_inroom.sqlite";
	public static boolean DB_HASUpdate = false;
	public static boolean IS_APP_FIRST_INSTALL = false;
	public static final String ACTION_INTERNET_ISSUE= "com.la.hotels.inroom.INTERNET_PROBLEM";
	public static final long ONE_GB_BYTES = 1073741824;
	public static final BigInteger MAX_LIMIT_STORAGE = BigInteger.valueOf(58 * ONE_GB_BYTES); // 57 GB in bytes
	public static final String ACTION_SYNC_COMPLETED = "com.la.hotels.inroom.SYNC_COMPLETED";

	public static final String AUTHORIZATION = "Authorization";
	public static final String CONTENT_TYPE = "Content-Type";



}
