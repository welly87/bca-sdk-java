package com.kary69.bca.auth;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String API_BASE_URL = "https://sandbox.bca.co.id/";
    private static final String apiKey = "5d61db73-9284-4ec7-b914-6bcfd30059c9";
    private static final String apiSecret = "040f68bf-14d8-46b6-a9dc-24e25b163d2e";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String clientId, String clientSecret) {
        String authToken = Credentials.basic(clientId, clientSecret);
        return createService(serviceClass, authToken);
    }



    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        Retrofit retrofit = null;

        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor(authToken);

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);

            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

    public static <S> S createRequestService(
            Class<S> serviceClass, final String authToken) {
        Retrofit retrofit = null;

        OauthRequestInterceptor interceptor =
                new OauthRequestInterceptor(authToken, apiKey, apiSecret);

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);

            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}