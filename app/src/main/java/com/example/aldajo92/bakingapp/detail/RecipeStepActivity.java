package com.example.aldajo92.bakingapp.detail;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.detail.fragments.RecipeStepFragment;
import com.example.aldajo92.bakingapp.models.ui.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE_NAME;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_STEP_LIST;

public class RecipeStepActivity extends AppCompatActivity {

    @BindView(R.id.view_pager_content_view)
    ViewPager viewPagerSteps;

    private int currentStep;
    private List<Step> steps;
    private String recipeName;

    private FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_LIST_INDEX) && intent.hasExtra(EXTRA_STEP_LIST)) {
                currentStep = intent.getIntExtra(EXTRA_LIST_INDEX, -1);
                steps = intent.getParcelableArrayListExtra(EXTRA_STEP_LIST);
                recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
            }
        }

        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(EXTRA_STEP_LIST);
            currentStep = savedInstanceState.getInt(EXTRA_LIST_INDEX);
            recipeName = savedInstanceState.getString(EXTRA_RECIPE_NAME, "");
        } else {
            createRecipeStepFragment(currentStep);
        }

        initViewPager();

        setTitle(recipeName);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupActionBar() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
        }
    }

    private void initViewPager() {
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return createRecipeStepFragment(position);
            }

            @Override
            public int getCount() {
                return steps.size();
            }
        };
        viewPagerSteps.setAdapter(pagerAdapter);
        viewPagerSteps.setCurrentItem(currentStep, false);

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
        outState.putInt(EXTRA_LIST_INDEX, currentStep);
        outState.putParcelableArrayList(EXTRA_STEP_LIST, new ArrayList<Parcelable>(steps));
        outState.putString(EXTRA_RECIPE_NAME, recipeName);
    }

    private RecipeStepFragment createRecipeStepFragment(int index) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setListIndex(index);
        recipeStepFragment.setStep(steps.get(index));
        return recipeStepFragment;
    }
}