package com.example.book_service.dto;

public class AuthenticationResponse {
    private String message;
    private String username;

    public AuthenticationResponse(String message, String username) {
        this.message = message;
        this.username = username;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
