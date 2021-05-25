package com.example.weatherapp_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp_test.adapter.LocationListAdapter;
import com.example.weatherapp_test.model.Lists;
import com.example.weatherapp_test.model.Main;
import com.example.weatherapp_test.network_call.ApiInterface;
import com.example.weatherapp_test.viewModel.LocationlistViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    public static String multipleLocCoordinates = "12,32,15,37,10";
    //public static String multipleLocCoordinates =

    private List<Lists> locationModelList;
    private List<Integer> mtempList = new ArrayList<>(); //for storing different temp of difft places

    private LocationListAdapter adapter;
    private LocationlistViewModel viewModel;

    TextView crrntCity,crrntDate,crrntCityTemp,minTemp,maxTemp,humidity;
    ImageView imageViewMnAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crrntCity = findViewById(R.id.currentCityTv);
        crrntDate = findViewById(R.id.currentDateTv);
        crrntCityTemp = findViewById(R.id.currentCityTempTv);
        imageViewMnAct = findViewById(R.id.imageMainActTv);
        minTemp =findViewById(R.id.minTempTv);
        maxTemp= findViewById(R.id.maxTempTv);
        humidity = findViewById(R.id.humidityTv);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //to show if api call fails
        final TextView noResultFound  = findViewById(R.id.noResult_Tv);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManager = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new LocationListAdapter(this,locationModelList);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(LocationlistViewModel.class);
        viewModel.getLocationListObservables().observe(this, new Observer<List<Lists>>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<Lists> locationModels) {
                if(locationModels != null){
                    Log.d("value : ",locationModels.toString());
                    locationModelList = locationModels ;
                    adapter.setLocationList(locationModelList);
                    crrntCity.setText(locationModels.get(0).getName());

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    String tarik = String.valueOf(dtf.format(now));
                    Log.d("Date :",tarik);
                    //converting date to day

                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM");
                    String stringDate = sdf.format(new Date());

                    crrntDate.setText(stringDate);
                    crrntCityTemp.setText(String.valueOf(locationModels.get(0).getMain().getFeelsLike().intValue())+"\u00B0");
                    minTemp.setText(String.valueOf(locationModels.get(0).getMain().getTempMin().intValue())+"\u00B0");
                    maxTemp.setText("/ "+String.valueOf(locationModels.get(0).getMain().getTempMax().intValue())+"\u00B0");
                    humidity.setText(locationModelList.get(0).getMain().getHumidity()+"\u00B0");
                    //current day
                   /* DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    String tarik = String.valueOf(dtf.format(now));
                    Log.d("Date :",tarik);
                    //converting date to day
                    //have to do
                    crrntDate.setText(tarik);
                   // minTemp.setText(locationModelList.get(1).getTempMin().intValue());
                    //maxTemp.setText(locationModelList.get(1).getTempMax().intValue());
                    humidity.setText(locationModelList.get(0).getHumidity()+"\u00B0");*/

                    noResultFound.setVisibility(View.GONE);
                }
                else
                {
                    Log.d("error : ",viewModel.toString());
                    noResultFound.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.makeApiCall();
    }

}