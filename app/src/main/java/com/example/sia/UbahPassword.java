package com.example.sia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class UbahPassword extends AppCompatActivity {

    TextView txtUser;

    EditText txtPassLama, txtPassBaru, txtKonfirmasiPass;

    Button btnUbahPass;

    SharedPrefManager sharedPrefManager;

    Context mContext;

    ProgressDialog loading;

    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        txtUser = findViewById(R.id.txtUser);
        txtPassLama = findViewById(R.id.txtPassLama);
        txtPassBaru = findViewById(R.id.txtPassBaru);
        txtKonfirmasiPass = findViewById(R.id.txtKonfirmasiPass);
        btnUbahPass = findViewById(R.id.btnUbahPass);

        sharedPrefManager = new SharedPrefManager(this);
        txtUser.setText(sharedPrefManager.getSPUser());

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        btnUbahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                ubahPassword();
            }
        });
    }

    private void ubahPassword() {
        mApiService.ubahPassRequest(txtUser.getText().toString(), txtPassLama.getText().toString(), txtPassBaru.getText().toString(),
                txtKonfirmasiPass.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        assert response.body() != null;
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getString("error").equals("false")) {

                            String message = jsonResults.getString("message");
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(mContext, Home.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        } else {
                            // jika gagal ubah password
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
