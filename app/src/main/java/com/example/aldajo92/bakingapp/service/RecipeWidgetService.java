package com.example.aldajo92.bakingapp.service;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.aldajo92.bakingapp.BakingWidgetProvider;
import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.db.RecipeEntry;
import com.example.aldajo92.bakingapp.models.WidgetType;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeWidgetService extends LifecycleService {

    private RecipeWidgetViewModel viewModel;
    private AppWidgetManager appWidgetManager;

    @Override
    public void onCreate() {
        super.onCreate();
        viewModel = new RecipeWidgetViewModel(getApplication());
        appWidgetManager = AppWidgetManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        viewModel.getDatabase().getRecipeDao().getLiveDataRecipes().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                if (recipeEntries != null){
                    ArrayList<Recipe> recipeList = new ArrayList<>();

                    for (RecipeEntry recipeEntry : recipeEntries) {
                        recipeList.add(new Recipe(recipeEntry));
                    }

                    updateWidget(recipeList);
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWidget(ArrayList<Recipe> recipeList) {
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        BakingWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds, WidgetType.RECIPES, recipeList);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
    }

    public static void startActionUpdateWidgets(@NonNull Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        context.startService(intent);
    }
}
