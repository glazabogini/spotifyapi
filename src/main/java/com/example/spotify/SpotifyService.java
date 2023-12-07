package com.example.spotify;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SpotifyService {
    // Client ID and Secret for Spotify API
    private final String clientId = "e49a98647e0f4ee98b4e5a9b44acf642";
    private final String clientSecret = "dee0a42f4fe44b1c9081580fa11afefc";
    private String accessToken;

    // Constructor to authenticate when an instance is created
    public SpotifyService() {
        authenticate();
    }

    // Authenticates the application and retrieves an access token
    private void authenticate() {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // Make a POST request to get the access token
        HttpResponse<String> response = Unirest.post("https://accounts.spotify.com/api/token")
                .header("Authorization", "Basic " + encodedCredentials)
                .field("grant_type", "client_credentials")
                .asString();

        // If authentication is successful, store the access token
        if (response.getStatus() == 200) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            accessToken = jsonResponse.getString("access_token");
        } else {
            System.out.println("Couldn't authenticate!");
        }
    }

    // Searches for tracks based on the provided query and returns a list of Track objects
    public List<Track> searchTracks(String query) {
        List<Track> tracks = new ArrayList<>();
        try {
            // Make a GET request to search tracks in the Spotify API
            HttpResponse<JsonNode> response = Unirest.get("https://api.spotify.com/v1/search")
                    .queryString("q", query)
                    .queryString("type", "track")
                    .header("Authorization", "Bearer " + accessToken)
                    .asJson();

            // Deserialize the JSON response into Track objects
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Track>>(){}.getType();
            JSONArray items = response.getBody().getObject().getJSONObject("tracks").getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject trackObject = items.getJSONObject(i);
                Track track = gson.fromJson(trackObject.toString(), Track.class);

                // Extract album image URL and set it in the Track object
                JSONObject albumObject = trackObject.getJSONObject("album");
                if (albumObject.has("images") && albumObject.getJSONArray("images").length() > 0) {
                    String imageUrl = albumObject.getJSONArray("images").getJSONObject(0).getString("url");
                    track.getAlbum().setImageUrl(imageUrl);
                }

                // Extract artists and fetch their genres
                JSONArray artistArray = trackObject.getJSONArray("artists");
                List<Artist> artistList = new ArrayList<>();
                for (int j = 0; j < artistArray.length(); j++) {
                    JSONObject artistObject = artistArray.getJSONObject(j);
                    Artist artist = gson.fromJson(artistObject.toString(), Artist.class);

                    // Fetch genres for each artist
                    artist.setGenres(fetchArtistGenres(artistObject.getString("id")));
                    artistList.add(artist);
                }
                track.setArtists(artistList);

                tracks.add(track);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracks;
    }

    // Fetches genres for a specific artist by their ID
    private List<String> fetchArtistGenres(String artistId) {
        List<String> genres = new ArrayList<>();
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://api.spotify.com/v1/artists/" + artistId)
                    .header("Authorization", "Bearer " + accessToken)
                    .asJson();

            if (response.getStatus() == 200) {
                JSONArray genresArray = response.getBody().getObject().getJSONArray("genres");
                for (int i = 0; i < genresArray.length(); i++) {
                    genres.add(genresArray.getString(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genres;
    }
}
