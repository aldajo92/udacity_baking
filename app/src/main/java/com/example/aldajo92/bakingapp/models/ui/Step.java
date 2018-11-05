package com.example.aldajo92.bakingapp.models.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.aldajo92.bakingapp.models.network.StepModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

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

    protected Step(Parcel in) {
        stepId = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public Step() {

    }

    public Step(StepModel stepModel){
        rowId = stepModel.getRowId();
        stepId = stepModel.getStepId();
        shortDescription = stepModel.getShortDescription();
        description = stepModel.getDescription();
        videoURL = stepModel.getVideoURL();
        thumbnailURL = stepModel.getThumbnailURL();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
