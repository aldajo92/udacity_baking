package com.projects.aldajo92.bakingapp.service.recipe;

import android.appwidget.AppWidgetManager;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.projects.aldajo92.bakingapp.BakingWidgetProvider;
import com.projects.aldajo92.bakingapp.R;
import com.projects.aldajo92.bakingapp.db.RecipeEntry;
import com.projects.aldajo92.bakingapp.models.WidgetType;
import com.projects.aldajo92.bakingapp.models.ui.Recipe;
import com.projects.aldajo92.bakingapp.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class RecipeWidgetService extends LifecycleService {

    private RecipeWidgetViewModel viewModel;
    private AppWidgetManager appWidgetManager;

    private WidgetType widgetType;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        viewModel = new RecipeWidgetViewModel(getApplication());
        appWidgetManager = AppWidgetManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            viewModel.getDatabase().getRecipeDao().getLiveDataRecipes().observe(this, new Observer<List<RecipeEntry>>() {
                @Override
                public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                    int selectedRecipeId = PreferenceUtil.getSelectedRecipeId(RecipeWidgetService.this);
                    if (selectedRecipeId == PreferenceUtil.NO_ID) {
                        if (recipeEntries != null) {
                            if (recipeEntries.size() > 0) {
                                for (RecipeEntry recipeEntry : recipeEntries) {
                                    recipeList.add(new Recipe(recipeEntry));
                                }
                                widgetType = WidgetType.RECIPES;
                            } else {
                                widgetType = WidgetType.NONE;
                            }
                        } else {
                            widgetType = WidgetType.NONE;
                        }

                    } else {
                        widgetType = WidgetType.INGREDIENTS;
                    }
                    updateWidget();
                }
            });
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWidget() {
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        BakingWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds, widgetType);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
    }

    public static void startActionUpdateWidgets(@NonNull Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        context.startService(intent);
    }
}
