package com.kumar.nikhil.popoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ChecksAdapter extends RecyclerView.Adapter<ChecksAdapter.ViewHolder> {

     private Context mContext;
     private List<PoliceCheck> mData;

     public ChecksAdapter(Context mContext, List<PoliceCheck> mData) {
          this.mContext = mContext;
          this.mData = mData;
     }

     public static class ViewHolder extends RecyclerView.ViewHolder{
          private TextView mTitle;
          private TextView mDate;

          public ViewHolder(View itemView) {
               super(itemView);
               mTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
               mDate  = (TextView) itemView.findViewById(R.id.textViewDate);
          }
     }

     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v;
          v = LayoutInflater.from(mContext).inflate(R.layout.checks_cell_layout, parent, false);
          ViewHolder vHolder = new ViewHolder(v);

          return vHolder;
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.mTitle.setText(mData.get(position).getTitle());
          holder.mDate.setText(mData.get(position).getDateAdded().toString());

     }


     @Override
     public int getItemCount() {
          return mData.size();
     }
}
