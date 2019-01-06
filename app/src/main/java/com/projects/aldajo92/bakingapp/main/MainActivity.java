package com.projects.aldajo92.bakingapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.aldajo92.bakingapp.R;
import com.projects.aldajo92.bakingapp.adapter.recipe.RecipeAdapter;
import com.projects.aldajo92.bakingapp.adapter.recipe.RecipeListItemClickListener;
import com.projects.aldajo92.bakingapp.detail.DetailActivity;
import com.projects.aldajo92.bakingapp.models.ui.Recipe;
import com.projects.aldajo92.bakingapp.service.recipe.RecipeWidgetService;
import com.projects.aldajo92.bakingapp.util.FetchingIdlingResource;
import com.projects.aldajo92.bakingapp.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.projects.aldajo92.bakingapp.Constants.EXTRA_RECIPE;
import static com.projects.aldajo92.bakingapp.Constants.EXTRA_RECIPE_LIST;
import static com.projects.aldajo92.bakingapp.Constants.NETWORK_ERROR;

public class MainActivity extends AppCompatActivity implements RecipeListItemClickListener, MainViewLister {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.imageView_no_internet)
    ImageView imageViewNoInternet;

    @BindView(R.id.textView_no_internet)
    TextView textViewNoInternet;

    @BindView(R.id.button_try_again)
    TextView buttonTryAgain;

    private RecipeAdapter recipeAdapter;

    private MainViewModel viewModel;

    private boolean wasUpdated = false;

    @Nullable
    private FetchingIdlingResource fetcherListener;

    @VisibleForTesting
    @NonNull
    public FetchingIdlingResource getIdlingResource() {
        if (fetcherListener == null) {
            fetcherListener = new FetchingIdlingResource();
            fetcherListener.setIdleState(false);
        }
        return fetcherListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.setMainViewLister(this);

        initRecyclerView();
        initListeners();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EXTRA_RECIPE_LIST)) {
                List<Recipe> recipes = savedInstanceState.getParcelableArrayList(EXTRA_RECIPE_LIST);
                recipeAdapter.setItems(recipes);
                wasUpdated = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceUtil.setSelectedRecipeId(this, PreferenceUtil.NO_ID);
        if (!wasUpdated) {
            viewModel.updateRecipeList();
            wasUpdated = true;
        }
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(
                this,
                calculateBestSpanCount(getResources().getDimensionPixelSize(R.dimen.width_image_home)));
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void initListeners() {
        buttonTryAgain.setOnClickListener(v -> viewModel.updateRecipeList());
    }

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public void onRecipes(List<Recipe> recipeList) {
        showViewError(false);
        recipeAdapter.setItems(recipeList);
        RecipeWidgetService.startActionUpdateWidgets(MainActivity.this);
        if (fetcherListener != null) fetcherListener.setIdleState(true);
    }

    @Override
    public void showError(int typeError, String message) {
        if (typeError == NETWORK_ERROR) {
            showViewError(true);
            textViewNoInternet.setText(message);
        }
    }

    private void showViewError(boolean showError) {
        recyclerView.setVisibility(showError ? View.GONE : View.VISIBLE);
        textViewNoInternet.setVisibility(showError ? View.VISIBLE : View.GONE);
        buttonTryAgain.setVisibility(showError ? View.VISIBLE : View.GONE);
        imageViewNoInternet.setVisibility(showError ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeAdapter != null) {
            List<Recipe> recipes = recipeAdapter.getData();
            if (recipes != null && !recipes.isEmpty()) {
                outState.putParcelableArrayList(EXTRA_RECIPE_LIST, new ArrayList<>(recipes));
            }
        }
    }
}
