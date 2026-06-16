package com.gis.backend.dto;

public class LoginResponseDto {

    public String token;

    public String role;

    public String username;

    public String assignedClasses;

    public LoginResponseDto(
            String token,
            String role,
            String username,
            String assignedClasses
    ) {

        this.token = token;
        this.role = role;
        this.username = username;
        this.assignedClasses = assignedClasses;
    }
}