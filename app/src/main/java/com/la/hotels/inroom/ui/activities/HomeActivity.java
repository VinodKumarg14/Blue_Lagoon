package com.la.hotels.inroom.ui.activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.la.hotels.inroom.R;
import com.la.hotels.inroom.db.database.DatabaseHelper;
import com.la.hotels.inroom.networks.businesslayer.DataListener;
import com.la.hotels.inroom.networks.webaccess.Response;


public class HomeActivity extends BaseActivity implements View.OnClickListener , DataListener
{
    private RelativeLayout llHome;

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
//        tvTime				    = (TextView)llHome.findViewById(R.id.tvTime);
//        FontUtils.applyTypeface(tvOrders,FontUtils.getTypeface(HomeActivity.this,FontUtils.ALLER_REGULAR));
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
        }
    }


    @Override
    public void dataRetreived(Response data)
    {


    }

}
