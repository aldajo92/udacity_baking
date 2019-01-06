package com.projects.aldajo92.bakingapp.models.network;

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

    public int getRowId() {
        return rowId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public List<IngredientModel> getIngredients() {
        return ingredients;
    }

    public List<StepModel> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
