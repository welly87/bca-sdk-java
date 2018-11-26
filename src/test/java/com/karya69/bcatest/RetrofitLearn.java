package com.karya69.bcatest;

import com.kary69.bca.auth.AccessToken;
import com.kary69.bca.auth.LoginService;
import com.kary69.bca.auth.ServiceGenerator;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class RetrofitLearn {
    @Test
    public void should_access_token() throws IOException {
        String clientId = "Client_ID";
        String clientSecret = "Client_Secret";
        String code = "Code";

        LoginService loginService =
                ServiceGenerator.createService(LoginService.class, clientId, clientSecret);
        Call<AccessToken> call = loginService.getAccessToken(code, "client_credentials");
        Response<AccessToken> response = call.execute();
        AccessToken accessToken = response.body();

        System.out.println(response.message());
        System.out.println(accessToken.getAccess_token());
        System.out.println(accessToken.getToken_type());


    }
}
