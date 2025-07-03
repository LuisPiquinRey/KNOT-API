package com.luispiquinrey.common_artifacts.common;

public class JwtResponseDTO {
    private String accessTokenJwt;

    public JwtResponseDTO() {
    }

    public JwtResponseDTO(String accessTokenJwt) {
        this.accessTokenJwt = accessTokenJwt;
    }

    public String getAccessTokenJwt() {
        return accessTokenJwt;
    }

    public void setAccessTokenJwt(String accessTokenJwt) {
        this.accessTokenJwt = accessTokenJwt;
    }
}
