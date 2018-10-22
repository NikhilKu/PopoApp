package com.kumar.nikhil.popoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AddActivity extends AppCompatActivity {

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_add);
          Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          getSupportActionBar().setDisplayShowHomeEnabled(true);


          FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
          fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    Address address = new Address(52.372521, 4.856589, getBaseContext());
                    String city = address.getCity();
                    String streetname = address.getStreetname();

                    Snackbar.make(view, city + " Straat: " + streetname, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
               }
          });
     }

     @Override
     public boolean onSupportNavigateUp() {
          onBackPressed();
          return true;
     }

}
