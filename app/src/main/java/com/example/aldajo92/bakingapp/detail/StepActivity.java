package com.example.aldajo92.bakingapp.detail;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.detail.fragments.StepFragment;
import com.example.aldajo92.bakingapp.models.ui.Step;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE_NAME;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_STEP_LIST;

public class StepActivity extends AppCompatActivity {

    @BindView(R.id.view_pager_content_view)
    ViewPager viewPagerSteps;

    @BindView(R.id.recipe_step_tablayout)
    TabLayout tabLayout;

    private int currentStep;
    private List<Step> steps;
    private String recipeName;

    private FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();

        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);

        handleIntentExtras();
        handleSavedInstanceState(savedInstanceState);
        setupPager();

        setupActionBar();
    }

    private void handleIntentExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_LIST_INDEX) && intent.hasExtra(EXTRA_STEP_LIST)) {
                currentStep = intent.getIntExtra(EXTRA_LIST_INDEX, -1);
                steps = intent.getParcelableArrayListExtra(EXTRA_STEP_LIST);
                recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
            }
        }
    }

    private void handleSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(EXTRA_STEP_LIST);
            currentStep = savedInstanceState.getInt(EXTRA_LIST_INDEX);
            recipeName = savedInstanceState.getString(EXTRA_RECIPE_NAME, "");
        } else {
            createRecipeStepFragment(currentStep);
        }
    }

    private void initActionBar() {
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

    private void setupPager() {
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return createRecipeStepFragment(position);
            }

            @Override
            public int getCount() {
                return steps.size();
            }

            @Override
            public CharSequence getPageTitle(int position){
                return steps.get(position).getShortDescription();
            }
        };
        viewPagerSteps.setAdapter(pagerAdapter);
        viewPagerSteps.setCurrentItem(currentStep, false);
        tabLayout.setupWithViewPager(viewPagerSteps);
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(recipeName);
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

    private StepFragment createRecipeStepFragment(int index) {
        StepFragment stepFragment = StepFragment.newInstance(steps.get(index));
        stepFragment.setListIndex(index);
        return stepFragment;
    }
}