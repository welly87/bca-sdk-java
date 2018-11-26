package com.kary69.bca.auth;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String API_BASE_URL = "https://sandbox.bca.co.id/api/";

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
        // TODO need to activate this one
//        if (!TextUtils.isEmpty(clientId)
//                && !TextUtils.isEmpty(clientSecret)) {
//            String authToken = Credentials.basic(clientId, clientSecret);
//            return createService(serviceClass, authToken);
//        }
//        return createService(serviceClass, null, null);
        String authToken = Credentials.basic(clientId, clientSecret);
        return createService(serviceClass, authToken);

    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
//        if (!TextUtils.isEmpty(authToken)) {
//            AuthenticationInterceptor interceptor =
//                    new AuthenticationInterceptor(authToken);
//
//            if (!httpClient.interceptors().contains(interceptor)) {
//                httpClient.addInterceptor(interceptor);
//
//                builder.client(httpClient.build());
//                retrofit = builder.build();
//            }
//        }
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
}