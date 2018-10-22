package com.kumar.nikhil.popoapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter{

     private final List<Fragment> listFragments = new ArrayList<>();
     private final List<String> listTitles = new ArrayList<>();

     @Override
     public int getCount() {
          return listFragments.size();
     }

     @Nullable
     @Override
     public CharSequence getPageTitle(int position) {
          return listTitles.get(position);
     }

     public ViewPagerAdapter(FragmentManager fm) {
          super(fm);
     }

     @Override
     public Fragment getItem(int position) {
          return listFragments.get(position);
     }



     public void AddFragment(Fragment fragment, String title){
          listFragments.add(fragment);
          listTitles.add(title);
     }
}
