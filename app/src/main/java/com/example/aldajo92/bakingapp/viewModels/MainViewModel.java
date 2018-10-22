package com.example.aldajo92.bakingapp.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.aldajo92.bakingapp.api.BakingAPI;
import com.example.aldajo92.bakingapp.api.BakingService;

public class MainViewModel extends AndroidViewModel {

    private BakingAPI moviesApi;

    public MainViewModel(@NonNull Application application) {
        super(application);

        moviesApi = BakingService.getInstance();
    }

}
