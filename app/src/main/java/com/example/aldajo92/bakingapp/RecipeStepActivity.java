package com.example.aldajo92.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.aldajo92.bakingapp.detail.fragments.RecipeStepFragment;
import com.example.aldajo92.bakingapp.models.ui.Step;

import java.util.ArrayList;
import java.util.List;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE_NAME;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_STEP_LIST;

public class RecipeStepActivity extends AppCompatActivity {

    private int listIndex;
    private List<Step> steps;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        setContentView(R.layout.activity_recipe_step);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_LIST_INDEX) && intent.hasExtra(EXTRA_STEP_LIST)) {
                listIndex = intent.getIntExtra(EXTRA_LIST_INDEX, -1);
                steps = intent.getParcelableArrayListExtra(EXTRA_STEP_LIST);
                recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
            }
        }

        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(EXTRA_STEP_LIST);
            listIndex = savedInstanceState.getInt(EXTRA_LIST_INDEX);
            recipeName = savedInstanceState.getString(EXTRA_RECIPE_NAME, "");
        }

        if (listIndex == -1 || steps == null) {
            finish();
        }

        if (savedInstanceState == null) {
            replaceFragment(listIndex);
        }

        setTitle(recipeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        outState.putInt(EXTRA_LIST_INDEX, listIndex);
        outState.putParcelableArrayList(EXTRA_STEP_LIST, new ArrayList<Parcelable>(steps));
        outState.putString(EXTRA_RECIPE_NAME, recipeName);
    }

    private void replaceFragment(int index) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setListIndex(index);
        recipeStepFragment.setStep(steps.get(index));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_content_view, recipeStepFragment)
                .commit();
    }
}