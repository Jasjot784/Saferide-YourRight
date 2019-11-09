package com.jasjotsingh.saferide_yourright;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CombuyClient {
    @GET("localnegocio/")
    public Call<List<CombuyLocal>> getListLocales();
    @GET("localesporproductos/{producto}")
    public Call<List<CombuyLocal>> getLocalesProducto(@Path("producto") String producto);
}
