package com.kumar.nikhil.popoapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSettings extends Fragment {
     private View view;

     public FragmentSettings() {
     }

     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.settings_fragment, container, false);
          return view;
     }
}
