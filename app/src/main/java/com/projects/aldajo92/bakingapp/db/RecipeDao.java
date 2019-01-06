package com.projects.aldajo92.bakingapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static com.projects.aldajo92.bakingapp.db.DBConstants.RECIPES_TABLE;

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
    RecipeEntry getRecipeById(long recipeId);

}
