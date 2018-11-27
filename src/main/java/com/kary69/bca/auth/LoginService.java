package com.kary69.bca.auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("api/oauth/token")
    Call<AccessToken> getAccessToken(
            @Field("grant_type") String grantType);
}
