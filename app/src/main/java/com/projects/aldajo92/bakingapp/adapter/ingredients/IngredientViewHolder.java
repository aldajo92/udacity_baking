package com.projects.aldajo92.bakingapp.adapter.ingredients;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projects.aldajo92.bakingapp.R;
import com.projects.aldajo92.bakingapp.models.ui.Ingredient;

import butterknife.BindView;
import butterknife.ButterKnife;

class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.textView_ingredient_summary)
    TextView ingredientSummary;

    private Ingredient ingredient;

    private IngredientListItemClickListener ingredientListItemClickListener;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    void bind(@NonNull Ingredient ingredient, IngredientListItemClickListener ingredientListItemClickListener) {
        this.ingredient = ingredient;
        this.ingredientListItemClickListener = ingredientListItemClickListener;
//            int backgroundColor = getAdapterPosition() % 2 == 0 ? R.color.background1 : R.color.background2;
//            layoutRoot.setBackgroundColor(ContextCompat.getColor(layoutRoot.getContext(), backgroundColor));
        ingredientSummary.setText(ingredient.toString());
    }

    @Override
    public void onClick(View v) {
        ingredientListItemClickListener.onIngredientItemClick(ingredient, getAdapterPosition());
    }
}