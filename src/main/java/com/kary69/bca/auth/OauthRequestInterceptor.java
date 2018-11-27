package com.kary69.bca.auth;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OauthRequestInterceptor implements Interceptor {
    private String apiKey = "5d61db73-9284-4ec7-b914-6bcfd30059c9";
    private String apiSecret = "040f68bf-14d8-46b6-a9dc-24e25b163d2e";
    private String authToken;

    public OauthRequestInterceptor(String token, String apiKey, String apiSecret) {
        this.authToken = token;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        String timestamp = TimestampUtils.getISO8601StringForCurrentDate();
        String relativeUrl = original.url().encodedPath();

        String httpMethod = original.method();
        String requestBody = "";

        if (original.body() != null) requestBody = original.body().toString();
        System.out.println(requestBody);
        // TODO need to refactor this into utility, just need to call this once
        String stringToSign = HMACUtils.createStringToSign(authToken, timestamp, relativeUrl, httpMethod, requestBody);

        System.out.println(stringToSign);

        String signature = HMACUtils.calculateSignature(apiSecret, stringToSign);

        System.out.println(signature);

        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .header("Origin", "yourdomain.com")
                .header("X-BCA-Key", apiKey)
                .header("X-BCA-Timestamp", timestamp)
                .header("X-BCA-Signature", signature);

        Request request = builder.build();

        return chain.proceed(request);
    }


}