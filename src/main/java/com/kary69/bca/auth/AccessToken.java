package com.kary69.bca.auth;

public class AccessToken {

    private String access_token;
    private String token_type;

//    public String getAccessToken() {
//        return accessToken;
//    }

//    public String getTokenType() {
//        // OAuth requires uppercase Authorization HTTP header value for token type
//        if (! Character.isUpperCase(tokenType.charAt(0))) {
//            tokenType =
//                    Character
//                            .toString(tokenType.charAt(0))
//                            .toUpperCase() + tokenType.substring(1);
//        }
//
//        return tokenType;
//    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}