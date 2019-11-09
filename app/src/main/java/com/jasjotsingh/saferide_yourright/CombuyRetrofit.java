package com.jasjotsingh.saferide_yourright;

import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CombuyRetrofit{

    private static final String URL_APICOMBUY="https://combuyapi.herokuapp.com/";
    List<CombuyLocal> listLocal = null;
    CombuyClient api;

    public CombuyRetrofit() {

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_APICOMBUY)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(CombuyClient.class);

    }

    public List<CombuyLocal> getListLocal(){

        try {
            final Call<List<CombuyLocal>> callsync= api.getListLocales();
            Response<List<CombuyLocal>> response=callsync.execute();
            listLocal=response.body();
            if(response.code()==200){
                Log.v("RETRO","Response Satisfed!");
            }else{
                Log.v("RETRO","Failed U.U");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listLocal;
    }
    public List<CombuyLocal> getLocalesProducto(String producto){
        try {
            final Call<List<CombuyLocal>> callsync= api.getLocalesProducto(producto);
            Response<List<CombuyLocal>> response=callsync.execute();
            listLocal=response.body();
            if(response.code()==200){
                Log.v("RETRO","Response Satisfed!");
            }else{
                Log.v("RETRO","Failed U.U");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listLocal;

    }


}

