package com.example.sia.apihelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://172.23.3.25/sia/";

    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
