package com.kumar.nikhil.popoapp.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kumar.nikhil.popoapp.models.Settings;

import java.util.List;

@Dao
public interface SettingDAO {
     @Query("SELECT * FROM settings")
     public LiveData<List<Settings>> getAllSettings();

     @Insert
     public void insertSettings(Settings mSettings);

     @Update
     public void updateSetting(Settings mSettings);

     @Delete
     public void deleteSetting(Settings mSettings);

     //There will always be one setting.
     @Query("SELECT * FROM settings WHERE id = 1")
     public Settings getCurrentSettings();
}