package com.example.sia;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.StringTokenizer;

public class BiodataDosen extends AppCompatActivity {

    TextView textKodePegawai,textNamaPegawai,textJenisKelamin, textTanggalLahir;

    EditText textGelarDepan, textGelarBelakang, textNik, textNpwp, textAlamatSkr,
            textTelpRumah, textNoHp1, textEmail1, textTempatLahir, textStatusKeluar,
            textStatusPegawai, textNidn, textAlamatKtp, textEmail2, textNoHp2;

    Button button_tgl, button_ubah;

    DatePickerDialog dpd;

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

    }
}
