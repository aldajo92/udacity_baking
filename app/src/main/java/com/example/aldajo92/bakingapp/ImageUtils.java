package com.example.aldajo92.bakingapp;

import android.support.annotation.NonNull;

public class ImageUtils {
    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String BROWNIES = "Brownies";
    private static final String YELLOW_CAKE = "Yellow Cake";
    private static final String CHEESECAKE = "CheeseCake";

    public static int getImageResId(@NonNull String recipeName) {
        if (recipeName.equalsIgnoreCase(NUTELLA_PIE)) {
            return R.drawable.nutella_pie;
        } else if (recipeName.equalsIgnoreCase(BROWNIES)) {
            return R.drawable.brownies;
        } else if (recipeName.equalsIgnoreCase(YELLOW_CAKE)) {
            return R.drawable.yellow_cake;
        } else if (recipeName.equalsIgnoreCase(CHEESECAKE)) {
            return R.drawable.cheesecake;
        } else {
            return R.drawable.brownies;
        }
    }
}
