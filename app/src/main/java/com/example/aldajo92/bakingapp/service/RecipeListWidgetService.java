package com.example.aldajo92.bakingapp.service;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aldajo92.bakingapp.AppExecutors;
import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.db.RecipeDatabase;
import com.example.aldajo92.bakingapp.db.RecipeEntry;

import java.util.List;

public class RecipeListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeListRemoteViewsFactory(getApplicationContext());
    }
}

class RecipeListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<RecipeEntry> recipes;
    private RecipeDatabase database;


    public RecipeListRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        database = RecipeDatabase.getInstance(mContext);
    }

    @Override
    public void onDataSetChanged() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                recipes = database.recipeDao().getRecipes();
            }
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipes != null ? recipes.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RecipeEntry recipe = recipes.get(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);
        views.setTextViewText(R.id.textview_ingredient_summary, recipe.getName());

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
        //Bundle extras = new Bundle();
        //extras.putParcelable(RecipeActivity.EXTRA_RECIPE, recipe);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtra(EXTRA_RECIPE, recipe);
//        views.setOnClickFillInIntent(R.id.textview_ingredient_summary, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
