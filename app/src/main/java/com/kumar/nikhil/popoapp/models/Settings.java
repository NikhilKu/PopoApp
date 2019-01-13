package com.kumar.nikhil.popoapp.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "settings")
public class Settings {

     @PrimaryKey(autoGenerate = true)
     private Long id;

     @ColumnInfo(name = "distance")
     private int distance;

     @ColumnInfo(name = "notification")
     private boolean notification;

     public Settings(int distance, boolean notification) {
          this.distance = distance;
          this.notification = notification;
     }

     public int getDistance() {
          return distance;
     }

     public void setDistance(int distance) {
          this.distance = distance;
     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public boolean isNotification() {
          return notification;
     }

     public void setNotification(boolean notification) {
          this.notification = notification;
     }

     @Override
     public String toString() {
          return isNotification() + "";
     }
}