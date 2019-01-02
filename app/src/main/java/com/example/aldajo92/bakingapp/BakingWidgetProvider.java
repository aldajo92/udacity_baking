package com.example.aldajo92.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.aldajo92.bakingapp.db.RecipeDatabase;
import com.example.aldajo92.bakingapp.db.RecipeEntry;
import com.example.aldajo92.bakingapp.detail.DetailActivity;
import com.example.aldajo92.bakingapp.models.WidgetType;
import com.example.aldajo92.bakingapp.models.ui.Recipe;
import com.example.aldajo92.bakingapp.service.RecipeListWidgetService;
import com.example.aldajo92.bakingapp.service.RecipeWidgetService;
import com.example.aldajo92.bakingapp.util.PreferenceUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;
import static com.example.aldajo92.bakingapp.service.RecipeListWidgetService.RECIPES_KEY;

public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId, WidgetType widgetType) {

        RemoteViews views;

        if (widgetType == WidgetType.INGREDIENTS) {
            views = getIngredientListRemoteView(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } else if (widgetType == WidgetType.RECIPES) {

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<RecipeEntry> otherRecipeList = RecipeDatabase.getInstance(context).getRecipeDao().getRecipes();
                    ArrayList<Recipe> newRecipe = new ArrayList<>();
                    for (RecipeEntry entry :
                            otherRecipeList) {
                        newRecipe.add(new Recipe(entry));
                    }

                    RemoteViews views = getRecipeListRemoteView(context, newRecipe);
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }
            });
        } else {
            views = getEmptyRemoteView(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }


    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, WidgetType widgetType) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, widgetType);
        }
    }

    private static RemoteViews getIngredientListRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
        String recipeName = PreferenceUtil.getSelectedRecipeName(context);
        views.setTextViewText(R.id.title_text_view, context.getString(R.string.ingredient_of, recipeName));

//        Intent intent = new Intent(context, IngredientListWidgetService.class);
//        views.setRemoteAdapter(R.id.widget_list_view, intent);
//
//        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
//        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setPendingIntentTemplate(R.id.widget_list_view, appPendingIntent);

        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);
        return views;
    }

    private static RemoteViews getRecipeListRemoteView(Context context, ArrayList<Recipe> recipes) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
        String title = context.getString(R.string.recipes);
        views.setTextViewText(R.id.title_text_view, title);

        Intent intent = new Intent(context, RecipeListWidgetService.class);
        String recipesJson = new Gson().toJson(recipes);
        Bundle bundle = new Bundle();
        bundle.putString(RECIPES_KEY, recipesJson);
        intent.putExtras(bundle);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        Intent appIntent = new Intent(context, DetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list_view, appPendingIntent);

        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);
        return views;
    }

    private static RemoteViews getEmptyRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.empty_widget_view);

//        Intent appIntent = new Intent(context, RecipeActivity.class);
//        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setPendingIntentTemplate(R.id.empty_widget_image_view, appPendingIntent);
//        views.setOnClickPendingIntent(R.id.empty_widget_image_view, appPendingIntent);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }

        RecipeWidgetService.startActionUpdateWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

