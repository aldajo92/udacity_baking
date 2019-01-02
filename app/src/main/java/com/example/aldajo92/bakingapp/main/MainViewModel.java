package com.example.aldajo92.bakingapp.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.aldajo92.bakingapp.AppExecutors;
import com.example.aldajo92.bakingapp.api.BakingAPI;
import com.example.aldajo92.bakingapp.api.BakingService;
import com.example.aldajo92.bakingapp.db.RecipeDatabase;
import com.example.aldajo92.bakingapp.db.RecipeEntry;
import com.example.aldajo92.bakingapp.models.network.RecipeModel;
import com.example.aldajo92.bakingapp.models.ui.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aldajo92.bakingapp.Constants.NETWORK_ERROR;
import static com.example.aldajo92.bakingapp.Constants.OTHER_ERROR;

public class MainViewModel extends AndroidViewModel {

    private BakingAPI moviesApi;

    List<RecipeModel> recipeModelList;

    private MainViewLister mainViewLister;

    private RecipeDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);

        moviesApi = BakingService.getInstance();

        database = RecipeDatabase.getInstance(this.getApplication());
    }

    public void updateRecipeList() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.getRecipeDao().deleteAll();

                moviesApi.getRecipes().enqueue(new Callback<List<RecipeModel>>() {
                    @Override
                    public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                        recipeModelList = response.body();
                        List<Recipe> recipeList = new ArrayList<>();
                        for (RecipeModel recipeModel : recipeModelList) {
                            recipeList.add(new Recipe(recipeModel));
                            saveRecipeEntry(new RecipeEntry(recipeModel));
                        }

                        if (mainViewLister != null) {
                            mainViewLister.onRecipes(recipeList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                        if (t instanceof IOException) {
                            mainViewLister.showError(NETWORK_ERROR, t.getMessage());
                        } else {
                            mainViewLister.showError(OTHER_ERROR, t.getMessage());
                        }
                    }
                });
            }
        });
    }

    public void setMainViewLister(MainViewLister mainViewLister) {
        this.mainViewLister = mainViewLister;
    }

    public void saveRecipeEntry(final RecipeEntry movieEntry) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.getRecipeDao().insertRecipe(movieEntry);
            }
        });
    }

}
