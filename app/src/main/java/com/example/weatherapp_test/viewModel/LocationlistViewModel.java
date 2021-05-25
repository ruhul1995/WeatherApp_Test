package com.example.weatherapp_test.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp_test.MainActivity;
import com.example.weatherapp_test.model.Example;
import com.example.weatherapp_test.model.Lists;
import com.example.weatherapp_test.model.Main;
import com.example.weatherapp_test.network_call.ApiInterface;
import com.example.weatherapp_test.network_call.RetrofitInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationlistViewModel extends ViewModel {
    private MutableLiveData<List<Lists>> locationList;

    public LocationlistViewModel()
    {
        locationList = new MutableLiveData<>();

    }
    public MutableLiveData<List<Lists>> getLocationListObservables()
    {
        return locationList;
    }

    public void makeApiCall()
    {
        ApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<Example> call = apiInterface.getResults(MainActivity.multipleLocCoordinates,"a8a6cc378267083c871444e1573029b7");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Log.d("response : ",response.body().toString());
                Example resource = response.body();
                //baki ka banana hain

                List<Lists>mylist = resource.getList();
                mylist.get(0).getMain().getTempMax();





                locationList.postValue(mylist);
                //myTempList.postValue(tempList);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("error : ","Api call was not successfull"+call.toString());
                locationList.postValue(null);
                //myTempList.postValue(null);
            }
        });
    }
}
