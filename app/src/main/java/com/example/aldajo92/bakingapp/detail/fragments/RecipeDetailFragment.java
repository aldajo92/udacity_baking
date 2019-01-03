package com.example.aldajo92.bakingapp.detail.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.adapter.ingredients.IngredientAdapter;
import com.example.aldajo92.bakingapp.adapter.ingredients.IngredientListItemClickListener;
import com.example.aldajo92.bakingapp.adapter.step.StepAdapter;
import com.example.aldajo92.bakingapp.adapter.step.StepListItemClickListener;
import com.example.aldajo92.bakingapp.models.ui.Ingredient;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.RECIPE;

public class RecipeDetailFragment extends Fragment implements IngredientListItemClickListener, StepListItemClickListener {

    @BindView(R.id.recyclerview_ingredient)
    RecyclerView ingredientRecyclerView;

    @BindView(R.id.recyclerview_step)
    RecyclerView stepRecyclerView;

    private IngredientAdapter ingredientAdapter;
    private StepAdapter stepAdapter;

    private Recipe recipe;

    private StepListItemClickListener listener;

    public RecipeDetailFragment() { }

    public void setListener(StepListItemClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, fragmentView);

        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setNestedScrollingEnabled(false);
        ingredientAdapter = new IngredientAdapter(this);
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        stepRecyclerView.setHasFixedSize(true);
        stepRecyclerView.setNestedScrollingEnabled(false);
        stepAdapter = new StepAdapter(this);
        stepRecyclerView.setAdapter(stepAdapter);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE);
        }

        if (recipe != null) {
            ingredientAdapter.setData(recipe.getIngredients());
            stepAdapter.setData(recipe.getSteps());
        }

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setRecipe(@NonNull Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onIngredientItemClick(Ingredient ingredient, int position) {

    }

    @Override
    public void onStepItemClick(int position) {
        listener.onStepItemClick(position);
    }

    public static RecipeDetailFragment getInstance(StepListItemClickListener listener){
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setListener(listener);
        return recipeDetailFragment;
    }
}
