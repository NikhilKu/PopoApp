package com.kumar.nikhil.popoapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     private TextView mTextMessage;
     private TabLayout mTabLayout;
     private ViewPager mViewPager;
     private ViewPagerAdapter mAdapter;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          mTextMessage = (TextView) findViewById(R.id.message);
          mTabLayout = findViewById(R.id.tabLayout);
          mViewPager = findViewById(R.id.viewPager);
          mAdapter =  new ViewPagerAdapter(getSupportFragmentManager());

          //Add fragment
          mAdapter.AddFragment(new FragmentChecks(), "Checks");
          mAdapter.AddFragment(new FragmentSettings(), "Settings");


          mViewPager.setAdapter(mAdapter);
          mTabLayout.setupWithViewPager(mViewPager);
     }

}
