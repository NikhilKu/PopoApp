package com.kumar.nikhil.popoapp;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentChecks extends Fragment {
     private View view;
     private RecyclerView mRecyclerView;
     private FloatingActionButton fab;
     private List<PoliceCheck> mCheckList;
     private ChecksAdapter mAdapter;

     @Override
     public void onCreate(@Nullable final Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          mCheckList = new ArrayList<>();
          mCheckList.add(new PoliceCheck('1', "Flitser: Amsterdam", "Locatie", new Date()));
          mCheckList.add(new PoliceCheck('1', "Dit is de titel", "Locatie", new Date()));
          mCheckList.add(new PoliceCheck('1', "Dit is de titel", "Locatie", new Date()));
     }

     public FragmentChecks() {
     }

     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.checks_fragment, container, false);
          mRecyclerView = view.findViewById(R.id.recyclerViewChecks);
          fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
          mAdapter = new ChecksAdapter(getContext(), mCheckList);
          mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
          mRecyclerView.setAdapter(mAdapter);

          fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    startActivity(new Intent(FragmentChecks.this.getActivity(), AddActivity.class));
               }
          });

          return view;

     }
}
