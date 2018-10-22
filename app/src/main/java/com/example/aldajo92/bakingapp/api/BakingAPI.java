package com.example.aldajo92.bakingapp.api;

import com.example.aldajo92.bakingapp.models.network.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingAPI {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<RecipeModel>> getRecipes();
}
