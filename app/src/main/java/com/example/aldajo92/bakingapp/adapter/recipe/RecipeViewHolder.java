package com.example.aldajo92.bakingapp.adapter.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aldajo92.bakingapp.ImageUtils;
import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.image_view_receipt)
    AppCompatImageView image;

    @BindView(R.id.textview_recipe_name)
    TextView recipeName;

    private RecipeListItemClickListener recipeListItemClickListener;

    private Recipe recipe;

    public RecipeViewHolder(View itemView, RecipeListItemClickListener recipeListItemClickListener) {
        super(itemView);
        this.recipeListItemClickListener = recipeListItemClickListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    void bind(@NonNull Recipe recipe) {
        this.recipe = recipe;
        ImageUtils.loadImageFromUrl(recipe.getImage(), image, ImageUtils.getCakeImageResId(recipe.getName()));
        recipeName.setText(recipe.getName());
    }

    @Override
    public void onClick(View v) {
        recipeListItemClickListener.onRecipeItemClick(recipe);
    }
}