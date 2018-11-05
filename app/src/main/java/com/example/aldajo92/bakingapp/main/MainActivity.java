package com.example.aldajo92.bakingapp.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.adapter.recipe.RecipeAdapter;
import com.example.aldajo92.bakingapp.adapter.recipe.RecipeListItemClickListener;
import com.example.aldajo92.bakingapp.detail.DetailActivity;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_RECIPE;

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

        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, getColumn());
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private int getColumn() {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isTablet) {
            return isLandscape ? TABLET_LANDSCAPE_COLUMN : TABLET_POTRAIT_COLUMN;
        } else {
            return isLandscape ? PHONE_LANDSCAPE_COLUMN : PHONE_POTRAIT_COLUMN;
        }
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
    }

    @Override
    public void showError(String message) {

    }
}
