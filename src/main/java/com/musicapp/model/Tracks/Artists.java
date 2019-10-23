package com.musicapp.model.Tracks;

import com.musicapp.model.Artist.ExternalUrls;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Artists {
    private String name;

    private String href;

    private String id;

    private String type;

    private ExternalUrls external_urls;

    private String uri;
}
