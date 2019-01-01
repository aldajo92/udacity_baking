package com.example.aldajo92.bakingapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static com.example.aldajo92.bakingapp.db.DBConstants.RECIPES_TABLE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM " + RECIPES_TABLE)
    LiveData<List<RecipeEntry>> getLiveDataRecipes();

    @Query("SELECT * FROM " + RECIPES_TABLE)
    List<RecipeEntry> getRecipes();

    @Insert
    void insertRecipe(RecipeEntry recipeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(RecipeEntry taskEntry);

    @Delete
    void deleteRecipe(RecipeEntry recipeEntry);

    @Query("DELETE FROM " + RECIPES_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + RECIPES_TABLE + " WHERE recipeId = :recipeId")
    LiveData<RecipeEntry> getFavoriteMovieById(long recipeId);

}
