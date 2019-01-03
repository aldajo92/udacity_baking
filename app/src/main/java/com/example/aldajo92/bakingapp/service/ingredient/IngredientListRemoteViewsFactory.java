package com.example.aldajo92.bakingapp.service.ingredient;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.models.ui.Ingredient;
import com.example.aldajo92.bakingapp.models.ui.Recipe;
import com.example.aldajo92.bakingapp.util.PreferenceUtil;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;

public class IngredientListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Recipe recipe;

    public IngredientListRemoteViewsFactory(Context applicationContext, Recipe recipe) {
        this.mContext = applicationContext;
        this.recipe = recipe;
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
        return recipe != null && recipe.getIngredients() != null ?
                recipe.getIngredients().size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = recipe.getIngredients().get(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);
        views.setTextViewText(R.id.textview_ingredient_summary, ingredient.toString());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(EXTRA_RECIPE, recipe);
        views.setOnClickFillInIntent(R.id.layout_root, fillInIntent);

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
