package com.example.aldajo92.bakingapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.aldajo92.bakingapp.models.network.RecipeModel;

import static com.example.aldajo92.bakingapp.db.DBConstants.RECIPES_TABLE;

@Entity(tableName = RECIPES_TABLE)
public class RecipeEntry {

    @PrimaryKey()
    private long recipeId;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String imageUrl;

    public RecipeEntry(){
        this.recipeId = 0;
        this.name = "";
        this.imageUrl = "";
    }

    public RecipeEntry(long id, String title, String imageUrl) {
        this.recipeId = id;
        this.name = title;
        this.imageUrl = imageUrl;
    }

    public RecipeEntry(RecipeModel recipeModel){
        this.recipeId = recipeModel.getRecipeId();
        this.name = recipeModel.getName();
        this.imageUrl = recipeModel.getImage();
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

    public void setRecipeId(long id) {
        this.recipeId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
