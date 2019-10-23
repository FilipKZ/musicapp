package com.musicapp;

import com.google.gson.Gson;
import com.musicapp.model.Artist.Artists;
import com.musicapp.model.Artist.FoundArtists;
import com.musicapp.model.Artist.Items;
import com.musicapp.model.TokenModel;
import com.musicapp.model.Tracks.FoundTracks;
import com.musicapp.model.Tracks.Tracks;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Getter
@Setter

@Service
public class SpotifyConnect {

    private URL spotifyURL;
    public SpotifyConnect() throws MalformedURLException {
    }

    private void setSpotifyURL(String query, String type) throws MalformedURLException {
        this.spotifyURL = new URL("https://api.spotify.com/v1/search?q=" + query + "&type=" + type + "&limit=50");
    }

    public String getSpotifyToken() throws IOException {
        StringBuilder result = new StringBuilder();
        URL spotifyLink = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection spotifyTokenConnection = (HttpURLConnection) spotifyLink.openConnection();
        String originalCredentials = "1ee209b96e1a403f8fba8ba84ab98246:d1cef99d85394da5a687ddaef378136b";
        String base64Credentials = Base64.getEncoder().encodeToString(originalCredentials.getBytes());
        spotifyTokenConnection.setRequestProperty("Authorization", "Basic " + base64Credentials);
        spotifyTokenConnection.setRequestMethod("POST");
        spotifyTokenConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        spotifyTokenConnection.setDoOutput(true);
        OutputStream os = spotifyTokenConnection.getOutputStream();
        System.out.println(os.toString());
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write("grant_type=client_credentials");
        osw.flush();
        osw.close();
        os.close();
        spotifyTokenConnection.connect();
        Scanner sc = new Scanner(spotifyTokenConnection.getInputStream());
        Gson gson = new Gson();
        while (sc.hasNext()) {
            result.append(sc.nextLine());
        }
        TokenModel tokenModel = gson.fromJson(result.toString(), TokenModel.class);
        return tokenModel.getAccess_token();
    }

    private String establishConnection() throws IOException {
        StringBuilder result = new StringBuilder();
        URLConnection spotifyConnection = spotifyURL.openConnection();
//        String token = "BQCyToqIbwT8ib6x43DNGVis2R0aRDsPHxvnkiC5p6TDG60jwViqf_Eiq7Tlqxw9Z6ooTBEkoFolrF40dTqM4P_EL9dNJ3v9H55swOOjgoBh9rxHCRJe8Txiwnov88p-19LrctE4Sg-o";
        String token = getSpotifyToken();
        spotifyConnection.setRequestProperty("Authorization", "Bearer " + token);
        Scanner scanner = new Scanner(spotifyConnection.getInputStream());
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }
        return result.toString();
    }

    private FoundArtists getArtistsJSON (String artist) throws IOException {
        setSpotifyURL(artist, "artist");
        Gson gson = new Gson();
        FoundArtists foundArtists = gson.fromJson(this.establishConnection(), FoundArtists.class);
        return foundArtists;
    }

    public List<String> getArtistsNamesList (String artist) throws IOException {
        FoundArtists artistsJSON = getArtistsJSON(artist);
        List<String> artistsNames = new ArrayList<>();
        Artists jsonArtists = artistsJSON.getArtists();
        Items[] items = jsonArtists.getItems();
        for (Items element: items) {
            String elementName = element.getName();
            artistsNames.add(elementName);
        }
        return artistsNames;
    }

    private FoundTracks getTracksJSON (String trackName) throws IOException {
        setSpotifyURL(trackName, "track");
        Gson gson = new Gson();
        FoundTracks foundTracks = gson.fromJson(this.establishConnection(), FoundTracks.class);
        return foundTracks;
    }

    public List<String> getTracksList (String track) throws IOException {
        FoundTracks tracksJSON = getTracksJSON(track);
        List<String> tracks = new ArrayList<>();
        Tracks jsonTracks = tracksJSON.getTracks();
        com.musicapp.model.Tracks.Items[] items = jsonTracks.getItems();
        for (com.musicapp.model.Tracks.Items element: items) {
            String elementTitle = element.getName();
            tracks.add(elementTitle);
        }
        return tracks;
    }

}
