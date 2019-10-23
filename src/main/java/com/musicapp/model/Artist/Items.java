package com.musicapp.model.Artist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Items {
    private Images[] images;

    private Followers followers;

    private String[] genres;

    private String popularity;

    private String name;

    private String href;

    private String id;

    private String type;

    private ExternalUrls external_urls;

    private String uri;
}
