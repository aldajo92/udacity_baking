package com.example.aldajo92.bakingapp;

import android.support.annotation.NonNull;

public class ImageUtils {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String BROWNIES = "Brownies";
    private static final String YELLOW_CAKE = "Yellow Cake";
    private static final String CHEESECAKE = "Cheesecake";

    public static int getImageResId(@NonNull String recipeName) {
        switch (recipeName) {
            case NUTELLA_PIE:
                return R.drawable.nutella_pie;
            case YELLOW_CAKE:
                return R.drawable.yellow_cake;
            case CHEESECAKE:
                return R.drawable.cheesecake;
            case BROWNIES:
                return R.drawable.brownies;
            default:
                return R.drawable.nutella_pie;
        }
    }
}
