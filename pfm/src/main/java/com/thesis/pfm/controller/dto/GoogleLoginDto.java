package com.thesis.pfm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleLoginDto {

    @JsonProperty("jwtToken")
    private String jwtToken;

    @JsonProperty("isRegistered")
    private boolean isRegistered;

    @JsonProperty("email")
    private String email;
}
