package com.la.hotels.inroom.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.la.hotels.inroom.R;
import com.la.hotels.inroom.utils.PreferenceUtils;

public abstract class BaseActivity extends FragmentActivity
{
	public LinearLayout llBody ;
	public LayoutInflater inflater;
	public PreferenceUtils preferenceUtils;
	public static Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base_new_aa);
		inflater = this.getLayoutInflater();
		preferenceUtils = new PreferenceUtils(BaseActivity.this);
		mContext = this;
		baseInitializeControls();
		initialize();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	public abstract void initialize();
	private void baseInitializeControls()
	{
		llBody					 = findViewById(R.id.llBody);
//		llCart.setBackground(BitmapUtils.getSelectorFromDrawables(BaseActivity.this , R.mipmap.cart_bg , R.mipmap.cart_bg_h));
//		FontUtils.applyTypeface(tvCurrentServiceType,FontUtils.getTypeface(BaseActivity.this,FontUtils.ALLER_BOLD));
	}


	public String getVersionName()
	{
		PackageInfo pInfo = null;
		String version="";
		try
		{
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return version;



	}



	public void showToast(String strMsg)
	{
		Toast.makeText(BaseActivity.this, strMsg, Toast.LENGTH_SHORT).show();
	}

	public void startNewActivityForResult(Class<? extends Activity> activity,int requestCode)
	{
		Intent intent = new Intent(this,activity);
		startActivityForResult(intent, requestCode);
		 overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
	public void hideKeyBoard(View v)
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public void showSoftKeyboard(View view)
	{
		if (view.requestFocus())
		{
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
	}



	public boolean isGpsEnabled()
	{
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean enabledOrDisabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//		if(!enabledOrDisabled)
		enabledOrDisabled = mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		return enabledOrDisabled;
	}
	public static void setWindowParams(final Window window) {
		window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		window.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				hideNavBar(window);
			}

		});
	}
	public static  void hideNavBar(Window window) {

		if (Build.VERSION.SDK_INT >= 19) {
			View v = window.getDecorView();
			v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
					|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}




}