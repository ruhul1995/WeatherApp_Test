package com.example.weatherapp_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp_test.R;
import com.example.weatherapp_test.model.Lists;
import com.example.weatherapp_test.model.Main;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {
    private Context context;
    private List<Lists> locationList;


    public LocationListAdapter(Context context, List<Lists> locationList) {
        this.context = context;
        this.locationList = locationList;

    }

    //for modifying the data
    public void setLocationList(List<Lists> locationList) {
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.ViewHolder holder, int position) {

        Lists obj = locationList.get(position);
        holder.mCityNameTv.setText(obj.getName());
        holder.mCityMinTempTv.setText(String.valueOf(obj.getMain().getTempMin().intValue())+"\u00B0");
        holder.mCityMaxTempTv.setText("/ "+String.valueOf(obj.getMain().getTempMax().intValue())+"\u00B0");
    }

    @Override
    public int getItemCount() {
        if (locationList != null)
            return locationList.size();

        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public static TextView mCityNameTv, mCityMinTempTv,mCityMaxTempTv ;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewIv);
            mCityNameTv = itemView.findViewById(R.id.cityNameTv);
            mCityMinTempTv = itemView.findViewById(R.id.cityMinTempTv);
            mCityMaxTempTv = itemView.findViewById(R.id.cityMaxTempTv);
        }
    }
}
