package com.example.spotify;

import java.util.List;

public class Track {
    private String name;
    private Album album;
    private List<Artist> artists;

    // Constructor
    public Track(String name, Album album, List<Artist> artists, String releaseDate) {
        this.name = name;
        this.album = album;
        this.artists = artists;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Album getAlbum() {
        return album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", album=" + album +
                ", artists=" + artists + '\'' +
                '}';
    }
}
