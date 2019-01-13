package com.kumar.nikhil.popoapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper {
     private Double latitude;
     private Double longitude;
     private Context context;
     private String city;
     private String streetname;

     private LocationManager mLocationManager;
     Location myLocation;

     public LocationHelper(Double latitude, Double longitude, Context context) {
          this.latitude = latitude;
          this.longitude = longitude;
          this.context = context;

          this.convertAddress();
          myLocation = getLastKnownLocation(context);
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


     /**
      * Convert latitude and longitude to a real address
      *
      * @return string of the address and city
      */
     public String convertAddress() {
          Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());

          try {
               List<android.location.Address> locations = geocoder.getFromLocation(this.latitude, this.longitude, 1);

               String address = locations.get(0).getAddressLine(0);
               this.streetname = address;
               String city = locations.get(0).getLocality();
               this.city = city;

               return "Adres: " + address + " \n Stad: " + city;
          } catch (Error e) {
               e.printStackTrace();
               return e.toString();
          } catch (IOException e) {
               e.printStackTrace();
               return e.toString();
          }
     }


     /**
      * Calculates the distance between current location and the check
      *
      * @return distance
      */
     public int CalculateDistance() {
          final int UNDEFINED_CODE = -3;
          final double CONST_DISTANCE = 6371.01;
          final double CONST_RAD = 180.0;

          Location currentLocation = myLocation;

          if (currentLocation == null) {
               return UNDEFINED_CODE;
          }

          double PI_RAD = Math.PI / CONST_RAD;

          double lat1 = currentLocation.getLatitude();
          double long1 = currentLocation.getLongitude();

          Location location = new Location("");
          location.setLatitude(lat1);
          location.setLongitude(long1);

          double lat2 = this.latitude;
          double long2 = this.longitude;

          double phi1 = lat1 * PI_RAD;
          double phi2 = lat2 * PI_RAD;
          double lam1 = long1 * PI_RAD;
          double lam2 = long2 * PI_RAD;

          return (int) (CONST_DISTANCE * Math.acos(Math.sin(phi1) * Math.sin(phi2) + Math.cos(phi1) * Math.cos(phi2) * Math.cos(lam2 - lam1)));
     }

     private Location getLastKnownLocation(Context Context) {
          mLocationManager = (LocationManager) Context.getSystemService(Context.LOCATION_SERVICE);
          List<String> providers = mLocationManager.getProviders(true);
          Location bestLocation = null;
          for (String provider : providers) {
               if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               }
               Location l = mLocationManager.getLastKnownLocation(provider);
               if (l == null) {
                    continue;
               }
               if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
               }
          }
          return bestLocation;
     }
}
