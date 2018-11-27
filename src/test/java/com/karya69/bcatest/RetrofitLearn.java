package com.karya69.bcatest;

import com.kary69.bca.auth.*;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Time;

public class RetrofitLearn {
    private String clientId = "1e004364-3f91-4cf0-9041-67e58dc490b3";
    private String clientSecret = "416e71fb-3c50-4727-bd1f-4bffa9f1e669";
    private String authToken = "yyX7TUvdLSq5MlQ2BeoQkayJYTiXmZhAxO1C53Vk9srSsy9qUz6nDy";

    @Test
    public void should_access_token() throws IOException {
        LoginService loginService =
                ServiceGenerator.createService(LoginService.class, clientId, clientSecret);
        Call<AccessToken> call = loginService.getAccessToken("client_credentials");
        Response<AccessToken> response = call.execute();
        AccessToken accessToken = response.body();

        System.out.println(response.message());
        System.out.println(accessToken.getAccess_token());
        System.out.println(accessToken.getToken_type());
    }

    @Test
    public void should_create_instant_datetime() {
        System.out.println(TimestampUtils.getISO8601StringForCurrentDate());
    }

    @Test
    public void should_create_string_to_sign() {
        String stringToSign = HMACUtils.createStringToSign("lIWOt2p29grUo59bedBUrBY3pnzqQX544LzYPohcGHOuwn8AUEdUKS", "2016-02-03T10:00:00.000+07:00", "/banking/v2/corporates/BCAAPI2016/accounts/0201245680/statements?EndDate=2016-09-01&StartDate=2016-09-01", "GET", "");
//        System.out.println(stringToSign);

        Assert.assertEquals("GET:/banking/v2/corporates/BCAAPI2016/accounts/0201245680/statements?EndDate=2016-09-01&StartDate=2016-09-01:lIWOt2p29grUo59bedBUrBY3pnzqQX544LzYPohcGHOuwn8AUEdUKS:e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855:2016-02-03T10:00:00.000+07:00", stringToSign);

        String signature = HMACUtils.calculateSignature("22a2d25e-765d-41e1-8d29-da68dcb5698b", stringToSign);
        Assert.assertEquals("3ac124303746d222387d4398dddf33201a384aa22137aa08f4d9843c6f467a48", signature);
    }

    @Test
    public void should_access_balance_information() throws IOException {
        BusinessBankingService service =
                ServiceGenerator.createRequestService(BusinessBankingService.class, authToken);
        Call<Object> call = service.getBalanceInformation("BCAAPI2016");
        Response<Object> response = call.execute();

//        System.out.println(response.errorBody().string());
        System.out.println(response.body());
    }
}
