package com.foodspot.root.myapp.HttpService.LovedRestaurant;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 07/09/16.
 */
public class Lovedrequest {
    public Lovedrequest() {
    }

    @SerializedName("id_lestoran")
    private String id_lestoran;

    @SerializedName("id_pemilik")
    private String id_pemilik;

    @SerializedName("nama_lengkap")
    private String nama_dagangan;

    @SerializedName("status_buka")
    private String status_buka;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("picture")
    private String dagangan_picture;

    @SerializedName("background")
    private String dagangan_background;

    @SerializedName("tanggal_entri")
    private String tanggal_entri;

    public String getId_lestoran() {
        return id_lestoran;
    }

    public void setId_lestoran(String id_lestoran) {
        this.id_lestoran = id_lestoran;
    }

    public String getId_pemilik() {
        return id_pemilik;
    }

    public void setId_pemilik(String id_pemilik) {
        this.id_pemilik = id_pemilik;
    }

    public String getNama_dagangan() {
        return nama_dagangan;
    }

    public void setNama_dagangan(String nama_dagangan) {
        this.nama_dagangan = nama_dagangan;
    }

    public String getStatus_buka() {
        return status_buka;
    }

    public void setStatus_buka(String status_buka) {
        this.status_buka = status_buka;
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDagangan_picture() {
        return dagangan_picture;
    }

    public void setDagangan_picture(String dagangan_picture) {
        this.dagangan_picture = dagangan_picture;
    }

    public String getDagangan_background() {
        return dagangan_background;
    }

    public void setDagangan_background(String dagangan_background) {
        this.dagangan_background = dagangan_background;
    }

    public String getTanggal_entri() {
        return tanggal_entri;
    }

    public void setTanggal_entri(String tanggal_entri) {
        this.tanggal_entri = tanggal_entri;
    }
}
