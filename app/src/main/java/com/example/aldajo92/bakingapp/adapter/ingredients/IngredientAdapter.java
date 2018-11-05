package com.example.aldajo92.bakingapp.adapter.ingredients;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.models.ui.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private final IngredientListItemClickListener ingredientListItemClickListener;
    private List<Ingredient> ingredients;

    public IngredientAdapter(IngredientListItemClickListener ingredientListItemClickListener) {
        this.ingredientListItemClickListener = ingredientListItemClickListener;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.view_item_ingredient, parent, false);
        return new IngredientViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient, ingredientListItemClickListener);
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    public void setData(@NonNull List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
}