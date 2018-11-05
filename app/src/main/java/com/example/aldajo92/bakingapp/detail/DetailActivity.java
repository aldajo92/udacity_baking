package com.example.aldajo92.bakingapp.detail;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.detail.fragments.RecipeDetailFragment;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;

public class DetailActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.container)
    FrameLayout container;

    private boolean isTwoPane;
    private Recipe recipe;
    private int selectedStepIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE)) {
                recipe = intent.getParcelableExtra(EXTRA_RECIPE);
                // TODO: WHAT FUCKING IS THIS?
                selectedStepIndex = savedInstanceState != null && savedInstanceState.containsKey(EXTRA_LIST_INDEX) ?
                        savedInstanceState.getInt(EXTRA_LIST_INDEX) : 0;
            }
        } else if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
            selectedStepIndex = savedInstanceState.getInt(EXTRA_LIST_INDEX);
        }

        if (recipe != null) {
            if (savedInstanceState == null) {
                replaceRecipeDetailFragment(recipe);
            }

//            if (findViewById(R.id.recipe_step_content_view) != null) {
//                isTwoPane = true;
//                if (savedInstanceState == null) {
//                    replaceRecipeStepFragment(selectedStepIndex);
//                }
//            } else {
//                isTwoPane = false;
//            }

            setTitle(recipe.getName());
        }
    }

    private void replaceRecipeDetailFragment(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, recipeDetailFragment)
                .commit();
    }

//    private void replaceRecipeStepFragment(int position) {
//        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
//        recipeStepFragment.setListIndex(position);
//        recipeStepFragment.setStep(recipe.getSteps().get(position));
//        recipeStepFragment.isPrevEnabled(position > 0);
//        recipeStepFragment.isNextEnabled(position < recipe.getSteps().size() - 1);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().
//                replace(R.id.recipe_step_content_view, recipeStepFragment)
//                .commit();
//    }
}
