package com.kumar.nikhil.popoapp.local;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.kumar.nikhil.popoapp.models.Settings;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class SettingsRepository {
     private AppDatabase mAppDatabase;
     private SettingDAO mSettingsDAO;
     private LiveData<List<Settings>> mSettings;
     private Executor mExecutor = Executors.newSingleThreadExecutor();
     private Settings mCurrentSettings;

     public SettingsRepository(Context context) {
          mAppDatabase = AppDatabase.getInstance(context);
          mSettingsDAO = mAppDatabase.settingDAO();
          mSettings = mSettingsDAO.getAllSettings();
          mCurrentSettings = mSettingsDAO.getCurrentSettings();
     }

     public LiveData<List<Settings>> getAllSettings() {
          return mSettings;
     }

     public Settings getmCurrentSettings() {
          return mCurrentSettings;
     }

     public void insert(final Settings setting) {
          mExecutor.execute(new Runnable() {
               @Override
               public void run() {
                    mSettingsDAO.insertSettings(setting);
               }
          });
     }

     public void update(final Settings mSettings) {
          mExecutor.execute(new Runnable() {
               @Override
               public void run() {
                    mSettingsDAO.updateSetting(mSettings);
               }
          });
     }

     public void delete(final Settings mSettings) {
          mExecutor.execute(new Runnable() {
               @Override
               public void run() {
                    mSettingsDAO.deleteSetting(mSettings);
               }
          });
     }


}
