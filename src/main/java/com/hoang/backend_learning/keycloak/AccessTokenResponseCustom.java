package com.hoang.backend_learning.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.representations.AccessTokenResponse;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenResponseCustom {
    protected String token;

    protected long expiresIn;

    protected long refreshExpiresIn;

    protected String refreshToken;

    protected String tokenType;

    protected String idToken;

    protected int notBeforePolicy;

    protected String sessionState;

    protected Map<String, Object> otherClaims = new HashMap<>();

    // OIDC Financial API Read Only Profile : scope MUST be returned in the response from Token Endpoint
    @JsonProperty("scope")
    protected String scope;

    public AccessTokenResponseCustom() {
    }

    public AccessTokenResponseCustom(AccessTokenResponse accessTokenResponse) {
        this.token = accessTokenResponse.getToken();
        this.idToken = accessTokenResponse.getIdToken();
        this.expiresIn = accessTokenResponse.getExpiresIn();
        this.refreshToken = accessTokenResponse.getRefreshToken();
        this.tokenType = accessTokenResponse.getTokenType();
        this.notBeforePolicy = accessTokenResponse.getNotBeforePolicy();
        this.sessionState = accessTokenResponse.getSessionState();
        this.otherClaims = accessTokenResponse.getOtherClaims();
        this.scope = accessTokenResponse.getScope();
    }
}
