package com.kumar.nikhil.popoapp.utils;

import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationHelper {
     public static void disableNotifications(){
          FirebaseMessaging.getInstance().unsubscribeFromTopic("APP");
     }

     public static void enableNotifications(){
          FirebaseMessaging.getInstance().subscribeToTopic("APP");
     }
}
