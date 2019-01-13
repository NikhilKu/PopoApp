package com.kumar.nikhil.popoapp.local;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.kumar.nikhil.popoapp.models.Settings;

import java.util.List;

public class MainViewModel {
     private SettingsRepository mRepository;
     private LiveData<List<Settings>> mSettings;
     private Settings mCurrentSettings;

     public MainViewModel(Context context) {
          mRepository = new SettingsRepository(context);
          mSettings = mRepository.getAllSettings();
          mCurrentSettings = mRepository.getmCurrentSettings();
     }

     public LiveData<List<Settings>> getmSettings() {
          return mSettings;
     }

     public Settings getmCurrentSettings() {
          return mCurrentSettings;
     }

     public void insert(Settings mSetting) {
          mRepository.insert(mSetting);
     }

     public void update(Settings mSetting) {
          mRepository.update(mSetting);
     }

     public void delete(Settings mSetting) {
          mRepository.delete(mSetting);
     }


}

