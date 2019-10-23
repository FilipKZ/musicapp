package com.musicapp.model.Artist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

@Getter
@Setter
@ToString

public class Artists {

    @Id
    private NitriteId id;

    private String next;

    private String total;

    private String offset;

    private String previous;

    private String limit;

    private String href;

    private Items[] items;
}
