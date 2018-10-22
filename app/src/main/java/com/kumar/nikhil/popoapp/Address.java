package com.kumar.nikhil.popoapp;

import android.content.Context;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Address {
     private Double latitude;
     private Double longitude;
     private Context context;
     private String city;
     private String streetname;

     public Address(Double latitude, Double longitude, Context context) {
          this.latitude = latitude;
          this.longitude = longitude;
          this.context = context;

          this.convertAddress();
     }

     public Double getLatitude() {
          return latitude;
     }

     public void setLatitude(Double latitude) {
          this.latitude = latitude;
     }

     public Double getLongitude() {
          return longitude;
     }

     public void setLongitude(Double longitude) {
          this.longitude = longitude;
     }

     public Context getContext() {
          return context;
     }

     public void setContext(Context context) {
          this.context = context;
     }

     public String getCity() {
          return city;
     }

     public void setCity(String city) {
          this.city = city;
     }

     public String getStreetname() {
          return streetname;
     }

     public void setStreetname(String streetname) {
          this.streetname = streetname;
     }

     public String convertAddress(){
          Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());

          try{
               List<android.location.Address> locations = geocoder.getFromLocation(this.latitude, this.longitude, 1);
               String address = locations.get(0).getAddressLine(0);
               this.streetname = address;
               String city = locations.get(0).getLocality();
               this.city = city;

               return  "Adres: " + address + " \n Stad: " +  city;
          }catch (Error e){
               System.out.println(e);
               return e.toString();
          } catch (IOException e) {
               e.printStackTrace();
               return e.toString();
          }
     }


}
