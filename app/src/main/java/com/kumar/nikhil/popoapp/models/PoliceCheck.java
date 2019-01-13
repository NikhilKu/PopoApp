package com.kumar.nikhil.popoapp.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.kumar.nikhil.popoapp.utils.LocationHelper;

import java.text.SimpleDateFormat;

public class PoliceCheck implements Parcelable {

     private String type;
     private String information;
     private double longitude;
     private double lattitude;
     private Long dateAdded;

     public PoliceCheck() {

     }

     public PoliceCheck(String type, String information, double longitude, double lattitude, Long dateAdded) {
          this.type = type;
          this.information = information;
          this.longitude = longitude;
          this.lattitude = lattitude;
          this.dateAdded = dateAdded;
     }

     public String getType() {
          return type;
     }

     public void setType(String type) {
          this.type = type;
     }

     public String getInformation() {
          return information;
     }

     public void setInformation(String information) {
          this.information = information;
     }

     public double getLongitude() {
          return longitude;
     }

     public void setLongitude(double longitude) {
          this.longitude = longitude;
     }

     public double getLattitude() {
          return lattitude;
     }

     public void setLattitude(double lattitude) {
          this.lattitude = lattitude;
     }

     public Long getDateAdded() {
          return dateAdded;
     }

     public void setDateAdded(Long dateAdded) {
          this.dateAdded = dateAdded;
     }

     @Override
     public String toString() {
          return this.getType();
     }

     public String getCity(Context mContext) {
          return this.getLocation(mContext).getCity();
     }

     public String getAdress(Context mContext) {
          return this.getLocation(mContext).getStreetname();
     }

     public LocationHelper getLocation(Context mContext) {
          double lattitude = this.getLattitude();
          double longitude = this.getLongitude();

          LocationHelper location = new LocationHelper(lattitude, longitude, mContext);

          return location;
     }

     public boolean isScooter() {
          if (this.getType().toLowerCase().contains("scooter")) {
               return true;
          }
          return false;
     }

     public String getFormatDate(){
          SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
          return format.format(this.getDateAdded());
     }

     @Override
     public int describeContents() {
          return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(this.type);
          dest.writeString(this.information);
          dest.writeDouble(this.longitude);
          dest.writeDouble(this.lattitude);
          dest.writeValue(this.dateAdded);
     }

     protected PoliceCheck(Parcel in) {
          this.type = in.readString();
          this.information = in.readString();
          this.longitude = in.readDouble();
          this.lattitude = in.readDouble();
          this.dateAdded = (Long) in.readValue(Long.class.getClassLoader());
     }

     public static final Creator<PoliceCheck> CREATOR = new Creator<PoliceCheck>() {
          @Override
          public PoliceCheck createFromParcel(Parcel source) {
               return new PoliceCheck(source);
          }

          @Override
          public PoliceCheck[] newArray(int size) {
               return new PoliceCheck[size];
          }
     };
}

