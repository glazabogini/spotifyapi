package com.example.spotify;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.stream.Collectors;

public class TrackDetailController {
    @FXML
    private ImageView albumCoverImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;
    @FXML
    private Label releaseDateLabel;
    @FXML
    private Label genresLabel;

    private Track track;

    public void setTrack(Track track) {
        this.track = track;
        updateTrackDetails();
    }

    // Capitalize the string. It is used only for capitalizing genres names.
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (capitalized.length() > 0) capitalized.append(" ");
            capitalized.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return capitalized.toString();
    }


    private void updateTrackDetails() {
        if (track != null) {
            nameLabel.setText(track.getName());
            artistLabel.setText(track.getArtists().stream()
                    .map(Artist::getName)
                    .collect(Collectors.joining(", ")));
            albumLabel.setText(track.getAlbum().getName());
            releaseDateLabel.setText(track.getAlbum().getReleaseDate());

            // Load and set the album cover image
            Image albumCover = new Image(track.getAlbum().getImageUrl());
            albumCoverImageView.setImage(albumCover);

            // Aggregate genres from all artists
            String allGenres = track.getArtists().stream()
                    .filter(artist -> artist.getGenres() != null && !artist.getGenres().isEmpty())
                    .flatMap(artist -> artist.getGenres().stream())
                    .map(this::capitalize)
                    .distinct()
                    .collect(Collectors.joining(", "));

            genresLabel.setText(allGenres.isEmpty() ? "No genres available" : allGenres);
        }
    }
}