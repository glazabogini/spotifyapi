package com.example.spotify;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Track> tracksTable;
    @FXML
    private TableColumn<Track, String> nameColumn;
    @FXML
    private TableColumn<Track, String> artistColumn;
    @FXML
    private TableColumn<Track, String> albumColumn;
    @FXML
    private TableColumn<Track, String> releaseDateColumn;

    private ObservableList<Track> trackData = FXCollections.observableArrayList();
    private SpotifyService spotifyService = new SpotifyService();

    @FXML
    private void initialize() {
        // Set up table columns to display track data
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        artistColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getArtists().stream()
                        .map(Artist::getName)
                        .collect(Collectors.joining(", "))
        ));

        albumColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAlbum().getName()));

        releaseDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getAlbum().getReleaseDate()
        ));
        tracksTable.setItems(trackData);

        // Set up row double-click event to open track details
        tracksTable.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Track rowData = row.getItem();
                    openTrackDetailWindow(rowData);
                }
            });
            return row;
        });
    }

    // Open a new window to display track details
    private void openTrackDetailWindow(Track track) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("track_detail_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            stage.getIcons().add(new Image("/logo.png"));

            TrackDetailController controller = loader.getController();
            controller.setTrack(track);

            stage.setTitle(track.getName());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle search functionality
    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        if (!query.isEmpty()) {
            List<Track> tracks = spotifyService.searchTracks(query);
            tracksTable.getItems().setAll(tracks);
        }
    }
}
