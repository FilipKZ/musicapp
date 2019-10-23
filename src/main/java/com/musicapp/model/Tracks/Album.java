package com.musicapp.model.Tracks;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Album {
    private Images[] images;

    private String[] available_markets;

    private String release_date_precision;

    private String type;

    private String uri;

    private String total_tracks;

    private Artists[] artists;

    private String release_date;

    private String name;

    private String album_type;

    private String href;

    private String id;

    private ExternalUrls external_urls;
}
