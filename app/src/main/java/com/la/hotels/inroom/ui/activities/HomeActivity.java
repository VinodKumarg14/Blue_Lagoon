package com.la.hotels.inroom.ui.activities;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.la.hotels.inroom.R;
import com.la.hotels.inroom.constants.AppConstants;
import com.la.hotels.inroom.db.database.DatabaseHelper;
import com.la.hotels.inroom.networks.businesslayer.DataListener;
import com.la.hotels.inroom.networks.webaccess.Response;
import com.la.hotels.inroom.ui.fragments.DashboardFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HomeActivity extends BaseActivity implements View.OnClickListener , DataListener
{
    private RelativeLayout llHome;
    private TextView tvHello;

    @Override
    public void initialize()
    {
        llHome			 		 = 	(RelativeLayout) inflater.inflate(R.layout.activity_main, null);
        llBody.addView(llHome, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        createDataBase();
        initializeControls();
    }

    private void createDataBase()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);
        try
        {
            dbHelper.createDataBase();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
    private void initializeControls()
    {
        tvHello=findViewById(R.id.tvServices);
        tvHello.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tvServices:
                onHomeServiceSelected(AppConstants.STAY);
                break;
        }
    }
    private void onHomeServiceSelected(String serviceName){
        switch (serviceName){
            case AppConstants.STAY:
                loadStayFragment();
                break;
        }
    }

    private void loadStayFragment() {
        loadFragment(new DashboardFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        if (fragment!=null){
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.rlDashboard,fragment);
            transaction.commit();
        }
    }

    @Override
    public void dataRetreived(Response data) {

    }
}
