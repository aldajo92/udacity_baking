package com.projects.aldajo92.bakingapp.service.ingredient;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.projects.aldajo92.bakingapp.models.ui.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class IngredientListWidgetService  extends RemoteViewsService {

    public static final String SELECTED_RECIPE_KEY = "com.example.aldajo92.bakingApp.SELECTED_RECIPE_KEY";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Type type = new TypeToken<Recipe>(){}.getType();
        Recipe recipe = new Gson().fromJson(intent.getExtras().getString(SELECTED_RECIPE_KEY, ""), type);
        return new IngredientListRemoteViewsFactory(getApplicationContext(), recipe);
    }
}
