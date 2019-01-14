package com.kumar.nikhil.popoapp.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kumar.nikhil.popoapp.activity.PoliceCheckActivity;
import com.kumar.nikhil.popoapp.local.MainViewModel;
import com.kumar.nikhil.popoapp.models.Settings;
import com.kumar.nikhil.popoapp.adapters.ChecksAdapter;
import com.kumar.nikhil.popoapp.models.PoliceCheck;
import com.kumar.nikhil.popoapp.R;
import com.kumar.nikhil.popoapp.activity.AddActivity;

import java.util.ArrayList;
import java.util.Collections;


public class ChecksFragment extends Fragment implements ChecksAdapter.OnItemClickListener{
     private View view;
     private DatabaseReference mDatabaseReference;
     private RecyclerView mRecyclerView;
     private ChecksAdapter mAdapter;
     private ArrayList<PoliceCheck> mPoliceChecks = new ArrayList<PoliceCheck>();
     private MainViewModel mMainViewModel;

     //For intent
     public static final String EXTRA_POLICECHECK = "Check";
     public static final int REQUESTCODE = 1234;
     private int mModifyPosition;

     public ChecksFragment() {
     }

     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.fragment_checks, container, false);
          final long ONE_DAY = 1000L * 60L * 60L * 24L;


         //setup RecyclerView
          mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChecks);
          mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
          mAdapter = new ChecksAdapter(getContext(), mPoliceChecks, this);

           //setup FireBase
          mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("checks");

          //query
          Long before24Hour =   (Long) System.currentTimeMillis() - ONE_DAY; //get only data of 24 hours or ago.
          Query mDBQuery = mDatabaseReference.orderByChild("dateAdded").startAt(before24Hour);




          //Get checks
          mDBQuery.addValueEventListener(new ValueEventListener() {
               /**
                * @param dataSnapshot
                *
                * Empty the list first and after that refill the list with the data
                * and also filter on settings.
                */
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {

                    mPoliceChecks.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child: children) {
                         //Get firebase data and place it in the local array.
                         PoliceCheck check = child.getValue(PoliceCheck.class);
                         int distance = check.getLocation(getContext()).CalculateDistance();
                         if (filterOnDistance(distance)){
                              mPoliceChecks.add(check);
                         }

                    }

                    //Sort descending and update data
                    Collections.reverse(mPoliceChecks);
                    mAdapter.notifyDataSetChanged();

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
          });

          mRecyclerView.setAdapter(mAdapter);

          FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
          fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    startActivity(new Intent(getContext(), AddActivity.class));
               }
          });

          return view;
     }


     @Override
     public void onItemClick(int i) {
          Intent intent = new Intent(getContext(), PoliceCheckActivity.class);
          mModifyPosition = i;
          intent.putExtra(EXTRA_POLICECHECK,  mPoliceChecks.get(i));
          startActivityForResult(intent, REQUESTCODE);
     }

     /**
      * @param distance
      *
      * Check the distance of the check is lower than the maximum
      * distance in the settings.
      *
      * @return boolean should placed or not in the list
      */
     public boolean filterOnDistance(int distance){
          mMainViewModel = new MainViewModel(getContext());
          Settings currentSettings = (Settings) mMainViewModel.getmCurrentSettings();
          if (currentSettings == null) return true;
          int distanceSetting = (int) currentSettings.getDistance();
          if(distanceSetting == 0) return true;

          if(distance > distanceSetting) {
               return false;
          } else{
               return true;
          }
     }
}
