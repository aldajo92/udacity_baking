package com.projects.aldajo92.bakingapp.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.projects.aldajo92.bakingapp.R;
import com.projects.aldajo92.bakingapp.adapter.step.StepListItemClickListener;
import com.projects.aldajo92.bakingapp.detail.fragments.DetailFragment;
import com.projects.aldajo92.bakingapp.detail.fragments.StepFragment;
import com.projects.aldajo92.bakingapp.models.ui.Recipe;
import com.projects.aldajo92.bakingapp.service.recipe.RecipeWidgetService;
import com.projects.aldajo92.bakingapp.util.PreferenceUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.projects.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.projects.aldajo92.bakingapp.Constants.EXTRA_RECIPE;
import static com.projects.aldajo92.bakingapp.Constants.EXTRA_RECIPE_NAME;
import static com.projects.aldajo92.bakingapp.Constants.EXTRA_STEP_LIST;

public class DetailActivity extends AppCompatActivity implements StepListItemClickListener, ViewPager.OnPageChangeListener {

    @Nullable
    @BindView(R.id.view_pager_content_view)
    ViewPager viewPagerSteps;

    @Nullable
    @BindView(R.id.tabLayout_step)
    TabLayout tabLayout;

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

        PreferenceUtil.setSelectedRecipeId(this, recipe.getRecipeId());
        PreferenceUtil.setSelectedRecipeName(this, recipe.getName());
        RecipeWidgetService.startActionUpdateWidgets(this);
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

            @Override
            public CharSequence getPageTitle(int position){
                return recipe.getSteps().get(position).getShortDescription();
            }
        };
        viewPagerSteps.setAdapter(pagerAdapter);
        viewPagerSteps.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPagerSteps);
    }

    private void replaceRecipeDetailFragment(Recipe recipe) {
        DetailFragment detailFragment = DetailFragment.getInstance(this);
        detailFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, detailFragment)
                .commit();
    }

    private StepFragment createRecipeStepFragment(int position) {
        StepFragment stepFragment = StepFragment.newInstance(recipe.getSteps().get(position));
        stepFragment.setListIndex(position);
        return stepFragment;
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
        ((StepFragment) pagerAdapter.getItem(position)).stopPlayer();
    }
}
