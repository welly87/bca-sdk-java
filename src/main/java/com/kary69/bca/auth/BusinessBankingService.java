package com.kary69.bca.auth;

import retrofit2.Call;
import retrofit2.http.*;

public interface BusinessBankingService {
    @GET("banking/v3/corporates/{coorporate_id}/accounts/0201245680,0063001004,1111111111")
    Call<String> getBalanceInformation(
            @Path("coorporate_id") String coorporateId);
}
