package com.kumar.nikhil.popoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumar.nikhil.popoapp.R;
import com.kumar.nikhil.popoapp.local.MainViewModel;
import com.kumar.nikhil.popoapp.models.PoliceCheck;

import java.util.List;

public class ChecksAdapter extends RecyclerView.Adapter<ChecksAdapter.ViewHolder> {

     private Context mContext;
     private List<PoliceCheck> mData;
     private final OnItemClickListener mListener;
     private MainViewModel mMainViewModel;

     public interface OnItemClickListener {
          void onItemClick(int i);
     }

     public ChecksAdapter(Context mContext, List<PoliceCheck> mData, OnItemClickListener mListener) {
          this.mContext = mContext;
          this.mData = mData;
          this.mListener = mListener;
     }

     public class ViewHolder extends RecyclerView.ViewHolder{
          private TextView mTitle;
          private TextView mDate;
          private TextView mDistance;
          private TextView mCity;
          private ImageView mIcon;

          public ViewHolder(View itemView) {
               super(itemView);
               mTitle = (TextView) itemView.findViewById(R.id.textViewType);
               mDate = (TextView) itemView.findViewById(R.id.textViewDate);
               mCity = (TextView) itemView.findViewById(R.id.textViewCity);
               mDistance = (TextView) itemView.findViewById(R.id.textViewKM);
               mIcon = (ImageView) itemView.findViewById(R.id.imageViewIcon);
          }


          public void bind(final int i, final OnItemClickListener listener) {
               itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                         listener.onItemClick(i);
                    }
               });
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
          PoliceCheck check = mData.get(position);
          final int UNDEFINED_CODE = -3;

          int distance = check.getLocation(mContext).CalculateDistance();

          holder.bind(position, mListener);

          holder.mTitle.setText(check.getType());
          holder.mDate.setText(check.getFormatDate());

          holder.mDistance.setText(distance == UNDEFINED_CODE ? "? KM" : distance + " KM");
          holder.mCity.setText(check.getCity(mContext));

          if(check.isScooter()){
               holder.mIcon.setImageResource(R.drawable.ic_scooter_front_view);
          }
     }

     @Override
     public int getItemCount() {
          return mData.size();
     }

}
