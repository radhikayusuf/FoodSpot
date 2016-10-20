package com.foodspot.root.myapp.HttpService.Timeline;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 04/09/16.
 */
public class TimelineRequest {

    
    public TimelineRequest() {
    }
    
    @SerializedName("ids")
    private String ids;

    @SerializedName("id_user")
    private String id_lestoran;

    @SerializedName("nama_lengkap")
    private String nama_lengkap;

    @SerializedName("picture_profile")
    private String picture_profile;

    @SerializedName("isi_status")
    private String isi_status;

    @SerializedName("judul_status")
    private String judul_status;

    @SerializedName("gambar_status")
    private String gambar_status;

    @SerializedName("tanggal_entri")
    private String tanggal_entri;

    @SerializedName("num_like")
    private String num_like;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getId_lestoran() {
        return id_lestoran;
    }

    public void setId_lestoran(String id_lestoran) {
        this.id_lestoran = id_lestoran;
    }

    public String get_Nama_Lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_dagangan) {
        this.nama_lengkap = nama_dagangan;
    }

    public String getIsi_status() {
        return isi_status;
    }

    public void setIsi_status(String isi_status) {
        this.isi_status = isi_status;
    }

    public String getGambar_status() {
        return gambar_status;
    }

    public void setGambar_status(String gambar_status) {
        this.gambar_status = gambar_status;
    }

    public String getTanggal_entri() {
        return tanggal_entri;
    }

    public void setTanggal_entri(String tanggal_entri) {
        this.tanggal_entri = tanggal_entri;
    }

    public String getNum_like() {
        return num_like;
    }

    public void setNum_like(String num_like) {
        this.num_like = num_like;
    }

    public String getPicture_profile() {
        return picture_profile;
    }

    public void setPicture_profile(String picture_profile) {
        this.picture_profile = picture_profile;
    }

    public String getJudul_status() {
        return judul_status;
    }

    public void setJudul_status(String judul_status) {
        this.judul_status = judul_status;
    }
}
