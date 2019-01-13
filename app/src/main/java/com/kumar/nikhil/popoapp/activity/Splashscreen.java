package com.kumar.nikhil.popoapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kumar.nikhil.popoapp.R;

import gr.net.maroulis.library.EasySplashScreen;

public class Splashscreen extends AppCompatActivity {

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          final int TIMEOUT_TIME = 500;

          EasySplashScreen ESConfig = new EasySplashScreen(Splashscreen.this)
                  .withFullScreen()
                  .withTargetActivity(MainActivity.class)
                  .withSplashTimeOut(TIMEOUT_TIME)
                  .withBackgroundColor(getResources().getColor(R.color.colorYellow))
                  .withLogo(R.mipmap.ic_launcher)
                  .withFooterText("Copyrights 2018 - Nikhil Kumar")
                  ;

          View view = ESConfig.create(); //Create splashscreen.
          setContentView(view);

     }
}
