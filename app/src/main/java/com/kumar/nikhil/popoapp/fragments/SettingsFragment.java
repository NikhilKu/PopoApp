package com.kumar.nikhil.popoapp.fragments;

import android.arch.lifecycle.Observer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.kumar.nikhil.popoapp.R;
import com.kumar.nikhil.popoapp.local.AppDatabase;
import com.kumar.nikhil.popoapp.local.MainViewModel;
import com.kumar.nikhil.popoapp.models.Settings;
import com.kumar.nikhil.popoapp.utils.NotificationHelper;

import java.util.List;

public class SettingsFragment extends Fragment  {
     private View view;
     static AppDatabase db;
     private SeekBar mSeekbar;
     private TextView mDistance;
     private Switch mNotification;
     private MainViewModel mMainViewModel;


     public SettingsFragment() {
     }

     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.fragment_settings, container, false);
          mSeekbar = view.findViewById(R.id.SeekBarKMFilter);
          mDistance = view.findViewById(R.id.TextViewFilteredDistance);
          mNotification = view.findViewById(R.id.switchNotification);
          mMainViewModel = new MainViewModel(getActivity().getApplicationContext());

          loadSettings();

          mNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                         NotificationHelper.enableNotifications();
                    } else {
                         NotificationHelper.disableNotifications();
                    }

                    Settings currentSettings = (Settings) mMainViewModel.getmCurrentSettings();
                    if (currentSettings != null){
                         currentSettings.setNotification(b);
                         mMainViewModel.update(currentSettings);
                    }
               }
          });

          mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    mDistance.setText(i + " KM");
                    Settings currentSettings = (Settings) mMainViewModel.getmCurrentSettings();
                    if (currentSettings != null) {
                         currentSettings.setDistance(i);
                         mMainViewModel.update(currentSettings);
                    }
               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
          });



          return view;
     }

     /**
      * Get usersettings from room trough the mainviewmodel
      */
     public void loadSettings(){

          mMainViewModel.getmSettings().observe(this, new Observer<List<Settings>>() {
               @Override
               public void onChanged(@Nullable List<Settings> settings) {
                    final int DEFAULT_DISTANCE = 99;
                    final boolean DEFAULT_NOTIFICATION_SETTING = true;

                    int listSize = settings.size();
                    if(listSize == 0 ){
                         //Size is 0 so make new settings
                         Settings defaultSettings = new Settings(DEFAULT_DISTANCE, DEFAULT_NOTIFICATION_SETTING);
                         mMainViewModel.insert(defaultSettings);
                    }

                    for (Settings setting : settings){
                         mDistance.setText(setting.getDistance() + " KM");
                         mSeekbar.setProgress(setting.getDistance());
                         mNotification.setChecked(setting.isNotification());
                    }
               }
          });
     }


}
