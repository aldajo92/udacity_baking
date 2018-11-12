package com.example.aldajo92.bakingapp.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.adapter.step.StepListItemClickListener;
import com.example.aldajo92.bakingapp.detail.fragments.RecipeDetailFragment;
import com.example.aldajo92.bakingapp.detail.fragments.RecipeStepFragment;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;

public class DetailActivity extends AppCompatActivity implements StepListItemClickListener {

    @Nullable
    @BindView(R.id.frame_step_container)
    ViewPager viewPagerSteps;

    @BindView(R.id.frame_container)
    FrameLayout container;

    private Recipe recipe;

    private boolean isTablet;

    private int selectedStepIndex;

    private FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_RECIPE)) {
            recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
            replaceRecipeDetailFragment(recipe);
        }

        isTablet = viewPagerSteps != null;

        if (savedInstanceState != null) {
//            recipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
//            selectedStepIndex = savedInstanceState.getInt(EXTRA_LIST_INDEX);
        }

        initViewPager();

//        if (recipe != null) {
//            if (savedInstanceState == null) {
//                replaceRecipeDetailFragment(recipe);
//            }
//
//            if (findViewById(R.id.recipe_step_content_view) != null) {
//                isTwoPane = true;
//                if (savedInstanceState == null) {
////                    replaceRecipeStepFragment(selectedStepIndex);
//                }
//            } else {
//                isTwoPane = false;
//            }
//
//            setTitle(recipe.getName());
//        }
    }

    private void initViewPager() {
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return replaceRecipeStepFragment(position);
            }

            @Override
            public int getCount() {
                return recipe.getSteps().size();
            }
        };
        viewPagerSteps.setAdapter(pagerAdapter);

    }

    private void replaceRecipeDetailFragment(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment(this);
        recipeDetailFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, recipeDetailFragment)
                .commit();
    }

    private RecipeStepFragment replaceRecipeStepFragment(int position) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setListIndex(position);
        recipeStepFragment.setStep(recipe.getSteps().get(position));
        recipeStepFragment.isPrevEnabled(position > 0);
        recipeStepFragment.isNextEnabled(position < recipe.getSteps().size() - 1);
        return recipeStepFragment;

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().
//                replace(R.id.frame_step_container, recipeStepFragment)
//                .commit();
    }

    @Override
    public void onStepItemClick(int position) {
        selectedStepIndex = position;
        if (isTablet) {
            replaceRecipeStepFragment(position);
        } else {
//            Intent intent = new Intent(this, RecipeStepActivity.class);
//            intent.putExtra(EXTRA_LIST_INDEX, position);
//            intent.putParcelableArrayListExtra(EXTRA_STEP_LIST, new ArrayList<Parcelable>(recipe.getSteps()));
//            intent.putExtra(EXTRA_RECIPE_NAME, recipe.getName());
//            startActivity(intent);
        }
    }
}
