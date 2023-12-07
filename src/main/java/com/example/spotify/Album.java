package com.example.spotify;

import com.google.gson.annotations.SerializedName;

public class Album {
    private String name;
    @SerializedName("release_date")
    private String releaseDate;
    private String imageUrl;

    // Constructor, getters, and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
