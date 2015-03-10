package com.mealordering.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mealordering.net.RoboSpiceService;
import com.octo.android.robospice.SpiceManager;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    private SpiceManager mSpiceManager = new SpiceManager(
            RoboSpiceService.class);

    @Override
    public void onStart() {
        mSpiceManager.start(getActivity());
        super.onStart();
//        Ln.e("onStart");
    }


    @Override
    public void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
//        Ln.e("onStop");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        Ln.e("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Ln.e("onCreate");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @Override
    public void onDetach() {
//        Ln.e("onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Ln.e("onDestroyView");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Ln.e("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
//        Ln.e("onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Ln.e("onDestroy");
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

}
