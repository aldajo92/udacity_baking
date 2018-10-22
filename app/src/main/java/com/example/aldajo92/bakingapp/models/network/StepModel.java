package com.example.aldajo92.bakingapp.models.network;

import com.google.gson.annotations.SerializedName;

public class StepModel {
    private int rowId;

    @SerializedName("id")
    private int stepId;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    @SerializedName("videoURL")
    private String videoURL;

    @SerializedName("thumbnailURL")
    private String thumbnailURL;
}
