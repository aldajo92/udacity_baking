package com.example.aldajo92.bakingapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.aldajo92.bakingapp.Constants.BASE_URL;

public class BakingService {

    private static BakingService client = new BakingService();

    private BakingAPI api;

    private BakingService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(BakingAPI.class);
    }

    public static BakingAPI getInstance() {
        return client.api;
    }
}
