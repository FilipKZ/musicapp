package com.musicapp.controller;

import com.musicapp.service.ArtistManager;
import com.musicapp.service.TrackManager;
import org.dizitart.no2.*;
import org.dizitart.no2.filters.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("/")
public class HomeController {

    private TrackManager trackManager;
    private ArtistManager artistManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    public HomeController(TrackManager trackManager, ArtistManager artistManager) {
        this.trackManager = trackManager;
        this.artistManager = artistManager;
    }

    @GetMapping
    public String showHomeView() {
        return "index";
    }

    @GetMapping("/finder")
    public String finderView() {
        return "finder";
    }

    @PostMapping("/finder")
    public String foundItems(Model model,
                             @RequestParam(value = "type") String type,
                             @RequestParam(value = "name") String name) throws IOException {
        List<String> selectedItems = new ArrayList<>();

        if (type.equals("artist")) {
            selectedItems = artistManager.getSelectedArtistsList(name.replace(" ", "%20"));
            LOGGER.info("User searched for artist: " + name);
            model.addAttribute("artists", selectedItems);
        } else if (type.equals("track")) {
            selectedItems = trackManager.getSelectedTracksList(name.replace(" ", "%20"));
            LOGGER.info("User searched for track: " + name);
            model.addAttribute("tracks", selectedItems);
        }

        return "searchresults";
    }

    @PostMapping("/addfavouritetrack")
    public String addTrackToFavourites(@RequestParam(value = "track") String track) {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("./db/favourites.db")
                .openOrCreate("user", "password");
        NitriteCollection collection = db.getCollection("tracks");
        Cursor cursor = collection.find(Filters.eq("track", track));
        if (cursor.size() == 0) {
            Document doc = Document.createDocument("track", track);
            collection.insert(doc);
        }
        LOGGER.info("User added new track: " + track);
        collection.close();
        db.close();
        return "index";
    }

    @PostMapping("/addfavouriteartist")
    public String addArtistToFavourites(@RequestParam(value = "artist") String artist) {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("./db/favourites.db")
                .openOrCreate("user", "password");
        NitriteCollection collection = db.getCollection("artists");
        Cursor cursor = collection.find(Filters.eq("artist", artist));
        if (cursor.size() == 0) {
            Document doc = Document.createDocument("artist", artist);
            collection.insert(doc);
        }
        LOGGER.info("User added new artist: " + artist);
        collection.close();
        db.close();
        return "index";
    }

    @GetMapping("/favSongs")
    public String getFavouriteSongs(Model model) {
        List<String> favouriteSongs = new ArrayList<>();
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("./db/favourites.db")
                .openOrCreate("user", "password");
        NitriteCollection collection = db.getCollection("tracks");
        Cursor cursor = collection.find(FindOptions.sort("track", SortOrder.Descending));
        for (Document document : cursor) {
            Object dbTrack = document.get("track");
            String trackTitle = dbTrack.toString();
            favouriteSongs.add(trackTitle);
        }
        LOGGER.info("User looked up favourite songs");
        collection.close();
        db.close();
        Collections.sort(favouriteSongs);
        model.addAttribute("tracks", favouriteSongs);
        return "favsongs";
    }

    @PostMapping("/deletefavouritetrack")
    public String deleteTrackFromFavourites(@RequestParam(value = "track") String track) {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("./db/favourites.db")
                .openOrCreate("user", "password");
        NitriteCollection collection = db.getCollection("tracks");
        collection.remove(Filters.eq("track", track));
        collection.close();
        db.close();
        LOGGER.info("User deleted song: " + track);
        return "index";
    }

    @GetMapping("/favArtists")
    public String getFavouriteArtists(Model model) {
        List<String> favouriteArtists = new ArrayList<>();
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("./db/favourites.db")
                .openOrCreate("user", "password");
        NitriteCollection collection = db.getCollection("artists");
        Cursor cursor = collection.find(FindOptions.sort("artists", SortOrder.Descending));
        for (Document document : cursor) {
            Object dbTrack = document.get("artist");
            String trackTitle = dbTrack.toString();
            favouriteArtists.add(trackTitle);
        }
        LOGGER.info("User looked up favourite artists");
        collection.close();
        db.close();
        Collections.sort(favouriteArtists);
        model.addAttribute("artists", favouriteArtists);
        return "favartists";
    }

    @PostMapping("/deletefavouriteartist")
    public String deleteArtistFromFavourites(@RequestParam(value = "artist") String artist) {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("./db/favourites.db")
                .openOrCreate("user", "password");
        NitriteCollection collection = db.getCollection("artists");
        collection.remove(Filters.eq("artist", artist));
        collection.close();
        db.close();
        LOGGER.info("User deleted artist: " + artist);
        return "index";
    }

    @GetMapping("/userlogs")
    public String showUserLogs(Model model) throws FileNotFoundException {
        File logs = new File("logs/spring-boot-logger.log");
        Scanner sc = new Scanner(logs);
        List<String> logsList = new ArrayList<>();
        while (sc.hasNext()) {
            logsList.add(sc.nextLine());
        }
        model.addAttribute("logs", logsList);
        LOGGER.info("Activity log was opened");
        return "userlogs";
    }
}