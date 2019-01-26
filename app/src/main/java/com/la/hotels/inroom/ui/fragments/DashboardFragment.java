package com.la.hotels.inroom.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.la.hotels.inroom.R;
import com.la.hotels.inroom.utils.LogUtils;

public class DashboardFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stay_services_fragment, container, false);
        initControls();
        return view;
    }

    private void initControls() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.printMessage("OnCreated ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.printMessage("onDestroyed ");
    }
}
