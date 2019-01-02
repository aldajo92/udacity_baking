package com.example.aldajo92.bakingapp.service.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.db.RecipeDatabase;
import com.example.aldajo92.bakingapp.db.RecipeEntry;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import java.util.List;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;

class RecipeListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Recipe> recipes;


    public RecipeListRemoteViewsFactory(Context applicationContext, List<Recipe> recipes) {
        mContext = applicationContext;
        this.recipes = recipes;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

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
        Recipe recipe = recipes.get(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);
        views.setTextViewText(R.id.textview_ingredient_summary, recipe.getName());

        Intent intent = new Intent();
        intent.putExtra(EXTRA_RECIPE, recipe);
        views.setOnClickFillInIntent(R.id.layout_root, intent);

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
