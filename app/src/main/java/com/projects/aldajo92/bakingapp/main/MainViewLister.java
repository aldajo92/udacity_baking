package com.projects.aldajo92.bakingapp.main;

import com.projects.aldajo92.bakingapp.models.ui.Recipe;

import java.util.List;

public interface MainViewLister {

    void onRecipes(List<Recipe> recipeList);

    void showError(int typeError, String message);

}
