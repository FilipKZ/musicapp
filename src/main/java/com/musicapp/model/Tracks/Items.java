package com.musicapp.model.Tracks;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Items {
    private String disc_number;

    private Album album;

    private String[] available_markets;

    private String type;

    private ExternalIds external_ids;

    private String uri;

    private String duration_ms;

    private String explicit;

    private Artists[] artists;

    private String preview_url;

    private String popularity;

    private String name;

    private String track_number;

    private String href;

    private String id;

    private String is_local;

    private ExternalUrls external_urls;
}
