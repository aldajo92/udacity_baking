package com.example.aldajo92.bakingapp.models.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModel {
    private int rowId;

    @SerializedName("id")
    private int recipeId;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<IngredientModel> ingredients = null;

    @SerializedName("steps")
    private List<StepModel> steps = null;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;
}
