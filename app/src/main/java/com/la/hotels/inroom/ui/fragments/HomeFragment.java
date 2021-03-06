package com.la.hotels.inroom.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.la.hotels.inroom.R;
import com.la.hotels.inroom.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment  implements View.OnClickListener {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_layout, container, false);
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
    @Override
    public void onClick(View view) {

    }
}
