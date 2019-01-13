package com.kumar.nikhil.popoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kumar.nikhil.popoapp.models.PoliceCheck;
import com.kumar.nikhil.popoapp.R;

import java.util.ArrayList;


public class AddActivity extends AppCompatActivity {

     private EditText mInformation;
     private Spinner mType;
     private Button mSelectPlaceButton;
     private Double mLongitude;
     private Double mLatitude;
     private DatabaseReference mDatabaseRef;

     final int PLACE_PICKER_REQUEST = 1;



     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_add);

          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          getSupportActionBar().setDisplayShowHomeEnabled(true);

          mSelectPlaceButton = findViewById(R.id.SelectBtn);
          mSelectPlaceButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    openPlacePicker();
               }
          });


          FloatingActionButton addButton = findViewById(R.id.fab);
          addButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    mInformation = findViewById(R.id.EditTextInformation);
                    mType = findViewById(R.id.SpinnerType);

                    if(!validateForm()){
                        return;
                    }

                    PoliceCheck check = new PoliceCheck(
                            mType.getSelectedItem().toString(),
                            mInformation.getText().toString(),
                            mLongitude,
                            mLatitude,
                            System.currentTimeMillis()
                    );

                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("checks");
                    String id = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(id).setValue(check);

                    finish();
                    Snackbar.make(view, "New police check added", Snackbar.LENGTH_LONG).show();
               }
          });
     }


     /**
      *
      *  Validates the form to detect empty fields
      *  If a field is empty it shows the error.
      *
      * @return boolean form is validated or not
      */
     private boolean validateForm() {
          ArrayList<String> errors = new ArrayList<String>();
          final int MINUMUM_DESCRIPTION_LENGTH = 3;

          if(mInformation.getText().length() < MINUMUM_DESCRIPTION_LENGTH){
               errors.add(String.format("Please add atleast %s characters to the description", MINUMUM_DESCRIPTION_LENGTH));
          }

          if(mLongitude == null || mLatitude == null){
               errors.add("Location is required");
          }

          if(errors.size() == 0 ){
               return true;
          }else{
               String message = "";
               int i = 0;
               for (String e : errors){
                    message = i == 0 ? e : (message + " & " + e);
                    i++;
               }
               Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
               return false;
          }

     }

     @Override
     public boolean onSupportNavigateUp() {
          onBackPressed();
          return true;
     }

     /**
      * Opens a new intent with a placepicker.
      */
     private void openPlacePicker(){
          PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
          try {
               startActivityForResult(builder.build(AddActivity.this), PLACE_PICKER_REQUEST);
          } catch (GooglePlayServicesRepairableException e) {
               e.printStackTrace();
               Log.d("error - placepicker", e + "");
          } catch (GooglePlayServicesNotAvailableException e) {
               e.printStackTrace();
               Log.d("error - placepicker", e + "");
          }
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (requestCode == PLACE_PICKER_REQUEST) {
               if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(data, this);
                    String toastMsg = String.format("Place: %s", place.getName());
                    this.mLatitude = place.getLatLng().latitude;
                    this.mLongitude = place.getLatLng().longitude;

                    mSelectPlaceButton.setText(place.getAddress().toString());
                    mSelectPlaceButton.setTextColor(getResources().getColor(R.color.colorYellow));
               }
          }
     }
}
