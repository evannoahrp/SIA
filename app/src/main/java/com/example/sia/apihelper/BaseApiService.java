package com.example.sia.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    // Fungsi untuk memanggil API http://localhost/sia/Login.php
    @FormUrlEncoded
    @POST("Login.php")
    Call<ResponseBody> loginRequest(@Field("user") String user,
                                    @Field("password") String password);


    // Fungsi untuk memanggil API http://localhost/sia/UbahPassword.php
    @FormUrlEncoded
    @POST("UbahPassword.php")
    Call<ResponseBody> ubahPassRequest(@Field("user") String user,
                                       @Field("passwordlama") String passwordlama,
                                       @Field("passwordbaru") String passwordbaru,
                                       @Field("konfpassword") String konfpassword);

    // Fungsi untuk memanggil API http://localhost/sia/TampilBiodata.php
    @FormUrlEncoded
    @POST("TampilBiodata.php")
    Call<ResponseBody> tampilBiodataRequest(@Field("kode_pegawai") String kode_pegawai);

    // Fungsi untuk memanggil API http://localhost/sia/TampilJenisKelamin.php
    @FormUrlEncoded
    @POST("TampilJenisKelamin.php")
    Call<ResponseBody> tampilJenisKelaminRequest(@Field("kode_pegawai") String kode_pegawai);

    // Fungsi untuk memanggil API http://localhost/sia/TampilStatusKeluar.php
    @FormUrlEncoded
    @POST("TampilStatusKeluar.php")
    Call<ResponseBody> tampilStatusKeluarRequest(@Field("kode_pegawai") String kode_pegawai);

    // Fungsi untuk memanggil API http://localhost/sia/TampilAllStatusKeluar.php
    @GET("TampilAllStatusKeluar.php")
    Call<ResponseBody> getAllStatusKeluar();

    // Fungsi untuk memanggil API http://localhost/sia/TampilStatusPegawai.php
    @FormUrlEncoded
    @POST("TampilStatusPegawai.php")
    Call<ResponseBody> tampilStatusPegawaiRequest(@Field("kode_pegawai") String kode_pegawai);

    // Fungsi untuk memanggil API http://localhost/sia/UpdateBiodata.php
    @FormUrlEncoded
    @POST("UpdateBiodata.php")
    Call<ResponseBody> updateBiodataRequest(@Field("kode_pegawai") String kode_pegawai,
                                          @Field("glr_dpn") String glr_dpn,
                                          @Field("glr_blk") String glr_blk,
                                          @Field("nik") String nik,
                                          @Field("npwp") String npwp,
                                          @Field("alamat_skr") String alamat_skr,
                                          @Field("telp_rumah") String telp_rumah,
                                          @Field("no_hp1") String no_hp1,
                                          @Field("email1") String email1,
                                          @Field("tempat_lahir") String tempat_lahir,
                                          @Field("tgl_lahir") String tgl_lahir,
                                          @Field("kode_status_keluar") String kode_status_keluar,
                                          @Field("kode_status_pegawai") String kode_status_pegawai,
                                          @Field("nidn") String nidn,
                                          @Field("alamat_ktp") String alamat_ktp,
                                          @Field("email2") String email2,
                                          @Field("no_hp2") String no_hp2);
}
