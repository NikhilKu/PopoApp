package com.kumar.nikhil.popoapp.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kumar.nikhil.popoapp.models.Settings;

@Database(entities = Settings.class, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

     public abstract SettingDAO settingDAO();
     private final static String NAME_DATABASE = "PopoApp_DB";
     private static AppDatabase mInstance;

     public static AppDatabase getInstance(Context context) {
          if(mInstance == null) {
               mInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE).allowMainThreadQueries().fallbackToDestructiveMigration().build();
          }
          return mInstance;
     }
}