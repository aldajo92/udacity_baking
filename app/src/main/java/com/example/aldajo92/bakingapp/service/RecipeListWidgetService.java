package com.example.aldajo92.bakingapp.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.aldajo92.bakingapp.models.ui.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipeListWidgetService extends RemoteViewsService {

    public static final String RECIPES_KEY = "com.example.aldajo92.bakingapp.RECIPES_KEY";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Type type = new TypeToken<ArrayList<Recipe>>(){}.getType();
        ArrayList<Recipe> recipes = new Gson().fromJson(intent.getStringExtra(RECIPES_KEY), type);
        return new RecipeListRemoteViewsFactory(getApplicationContext(), recipes);
    }
}