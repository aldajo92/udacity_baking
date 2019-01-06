package com.projects.aldajo92.bakingapp.adapter.ingredients;

import com.projects.aldajo92.bakingapp.models.ui.Ingredient;

public interface IngredientListItemClickListener {
    void onIngredientItemClick(Ingredient ingredient, int position);
}