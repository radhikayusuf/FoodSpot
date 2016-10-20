package com.foodspot.root.myapp.HttpService.Login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 04/09/16.
 */
public class UserRequest {
    public UserRequest() {
    }

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("username")
    private String username;

    @SerializedName("nama_lengkap")
    private String nama_lengkap;

    @SerializedName("email")
    private String email;

    @SerializedName("no_telepon")
    private String no_telepon;

    @SerializedName("text_status")
    private String text_status;

    @SerializedName("picture_profile")
    private String picture_profile;

    @SerializedName("picture_background")
    private String picture_backgournd;

    @SerializedName("tanggal_entri")
    private String tanggal_entri;

    @SerializedName("type_user")
    private String type_user;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("type_dagangan")
    private String type_dagangan;

    @SerializedName("status_dagangan")
    private String status_dagangan;


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText_status() {
        return text_status;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public void setText_status(String text_status) {
        this.text_status = text_status;
    }

    public String getPicture_profile() {
        return picture_profile;
    }

    public void setPicture_profile(String picture_profile) {
        this.picture_profile = picture_profile;
    }

    public String getPicture_backgournd() {
        return picture_backgournd;
    }

    public void setPicture_backgournd(String picture_backgournd) {
        this.picture_backgournd = picture_backgournd;
    }

    public String getTanggal_entri() {
        return tanggal_entri;
    }

    public void setTanggal_entri(String tanggal_entri) {
        this.tanggal_entri = tanggal_entri;
    }


    public String getType_user() {
        return type_user;
    }

    public void setType_user(String type_user) {
        this.type_user = type_user;
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

    public String getType_dagangan() {
        return type_dagangan;
    }

    public void setType_dagangan(String type_dagangan) {
        this.type_dagangan = type_dagangan;
    }

    public String getStatus_dagangan() {
        return status_dagangan;
    }

    public void setStatus_dagangan(String status_dagangan) {
        this.status_dagangan = status_dagangan;
    }
}
