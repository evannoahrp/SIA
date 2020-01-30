package com.example.sia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

class SharedPrefManager {

    private static final String SP_DOSEN_APP = "spDosenApp";

    static final String SP_KODE = "spKode";

    static final String SP_USER = "spUser";

    private static final String SP_SUDAH_LOGIN = "spSudahLogin";

    private SharedPreferences sp;

    private SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_DOSEN_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    void saveSPBoolean(boolean value){
        spEditor.putBoolean(SharedPrefManager.SP_SUDAH_LOGIN, value);
        spEditor.commit();
    }

    String getSPKode(){
        return sp.getString(SP_KODE, "");
    }

    String getSPUser(){
        return sp.getString(SP_USER, "");
    }

    Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

}
