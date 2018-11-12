package com.example.aldajo92.bakingapp.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.adapter.step.StepListItemClickListener;
import com.example.aldajo92.bakingapp.detail.fragments.RecipeDetailFragment;
import com.example.aldajo92.bakingapp.detail.fragments.RecipeStepFragment;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE_NAME;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_STEP_LIST;

public class DetailActivity extends AppCompatActivity implements StepListItemClickListener, ViewPager.OnPageChangeListener {

    @Nullable
    @BindView(R.id.viewPager_step_container)
    ViewPager viewPagerSteps;

    @BindView(R.id.frame_container)
    FrameLayout frameContainer;

    private Recipe recipe;

    private boolean isTablet;

    private int selectedStepIndex;

    private FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        handleIntentExtras();

        handleSavedInstanceState(savedInstanceState);

        setupActionBar();

        isTablet = viewPagerSteps != null;
        if (isTablet) {
            initViewPager();
        }
    }

    private void handleIntentExtras() {
        if (getIntent().hasExtra(EXTRA_RECIPE)) {
            recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
            replaceRecipeDetailFragment(recipe);
        }
    }

    private void handleSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
            selectedStepIndex = savedInstanceState.getInt(EXTRA_LIST_INDEX);
        }
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(recipe.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_LIST_INDEX, selectedStepIndex);
        outState.putParcelable(EXTRA_RECIPE, recipe);
    }

    private void initViewPager() {
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return createRecipeStepFragment(position);
            }

            @Override
            public int getCount() {
                return recipe.getSteps().size();
            }
        };
        viewPagerSteps.setAdapter(pagerAdapter);
        viewPagerSteps.addOnPageChangeListener(this);

    }

    private void replaceRecipeDetailFragment(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.getInstance(this);
        recipeDetailFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, recipeDetailFragment)
                .commit();
    }

    private RecipeStepFragment createRecipeStepFragment(int position) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setListIndex(position);
        recipeStepFragment.setStep(recipe.getSteps().get(position));
        return recipeStepFragment;
    }

    @Override
    public void onStepItemClick(int position) {
        selectedStepIndex = position;
        if (isTablet && viewPagerSteps != null) {
            viewPagerSteps.setCurrentItem(position);
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(EXTRA_LIST_INDEX, position);
            intent.putParcelableArrayListExtra(EXTRA_STEP_LIST, new ArrayList<Parcelable>(recipe.getSteps()));
            intent.putExtra(EXTRA_RECIPE_NAME, recipe.getName());
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int position, float v, int i1) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int position) {
        ((RecipeStepFragment) pagerAdapter.getItem(position)).stopPlayer();
    }
}
