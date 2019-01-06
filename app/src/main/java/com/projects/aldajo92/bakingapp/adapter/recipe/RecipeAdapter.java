package com.projects.aldajo92.bakingapp.adapter.recipe;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.aldajo92.bakingapp.R;
import com.projects.aldajo92.bakingapp.models.ui.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final RecipeListItemClickListener recipeListItemClickListener;
    private List<Recipe> recipes;

    public RecipeAdapter(RecipeListItemClickListener recipeListItemClickListener) {
        this.recipeListItemClickListener = recipeListItemClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.view_item_recipe, parent, false);
        return new RecipeViewHolder(rootView, recipeListItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public void setItems(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public List<Recipe> getData() {
        return recipes;
    }
}