package com.example.sia.apihelper;

public class UtilsApi {

    static final String BASE_URL_API = "http://172.23.3.25/sia/";
    //static final String BASE_URL_API = "http://192.168.43.102/sia/";

    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
