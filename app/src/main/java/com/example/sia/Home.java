package com.example.sia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView kode_pegawai, tvResultUser;

    Button btnLogout, btnBiodata, btnUbahPassword;

    SharedPrefManager sharedPrefManager;

    BaseApiService mApiService;

    Context mContext;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        kode_pegawai = findViewById(R.id.tvResultKode);
        tvResultUser = findViewById(R.id.tvResultUser);
        btnLogout = findViewById(R.id.btnLogout);
        btnBiodata = findViewById(R.id.btnBiodata);
        btnUbahPassword = findViewById(R.id.btnUbahPassword);

        mContext = this;

        mApiService = UtilsApi.getAPIService();

        sharedPrefManager = new SharedPrefManager(this);
        kode_pegawai.setText(sharedPrefManager.getSPKode());
        tvResultUser.setText(sharedPrefManager.getSPUser());

        btnLogout.setOnClickListener(this);
        btnBiodata.setOnClickListener(this);
        btnUbahPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnBiodata) {
            loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
            getBiodata();
        }
        if (v == btnLogout) {
            konfirmasiLogout();
        }
        if (v == btnUbahPassword) {
            Intent intent = new Intent(this, UbahPassword.class);
            startActivity(intent);
        }
    }

    private void konfirmasiLogout() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Apakah yakin ingin keluar?");

        alertBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefManager.saveSPBoolean(false);
                        startActivity(new Intent(Home.this, Login.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                });

        alertBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private void getBiodata() {
        mApiService.biodataRequest(sharedPrefManager.getSPKode()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        assert response.body() != null;
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getString("error").equals("false")) {

                            Intent intent = new Intent(mContext, BiodataDosen.class);
                            Bundle extras = new Bundle();

                            String mKodePgw = kode_pegawai.getText().toString();
                            String mNamaPgw = jsonResults.getJSONObject("biodata").getString("nama_pegawai");
                            String mGlrDpn = jsonResults.getJSONObject("biodata").getString("glr_dpn");
                            String mGlrBlk = jsonResults.getJSONObject("biodata").getString("glr_blk");
                            String mNik = jsonResults.getJSONObject("biodata").getString("nik");
                            String mNpwp = jsonResults.getJSONObject("biodata").getString("npwp");
                            String mAlamatSkr = jsonResults.getJSONObject("biodata").getString("alamat_skr");
                            String mTelpRumah = jsonResults.getJSONObject("biodata").getString("telp_rumah");
                            String mNoHp1 = jsonResults.getJSONObject("biodata").getString("no_hp1");
                            String mEmail1 = jsonResults.getJSONObject("biodata").getString("email1");
                            String mTempatLahir = jsonResults.getJSONObject("biodata").getString("tempat_lahir");
                            String mTglLahir = jsonResults.getJSONObject("biodata").getString("tgl_lahir");
                            String mKodeSex = jsonResults.getJSONObject("biodata").getString("kode_sex");
                            String mKodeStatusKeluar = jsonResults.getJSONObject("biodata").getString("kode_status_keluar");
                            String mKodeStatusPegawai = jsonResults.getJSONObject("biodata").getString("kode_status_pegawai");
                            String nNidn = jsonResults.getJSONObject("biodata").getString("nidn");
                            String mAlamatKtp = jsonResults.getJSONObject("biodata").getString("alamat_ktp");
                            String mEmail2 = jsonResults.getJSONObject("biodata").getString("email2");
                            String mNoHp2 = jsonResults.getJSONObject("biodata").getString("no_hp2");

                            extras.putString("kode_pegawai", mKodePgw);
                            extras.putString("nama_pegawai", mNamaPgw);
                            extras.putString("glr_dpn", mGlrDpn);
                            extras.putString("glr_blk", mGlrBlk);
                            extras.putString("nik", mNik);
                            extras.putString("npwp", mNpwp);
                            extras.putString("alamat_skr", mAlamatSkr);
                            extras.putString("telp_rumah", mTelpRumah);
                            extras.putString("no_hp1", mNoHp1);
                            extras.putString("email1", mEmail1);
                            extras.putString("tempat_lahir", mTempatLahir);
                            extras.putString("tgl_lahir", mTglLahir);
                            extras.putString("kode_sex", mKodeSex);
                            extras.putString("kode_status_keluar", mKodeStatusKeluar);
                            extras.putString("kode_status_pegawai", mKodeStatusPegawai);
                            extras.putString("nidn", nNidn);
                            extras.putString("alamat_ktp", mAlamatKtp);
                            extras.putString("email2", mEmail2);
                            extras.putString("no_hp2", mNoHp2);

                            intent.putExtras(extras);

                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
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
