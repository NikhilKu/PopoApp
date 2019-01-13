package com.kumar.nikhil.popoapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.kumar.nikhil.popoapp.fragments.ChecksFragment;
import com.kumar.nikhil.popoapp.fragments.MapsFragment;
import com.kumar.nikhil.popoapp.fragments.SettingsFragment;
import com.kumar.nikhil.popoapp.R;
import com.kumar.nikhil.popoapp.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity{

     private TextView mTextMessage;
     private ViewPager mViewPager;
     private ViewPagerAdapter mAdapter;
     private ActionBar mToolbar;
     private final int PERMISSION_REQUEST_CODE = 1;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          checkLocationPermission();

          mTextMessage   = findViewById(R.id.message);
          mViewPager     = findViewById(R.id.viewPager);
          mAdapter       =  new ViewPagerAdapter(getSupportFragmentManager());

          //Add fragments to the adapter.
          mAdapter.AddFragment(new ChecksFragment(), "Checks");
          mAdapter.AddFragment(new MapsFragment(), "Maps");
          mAdapter.AddFragment(new SettingsFragment(), "Settings");

          mViewPager.setAdapter(mAdapter);
          mToolbar = getSupportActionBar();
          mToolbar.setTitle("PopoApp - Home");

          final BottomNavigationView navigation =  findViewById(R.id.bottomNavigationView);
          navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


          mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
               @Override
               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

               }

               @Override
               public void onPageSelected(int position) {
                    navigation.getMenu().getItem(position).setChecked(true);
               }

               @Override
               public void onPageScrollStateChanged(int state) {

               }
          });



     }

     private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
             = new BottomNavigationView.OnNavigationItemSelectedListener() {

          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               final int NAVIGATION_HOME_ID       = 0;
               final int NAVIGATION_MAPS_ID       = 1;
               final int NAVIGATION_SETTINGS_ID   = 2;

               switch (item.getItemId()) {

                    case R.id.navigation_home:
                         mToolbar.setTitle("PopoApp - Home");
                         mViewPager.setCurrentItem(NAVIGATION_HOME_ID);
                         return true;
                    case R.id.navigaiton_maps:
                         mToolbar.setTitle("PopoApp - Maps");
                         mViewPager.setCurrentItem(NAVIGATION_MAPS_ID);
                         return true;
                    case R.id.navigation_settings:
                         mToolbar.setTitle("PopoApp - Settings");
                         mViewPager.setCurrentItem(NAVIGATION_SETTINGS_ID);
                         return true;

               }
               return false;
          }
     };


     /**
      * Checks if the user has turned on the location permission.
      */
     private void checkLocationPermission() {
          if(ContextCompat.checkSelfPermission(this,   Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
               requestLocationPermission();
          }
     }


     /**
      * Requests user to turn on the location permission
      */
     public void requestLocationPermission() {
          ActivityCompat.requestPermissions(this, new String[]{
                  Manifest.permission.ACCESS_FINE_LOCATION,
                  Manifest.permission.ACCESS_COARSE_LOCATION
                  }, PERMISSION_REQUEST_CODE);
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          if (requestCode == PERMISSION_REQUEST_CODE)  {
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

                    //Refresh current activity to show distance.
                    this.recreate();
               } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                    requestLocationPermission();
               }
          }
     }

}
