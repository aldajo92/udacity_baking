package com.example.aldajo92.bakingapp.models.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.aldajo92.bakingapp.models.network.IngredientModel;
import com.example.aldajo92.bakingapp.models.network.RecipeModel;
import com.example.aldajo92.bakingapp.models.network.StepModel;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    private int rowId;

    private int recipeId;

    private String name;

    private List<Ingredient> ingredients = new ArrayList<>();

    private List<Step> steps = new ArrayList<>();

    private int servings;

    private String image;

    private Recipe(Parcel in) {
        recipeId = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public Recipe() {

    }

    public Recipe(RecipeModel recipeModel) {
        rowId = recipeModel.getRowId();
        recipeId = recipeModel.getRecipeId();
        name = recipeModel.getName();
        for (IngredientModel ingredient :
                recipeModel.getIngredients()) {
            ingredients.add(new Ingredient(ingredient));
        }
        for (StepModel step :
                recipeModel.getSteps()) {
            steps.add(new Step(step));
        }
        servings = recipeModel.getServings();
        image = recipeModel.getImage();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}
