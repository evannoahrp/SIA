package com.example.sia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sia.apihelper.BaseApiService;
import com.example.sia.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText txtUser;
    EditText txtPassword;
    Button btnLogin;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPrefManager = new SharedPrefManager(this);
        // skip login jika user sudah login
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(Login.this, Home.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });

    }

    private void requestLogin() {
        mApiService.loginRequest(txtUser.getText().toString(), txtPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                assert response.body() != null;
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if (jsonResults.getString("error").equals("false")) {
                                    // jika login berhasil maka data nim yang ada di response API
                                    // akan diparsing ke activity selanjutnya
                                    Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();
                                    String mKode = jsonResults.getJSONObject("dosen").getString("kode_pegawai");
                                    String mUser = jsonResults.getJSONObject("dosen").getString("user");

                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KODE, mKode);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_USER, mUser);
                                    // Shared Pref berfungsi untuk menjadi trigger session login
                                    sharedPrefManager.saveSPBoolean(true);
                                    Log.e("kodepagawai",sharedPrefManager.getSPKode());
                                    startActivity(new Intent(mContext, Home.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                    //Intent intent = new Intent(mContext, MainActivity.class);
                                    //intent.putExtra("result_nim", mNim);
                                    //startActivity(intent);
                                } else {
                                    // jika login gagal
                                    String error_msg = jsonResults.getString("error_msg");
                                    Toast.makeText(mContext, error_msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }
}
