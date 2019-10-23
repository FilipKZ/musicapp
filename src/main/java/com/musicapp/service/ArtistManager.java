package com.musicapp.service;

import com.musicapp.SpotifyConnect;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor

@Service
public class ArtistManager {

    private SpotifyConnect spotifyConnect;

    public List<String> getSelectedArtistsList (String artistName) throws IOException {
        List<String> selectedArtists = spotifyConnect.getArtistsNamesList(artistName);
        return selectedArtists;
    }
}
