package com.example.aldajo92.bakingapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.aldajo92.bakingapp.models.network.IngredientModel;
import com.example.aldajo92.bakingapp.models.network.RecipeModel;

import java.util.List;

import static com.example.aldajo92.bakingapp.db.DBConstants.RECIPES_TABLE;

@Entity(tableName = RECIPES_TABLE)
public class RecipeEntry {

    @PrimaryKey()
    private long recipeId;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String imageUrl;

    @ColumnInfo
    @TypeConverters(DataTypeConverter.class)
    private List<IngredientModel> ingredientList;

    @ColumnInfo
    private int servings;

    @ColumnInfo
    private String image;

    public RecipeEntry(){
        this.recipeId = 0;
        this.name = "";
        this.imageUrl = "";
    }

    public RecipeEntry(long id, String title, String imageUrl, List<IngredientModel> ingredientModelList) {
        this.recipeId = id;
        this.name = title;
        this.imageUrl = imageUrl;
        this.ingredientList = ingredientModelList;
    }

    public RecipeEntry(RecipeModel recipeModel){
        this.recipeId = recipeModel.getRecipeId();
        this.name = recipeModel.getName();
        this.imageUrl = recipeModel.getImage();
        this.ingredientList = recipeModel.getIngredients();
    }

    public long getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<IngredientModel> getIngredientList() {
        return ingredientList;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public void setRecipeId(long id) {
        this.recipeId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIngredientList(List<IngredientModel> ingredientModelList) {
        this.ingredientList = ingredientModelList;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
