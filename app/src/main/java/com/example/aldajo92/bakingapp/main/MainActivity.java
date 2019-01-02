package com.example.aldajo92.bakingapp.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.adapter.recipe.RecipeAdapter;
import com.example.aldajo92.bakingapp.adapter.recipe.RecipeListItemClickListener;
import com.example.aldajo92.bakingapp.detail.DetailActivity;
import com.example.aldajo92.bakingapp.models.ui.Recipe;
import com.example.aldajo92.bakingapp.service.RecipeWidgetService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE_LIST;

public class MainActivity extends AppCompatActivity implements RecipeListItemClickListener, MainViewLister {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecipeAdapter recipeAdapter;

    private static final int PHONE_POTRAIT_COLUMN = 1;
    private static final int PHONE_LANDSCAPE_COLUMN = 2;
    private static final int TABLET_POTRAIT_COLUMN = 2;
    private static final int TABLET_LANDSCAPE_COLUMN = 3;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.setMainViewLister(this);

        viewModel.updateRecipeList();

        initRecyclerView();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EXTRA_RECIPE_LIST)) {
                List<Recipe> recipes = savedInstanceState.getParcelableArrayList(EXTRA_RECIPE_LIST);
                recipeAdapter.setItems(recipes);
            }
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
        recipeAdapter.setItems(recipeList);
        RecipeWidgetService.startActionUpdateWidgets(MainActivity.this);
    }

    @Override
    public void showError(String message) {

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
