package com.nurflower.test.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyWebService {
    @GET("/latest")
    Call<CurrencyResponse> getCurrencyRates(@Query("base") String base);
}
