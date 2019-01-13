package com.kumar.nikhil.popoapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kumar.nikhil.popoapp.R;
import com.kumar.nikhil.popoapp.fragments.ChecksFragment;
import com.kumar.nikhil.popoapp.models.PoliceCheck;

public class PoliceCheckActivity extends AppCompatActivity
        implements OnMapReadyCallback {

     private Toolbar toolbar;
     private PoliceCheck policeCheck;
     private TextView mType;
     private TextView mDate;
     private TextView mCity;
     private ImageView mIcon;
     private TextView mDistance;
     private TextView mDescription;
     private TextView mAdress;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_police_check);
          toolbar = (Toolbar) findViewById(R.id.toolbar);



          policeCheck = getIntent().getParcelableExtra(ChecksFragment.EXTRA_POLICECHECK);

          if(toolbar != null) {
               setSupportActionBar(toolbar);
               getSupportActionBar().setTitle(policeCheck.getType());
               getSupportActionBar().setHomeButtonEnabled(true);
               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          }

          mType = (TextView) findViewById(R.id.textViewType);
          mDate = (TextView) findViewById(R.id.textViewDate);
          mCity = (TextView) findViewById(R.id.textViewCity);
          mDistance = (TextView) findViewById(R.id.textViewKM);
          mIcon = (ImageView) findViewById(R.id.imageViewIcon);
          mDescription = (TextView) findViewById(R.id.textViewDescription);
          mAdress = (TextView) findViewById(R.id.textViewAdress);

          int distance = policeCheck.getLocation(getBaseContext()).CalculateDistance();

          mType.setText(policeCheck.getType());
          mDate.setText(policeCheck.getFormatDate());
          mCity.setText(policeCheck.getCity(getBaseContext()));
          mDistance.setText(getDistanceString(distance));
          mDescription.setText(policeCheck.getInformation());
          mAdress.setText(policeCheck.getAdress(getBaseContext()));

          if(policeCheck.isScooter()) {
               mIcon.setImageResource(R.drawable.ic_scooter_front_view);
          }

          SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                  .findFragmentById(R.id.mapView);
          mapFragment.getMapAsync(this);

     }

     /**
      * @param distance to convert to string
      * @return distance in string with added KM.
      */
     public String getDistanceString(int distance){
          final int UNDEFINED_CODE = -3;
          return distance == UNDEFINED_CODE ? "? KM" : distance + " KM";
     }

     /**
      * @param googleMap the map where to put the marker on.
      *
      * Add new marker on the map.
      */
     @Override
     public void onMapReady(GoogleMap googleMap) {
          final float ZOOM_IN_LEVEL = 16.0f;
          LatLng checkLatLng = new LatLng(policeCheck.getLattitude(), policeCheck.getLongitude());
          googleMap.addMarker(new MarkerOptions().position(checkLatLng));
          googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(checkLatLng, ZOOM_IN_LEVEL));
     }

     @Override
     public boolean onSupportNavigateUp() {
          onBackPressed();
          return true;
     }
}
