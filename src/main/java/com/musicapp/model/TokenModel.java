package com.musicapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class TokenModel {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
}
