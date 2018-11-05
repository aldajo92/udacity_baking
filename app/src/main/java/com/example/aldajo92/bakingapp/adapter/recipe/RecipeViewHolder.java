package com.example.aldajo92.bakingapp.adapter.recipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.aldajo92.bakingapp.ImageUtil;
import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.models.ui.Recipe;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.imageview_receipt)
    AppCompatImageView image;

    @BindView(R.id.textview_recipe_name)
    TextView recipeName;

    private RecipeListItemClickListener recipeListItemClickListener;

    Recipe recipe;

    public RecipeViewHolder(View itemView, RecipeListItemClickListener recipeListItemClickListener) {
        super(itemView);
        this.recipeListItemClickListener = recipeListItemClickListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    void bind(@NonNull Recipe recipe) {
        this.recipe = recipe;
        if (!TextUtils.isEmpty(recipe.getImage())) {
            Picasso.get().load(recipe.getImage()).into(image);
        } else {
            Picasso.get().load(ImageUtil.getImageResId(recipe.getName())).into(image);
        }
        recipeName.setText(recipe.getName());
    }

    @Override
    public void onClick(View v) {
        recipeListItemClickListener.onRecipeItemClick(recipe);
    }
}