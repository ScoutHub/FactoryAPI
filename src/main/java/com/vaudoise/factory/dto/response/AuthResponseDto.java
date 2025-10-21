package com.vaudoise.factory.dto.response;

import java.util.UUID;

public class AuthResponseDto {

    private String token;
    private UUID userId;
    private String clientType;

    public AuthResponseDto() {}

    public AuthResponseDto(String token, UUID userId, String clientType) {
        this.token = token;
        this.userId = userId;
        this.clientType = clientType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}