package com.kumar.nikhil.popoapp;

import java.util.Date;

public class PoliceCheck {
     private int id;
     private String title;
     private String location;
     private Date dateAdded;

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public String getLocation() {
          return location;
     }

     public void setLocation(String location) {
          this.location = location;
     }

     public Date getDateAdded() {
          return dateAdded;
     }

     public void setDateAdded(Date dateAdded) {
          this.dateAdded = dateAdded;
     }

     public PoliceCheck(int id, String title, String location, Date dateAdded) {

          this.id = id;
          this.title = title;
          this.location = location;
          this.dateAdded = dateAdded;
     }
}
