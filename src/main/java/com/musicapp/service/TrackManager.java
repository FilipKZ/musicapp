package com.musicapp.service;

import com.musicapp.SpotifyConnect;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor

@Service
public class TrackManager {

    private SpotifyConnect spotifyConnect;

    public List<String> getSelectedTracksList (String trackName) throws IOException {
        List<String> selectedTracks = spotifyConnect.getTracksList(trackName);
        return selectedTracks;
    }




}
