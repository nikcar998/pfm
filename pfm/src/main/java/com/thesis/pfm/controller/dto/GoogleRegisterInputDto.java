package com.thesis.pfm.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoogleRegisterInputDto {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
}
