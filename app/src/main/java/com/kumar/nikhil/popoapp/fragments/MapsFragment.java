package com.kumar.nikhil.popoapp.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kumar.nikhil.popoapp.R;
import com.kumar.nikhil.popoapp.models.PoliceCheck;

import java.util.ArrayList;
import java.util.List;


public class MapsFragment extends Fragment
        implements OnMapReadyCallback {

     private FragmentActivity myContext;
     private DatabaseReference mDatabaseReference;
     private List<PoliceCheck> mPoliceChecks = new ArrayList<PoliceCheck>();
     private  Query mDBQuery;

     public MapsFragment() {
          // Required empty public constructor
     }


     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

          View view=  inflater.inflate(R.layout.fragment_maps, container, false);
          SupportMapFragment mapFragment = (SupportMapFragment)
                  this.getChildFragmentManager()
                          .findFragmentById(R.id.mapView);
          mapFragment.getMapAsync(this);

          //SETUP FB
          mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("checks");

          //QUERY
          final long ONE_DAY = 1000L * 60L * 60L * 24L;
          Long before24Hour =   (Long) System.currentTimeMillis() - ONE_DAY;
          mDBQuery = mDatabaseReference.orderByChild("dateAdded").startAt(before24Hour);

          return view;
     }


     @Override
     public void onMapReady(final GoogleMap googleMap) {
          mDBQuery.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                    mPoliceChecks.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child: children) {
                         //Get firebase data and place it in the local array.
                         PoliceCheck check = child.getValue(PoliceCheck.class);
                         mPoliceChecks.add(check);

                         LatLng policeCheckLatLng = new LatLng(check.getLattitude(), check.getLongitude());
                         int icon = 0;

                         //Get the correct icon.
                         icon = (check.isScooter()) ?
                                 R.drawable.ic_scooter_front_view : //if is a scooter check
                                 R.drawable.ic_directions_car_black_24dp; //if is a car check


                         googleMap.addMarker(
                                 new MarkerOptions()
                                         .title(check.getType() + " - " + check.getInformation())
                                         .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(icon)))
                                         .position(policeCheckLatLng)
                         );
                    }

                    final double NL_LAT = 52.370216;
                    final double NL_LNG = 4.895168;
                    final float ZOOM_LEVEL = 7.0f;
                    final LatLng netherlands = new LatLng(NL_LAT, NL_LNG);

                    //Zoom in to the Netherlands at the zoomlevel.
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(netherlands, ZOOM_LEVEL));


               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
          });




     }


     @Override
     public void onAttach(Activity activity) {
          myContext=(FragmentActivity) activity;
          super.onAttach(activity);
     }


     /**
      * @param drawableRes
      *
      * Convert drawable to bitmap and change color of the drawable.
      *
      * @return bitmap
      */
     private Bitmap getBitmap(int drawableRes) {
          Drawable drawable = getResources().getDrawable(drawableRes);
          drawable.setTint(getContext().getResources().getColor(R.color.colorPrimary));
          Canvas canvas = new Canvas();
          Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
          canvas.setBitmap(bitmap);
          drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
          drawable.draw(canvas);

          return bitmap;
     }

}
