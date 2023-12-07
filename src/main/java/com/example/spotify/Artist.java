package com.example.spotify;

import java.util.List;

public class Artist {
    private String name;
    private List<String> genres;

    // Constructor, getters, and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}