package com.kumar.nikhil.popoapp.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kumar.nikhil.popoapp.models.PoliceCheck;


import java.util.ArrayList;


public class FirebaseHelper {

     DatabaseReference db;
     Boolean saved=null;
     ArrayList<PoliceCheck> policeChecks = new ArrayList<PoliceCheck>();


     public FirebaseHelper(DatabaseReference db) {
          this.db = db;
     }


     /**
      * @param PoliceCheck
      *
      * Add new policecheck to firebase
      *
      * @return boolean save status
      */
     public Boolean save(PoliceCheck PoliceCheck)
     {
          if(PoliceCheck==null)
          {
               saved=false;
          }else {

               try
               {
                    db.child("checks").push().setValue(PoliceCheck);
                    saved=true;
               }catch (DatabaseException e)
               {
                    e.printStackTrace();
                    saved=false;
               }

          }

          return saved;
     }


     /**
      * Read data
      *
      * @return police check
      */
     public ArrayList<PoliceCheck> retrieve()
     {
          db.addValueEventListener(new ValueEventListener() {


               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                    fetchData(dataSnapshot);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
          });

          return policeChecks;
     }

     /**
      * @param dataSnapshot
      *
      * Get all data from firebase
      *
      */
     private void fetchData(DataSnapshot dataSnapshot)
     {
          policeChecks.clear();

          Iterable<DataSnapshot> children = dataSnapshot.getChildren();

          for (DataSnapshot child: children) {
               PoliceCheck check = child.getValue(PoliceCheck.class);
               policeChecks.add(check);
          }
     }

}