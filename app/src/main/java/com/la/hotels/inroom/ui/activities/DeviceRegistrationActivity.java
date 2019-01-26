package com.la.hotels.inroom.ui.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.la.hotels.inroom.R;
import com.la.hotels.inroom.constants.AppConstants;
import com.la.hotels.inroom.networks.businesslayer.CommonBL;
import com.la.hotels.inroom.networks.businesslayer.DataListener;
import com.la.hotels.inroom.networks.webaccess.Response;
import com.la.hotels.inroom.networks.webaccess.ServiceMethods;
import com.la.hotels.inroom.objects.ApiResponseAsObject;
import com.la.hotels.inroom.objects.DeviceRegistrationDO;
import com.la.hotels.inroom.utils.PreferenceUtils;

import java.lang.reflect.Method;


public class DeviceRegistrationActivity extends BaseActivity implements View.OnClickListener, DataListener {


    private static final int MULTIPLE_PERMISSIONS = 5;
    private TextView tvClear;
    private TextView tvRegistration;
    private EditText etDeviceId;
    private EditText etTableModel;
    private EditText etIMEI1;
    private EditText etIMEI2;
    private EditText etSimNum;
    private EditText etMobileNumber;
    private EditText etEmpId;
    private EditText etAvailbleMemory;
    private EditText etAddress;
    private View iv_BackIcon;
    private View iv_Refresh;
    private View iv_Close;
    private TextView tv_Title;
    private long mLastClickTime;
    private DeviceRegistrationDO deviceRegistrationDO = new DeviceRegistrationDO();
    private Intent intent;
    private SparseIntArray mErrorString;
    private boolean allPermissionsAreAccepted;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private View selectedview = null;
    private String IMEI1 = "";
    private String IMEI2 = "";
    private Dialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //it closes keyboard and resize layout when keyboard is opened
        super.onCreate(savedInstanceState);
        selectedview = new View(this);
        setContentView(R.layout.fragment_device_registration);
        initializeControls();
        getdeviceId();
        setAllFields();
    }

    private void getdeviceId() {
        TelephonyManager telephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getFirstMethod = telephonyClass.getMethod("getDeviceId", parameter);
            Log.d("SimData", getFirstMethod.toString());
            Object[] obParameter = new Object[1];
            obParameter[0] = 0;
            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            IMEI1 = (String) getFirstMethod.invoke(telephony, obParameter);
            Log.d("SimData", "first :" + IMEI1);
            obParameter[0] = 1;
            IMEI2 = (String) getFirstMethod.invoke(telephony, obParameter);
            Log.d("SimData", "Second :" + IMEI2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize() {
        boolean isRoomAssigned = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_ROOM_ASSIGNED, false);
        boolean isDeviceRegistered = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_DEVICE_REGISTERED, false);
        if (isDeviceRegistered && isRoomAssigned) {
            finish();
            intent = new Intent(DeviceRegistrationActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (isDeviceRegistered) {
            finish();
            intent = new Intent(DeviceRegistrationActivity.this, HomeActivity.class);
            startActivity(intent);
        }
//        orientation = this.getResources().getConfiguration().orientation;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //it closes keyboard and resize layout when keyboard is opened

    }

    public void onPermissionsGranted(int requestCode) {
        allPermissionsAreAccepted = true;
        switch (selectedview.getId()) {
            case R.id.tvRegistration:
                String validationMessage = getDataValidation();
                if (validationMessage == null) {
                    setData();
                    hideKeyBoard(selectedview);
//                    showLoaderDialog(null);
                    new CommonBL(DeviceRegistrationActivity.this, DeviceRegistrationActivity.this).deviceRegistration(deviceRegistrationDO);
                } else {
                    showpopup(validationMessage);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialogbox();
                        }
                    }, 3000);
                }


                break;
            case R.id.tvClear:
                clearAllFields();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestAppPermissions(PERMISSIONS, R.string.runtime_permissions_txt, MULTIPLE_PERMISSIONS);
    }

    private void initializeControls() {

        tvClear = findViewById(R.id.tvClear);
        tvRegistration = findViewById(R.id.tvRegistration);
        etDeviceId = findViewById(R.id.etDeviceId);
        etTableModel = findViewById(R.id.etTableModel);
        etIMEI1 = findViewById(R.id.etIMEI1);
        etIMEI2 = findViewById(R.id.etIMEI2);
        etSimNum = findViewById(R.id.etSimNum);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etEmpId = findViewById(R.id.etEmpId);
        etAvailbleMemory = findViewById(R.id.etAvailbleMemory);
        etAddress = findViewById(R.id.etAddress);

        iv_BackIcon = findViewById(R.id.ivBackIcon);
        iv_Refresh = findViewById(R.id.ivRefresh);
        iv_Close = findViewById(R.id.ivClose);
        tv_Title = findViewById(R.id.tvTitle);

        tv_Title.setText(AppConstants.DEVICE_REGISTRATION);
        tv_Title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        iv_BackIcon.setVisibility(View.GONE);
        iv_Close.setVisibility(View.GONE);
        iv_Refresh.setVisibility(View.GONE);

        tvRegistration.setOnClickListener(this);
        tvClear.setOnClickListener(this);
//        BaseActivity.drawableUtils.setThemeBlackButtonBackground(tvRegistration);
//        BaseActivity.drawableUtils.setThemeWhiteButtonBackground(tvClear);
        hideKeyBoard(tvRegistration);
        setAllFields();
        mErrorString = new SparseIntArray();
//        String[] PERMISSIONS = {Manifest.permission_group.STORAGE,Manifest.permission_group.PHONE,Manifest.permission_group.LOCATION};


    }

    @Override
    public void onResume() {
        super.onResume();
        //in manifest and below lines of code, few things are addded to hide functionality of navigation bar
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        setWindowParams();
    }


    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < AppConstants.DOUBLE_CLICK_ESCPE_TIME) {
            return;
        } else {
            mLastClickTime = SystemClock.elapsedRealtime();
            selectedview = view;
            requestAppPermissions(PERMISSIONS, R.string.runtime_permissions_txt, MULTIPLE_PERMISSIONS);

//            if(allPermissionsAreA     ccepted) {

            switch (view.getId()) {
                case R.id.tvRegistration:
                    String validationMessage = getDataValidation();
                    if (validationMessage == null) {
                        setData();
                        hideKeyBoard(view);
//                            showLoaderDialog(null);
//                        showLoader();
                        new CommonBL(DeviceRegistrationActivity.this, DeviceRegistrationActivity.this).deviceRegistration(deviceRegistrationDO);
                    } else {
                        showpopup(validationMessage);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismissDialogbox();
                            }
                        }, 3000);
                    }


                    break;
                case R.id.tvClear:
                    clearAllFields();
                    break;
            }
//            }else {
//                requestAppPermissions(PERMISSIONS, R.string.runtime_permissions_txt, MULTIPLE_PERMISSIONS);
//
//            }
        }


    }

    private void setData() {
        deviceRegistrationDO.device_id = etDeviceId.getText().toString();
        deviceRegistrationDO.tab_model = etTableModel.getText().toString();
        deviceRegistrationDO.imei1 = etIMEI1.getText().toString();
        deviceRegistrationDO.imei2 = etIMEI2.getText().toString();
        deviceRegistrationDO.sim_no = etSimNum.getText().toString();
        deviceRegistrationDO.mobile_no = etMobileNumber.getText().toString();
        deviceRegistrationDO.registred_by = etEmpId.getText().toString();
        deviceRegistrationDO.freespace = etAvailbleMemory.getText().toString();

    }

    public void showpopup(String message) {
//        TextView tvTitle;
//        if (alertDialog != null && alertDialog.isShowing())
//            alertDialog.dismiss();
//        alertDialog = new Dialog(DeviceRegistrationActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//        alertDialog.setCanceledOnTouchOutside(true);
//        alertDialog.setCancelable(false);
//        final LinearLayout linearLayout = (LinearLayout) ((LayoutInflater)  getLayoutInflater()).inflate(R.layout.device_reg_success, null);
//        alertDialog.setContentView(linearLayout);
//        tvTitle=linearLayout.findViewById(R.id.tvSucessfullMsg);
//        if(message==null)
//            tvTitle.setText("Device ("+deviceRegistrationDO.device_id+") has been registered successfully");
//        else
//            tvTitle.setText(message+"");
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(alertDialog.getWindow().getAttributes());
//        if (!alertDialog.isShowing())
//            alertDialog.show();
//        alertDialog.getWindow().setAttributes(lp);
//        BaseActivity.setWindowParams(alertDialog.getWindow());

        if (message == null)
            showToast("Device (" + deviceRegistrationDO.device_id + ") has been registered successfully");
        else
            showToast(message + "");

    }

    public String getDataValidation() {
        if (TextUtils.isEmpty(etDeviceId.getText().toString())) {

            return "DeviceId is Empty";
        } else if (TextUtils.isEmpty(etTableModel.getText().toString()))
            return "TabletModel is Empty";
        else if (TextUtils.isEmpty(etIMEI1.getText().toString()))
            return "IMEI1 is Empty";
        else if (TextUtils.isEmpty(etIMEI2.getText().toString()))
            return "IMEI2 is Empty";
        else if (TextUtils.isEmpty(etSimNum.getText().toString()))
            return "SIM Number is Empty";
        else if (TextUtils.isEmpty(etMobileNumber.getText().toString()))
            return "Mobile Number is Empty";
        else if (TextUtils.isEmpty(etEmpId.getText().toString()))
            return "EmployeeId is Empty";
        else if (TextUtils.isEmpty(etAddress.getText().toString()))
            return "Address is Empty";
        else
            return null;

    }

    private void clearAllFields() {
        etDeviceId.setText("");
        etTableModel.setText("");
        etIMEI1.setText("");
        etIMEI2.setText("");
        etSimNum.setText("");
        etMobileNumber.setText("");
        etEmpId.setText("");
        etAvailbleMemory.setText("");
        etAddress.setText("");
    }

    private void setAllFields() {
        etDeviceId.setText(IMEI1);
        etTableModel.setText(IMEI2);
        etIMEI1.setText(IMEI1);
        etIMEI2.setText(IMEI2);
        etSimNum.setText(IMEI1);
        etMobileNumber.setText(IMEI2);
        etEmpId.setText("2");
        etAvailbleMemory.setText("2");
        etAddress.setText("2");

        etDeviceId.setFocusable(false);
        etTableModel.setFocusable(false);
        etIMEI1.setFocusable(false);
        etIMEI2.setFocusable(false);
        etSimNum.setFocusable(false);
        etMobileNumber.setFocusable(false);
    }


    public void dataRetrieved(Response dataResponse) {
//        hideLoaderDialog();
        if (dataResponse != null && dataResponse.method == ServiceMethods.WS_DEVICE_REGISTRATION) {
            if ((dataResponse.data != null) && (!(dataResponse.data instanceof String))) {
                ApiResponseAsObject response = (ApiResponseAsObject) dataResponse.data;
                if (response != null && response.getError() == false && response.getCode() == 200) {

                    preferenceUtils.saveString(PreferenceUtils.SELECTED_DEVICE_ID, deviceRegistrationDO.device_id);
                    preferenceUtils.saveBoolean(PreferenceUtils.IS_DEVICE_REGISTERED, true);
                    showpopup(null);
                    Intent intent = new Intent(DeviceRegistrationActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            dismissDialogbox();
                        }
                    }, 2000);
                } else {
                    showpopup(response.getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialogbox();
                        }
                    }, 3000);
                }
            }
        }

    }

    private void dismissDialogbox() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void setWindowParams() {
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hideNavBar();
            }

        });
    }

    private void hideNavBar() {

        if (Build.VERSION.SDK_INT >= 19) {
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void requestAppPermissions(final String[] requestedPermissions, final int stringId, final int requestCode) {
        mErrorString.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {

            permissionCheck = permissionCheck + PermissionChecker.checkSelfPermission(DeviceRegistrationActivity.this, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                Snackbar.make(findViewById(android.R.id.content), stringId, Snackbar.LENGTH_INDEFINITE).setAction("GRANT",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(DeviceRegistrationActivity.this, requestedPermissions, requestCode);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getdeviceId();
        setAllFields();
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        } else {
//            Snackbar.make(findViewById(android.R.id.content), mErrorString.get(requestCode),
//                    Snackbar.LENGTH_INDEFINITE).setAction("Enable",
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            intent.addCategory(Intent.CATEGORY_DEFAULT);
//                            intent.setData(Uri.parse("package:" + getPackageName()));
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                            startActivity(intent);
//                        }
//                    }).show();
        }
    }


    @Override
    public void dataRetreived(Response dataResponse) {
        //        hideLoaderDialog();
        if (dataResponse != null && dataResponse.method == ServiceMethods.WS_DEVICE_REGISTRATION) {
            if ((dataResponse.data != null) && (!(dataResponse.data instanceof String))) {
                ApiResponseAsObject response = (ApiResponseAsObject) dataResponse.data;
                if (response != null && response.getError() == false && response.getCode() == 200) {

                    preferenceUtils.saveString(PreferenceUtils.SELECTED_DEVICE_ID, deviceRegistrationDO.device_id);
                    preferenceUtils.saveBoolean(PreferenceUtils.IS_DEVICE_REGISTERED, true);
                    showpopup(null);
                    Intent intent = new Intent(DeviceRegistrationActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            dismissDialogbox();
                        }
                    }, 2000);
                } else {
                    showpopup(response.getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialogbox();
                        }
                    }, 3000);
                }
            }
        }
//        hideLoader();


    }
}
