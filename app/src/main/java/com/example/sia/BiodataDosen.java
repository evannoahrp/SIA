package com.example.sia;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sia.apihelper.BaseApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.StringTokenizer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BiodataDosen extends AppCompatActivity {

    TextView textKodePegawai,textNamaPegawai,textJenisKelamin, textTanggalLahir;

    EditText textGelarDepan, textGelarBelakang, textNik, textNpwp, textAlamatSkr,
            textTelpRumah, textNoHp1, textEmail1, textTempatLahir, textStatusKeluar,
            textStatusPegawai, textNidn, textAlamatKtp, textEmail2, textNoHp2;

    Button button_tgl, button_ubah;

    Context mContext;

    ProgressDialog loading;

    DatePickerDialog dpd;

    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);

        textKodePegawai = findViewById(R.id.textKodePegawai);
        textNamaPegawai = findViewById(R.id.textNamaPegawai);
        textGelarDepan = findViewById(R.id.textGelarDepan);
        textGelarBelakang = findViewById(R.id.textGelarBelakang);
        textNik = findViewById(R.id.textNik);
        textNpwp = findViewById(R.id.textNpwp);
        textAlamatSkr = findViewById(R.id.textAlamatSkr);
        textTelpRumah = findViewById(R.id.textTelpRumah);
        textNoHp1 = findViewById(R.id.textNoHp1);
        textEmail1 = findViewById(R.id.textEmail1);
        textTempatLahir = findViewById(R.id.textTempatLahir);
        textTanggalLahir = findViewById(R.id.textTanggalLahir);
        textJenisKelamin = findViewById(R.id.textJenisKelamin);
        textStatusKeluar = findViewById(R.id.textStatusKeluar);
        textStatusPegawai = findViewById(R.id.textStatusPegawai);
        textNidn = findViewById(R.id.textNidn);
        textAlamatKtp = findViewById(R.id.textAlamatKtp);
        textEmail2 = findViewById(R.id.textEmail2);
        textNoHp2 = findViewById(R.id.textNoHp2);

        button_tgl = findViewById(R.id.button_tgl);
        button_ubah = findViewById(R.id.button_ubah);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        assert extras != null;
        String mKodePgw = extras.getString("kode_pegawai");
        textKodePegawai.setText(mKodePgw);

        String mNamaPgw = extras.getString("nama_pegawai");
        textNamaPegawai.setText(mNamaPgw);

        String mGlrDpn = extras.getString("glr_dpn");
        textGelarDepan.setText(mGlrDpn);

        String mGlrBlk = extras.getString("glr_blk");
        textGelarBelakang.setText(mGlrBlk);

        String mNik = extras.getString("nik");
        textNik.setText(mNik);

        String mNpwp = extras.getString("npwp");
        textNpwp.setText(mNpwp);

        String mAlamatSkr = extras.getString("alamat_skr");
        textAlamatSkr.setText(mAlamatSkr);

        String mTelpRumah = extras.getString("telp_rumah");
        textTelpRumah.setText(mTelpRumah);

        String mNoHp1 = extras.getString("no_hp1");
        textNoHp1.setText(mNoHp1);

        String mEmail1 = extras.getString("email1");
        textEmail1.setText(mEmail1);

        String mTempatLahir = extras.getString("tempat_lahir");
        textTempatLahir.setText(mTempatLahir);

        String mTglLahir = extras.getString("tgl_lahir");
        textTanggalLahir.setText(mTglLahir);

        StringTokenizer st = new StringTokenizer(mTglLahir, "-");
        final int year = Integer.parseInt(st.nextToken());
        final int month = Integer.parseInt(st.nextToken()) - 1;
        final int day = Integer.parseInt(st.nextToken());

        button_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd = new DatePickerDialog(BiodataDosen.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textTanggalLahir.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        String mKodeSex = extras.getString("kode_sex");
        textJenisKelamin.setText(mKodeSex);

        String mKodeStatusKeluar = extras.getString("kode_status_keluar");
        textStatusKeluar.setText(mKodeStatusKeluar);

        String mKodeStatusPegawai = extras.getString("kode_status_pegawai");
        textStatusPegawai.setText(mKodeStatusPegawai);

        String nNidn = extras.getString("nidn");
        textNidn.setText(nNidn);

        String mAlamatKtp = extras.getString("alamat_ktp");
        textAlamatKtp.setText(mAlamatKtp);

        String mEmail2 = extras.getString("email2");
        textEmail2.setText(mEmail2);

        String mNoHp2 = extras.getString("no_hp2");
        textNoHp2.setText(mNoHp2);

        button_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                ubahBiodata();
            }
        });
    }

    private void ubahBiodata(){
        mApiService.ubahbiodataRequest(textKodePegawai.getText().toString(),
                textGelarDepan.getText().toString(), textGelarBelakang.getText().toString(),
                textNik.getText().toString(), textNpwp.getText().toString(),
                textAlamatSkr.getText().toString(), textTelpRumah.getText().toString(),
                textNoHp1.getText().toString(), textEmail1.getText().toString(),
                textTempatLahir.getText().toString(), textTanggalLahir.getText().toString(),
                textStatusKeluar.getText().toString(), textStatusPegawai.getText().toString(),
                textNidn.getText().toString(), textAlamatKtp.getText().toString(),
                textEmail2.getText().toString(), textNoHp2.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
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
