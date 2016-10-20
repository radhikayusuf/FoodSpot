package com.foodspot.root.myapp.HttpService.NearbyRestaurantRequest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 10/09/16.
 */
public class NearbyRequest {

    @SerializedName("id_user")
    private String id_user;

   @SerializedName("nama_lengkap")
    private String nama_lengkap;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private  String latitude;

    @SerializedName("picture_profile")
    private String picture_profile;

    @SerializedName("picture_background")
    private String picture_background;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
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

    public String getPicture_profile() {
        return picture_profile;
    }

    public void setPicture_profile(String picture_profile) {
        this.picture_profile = picture_profile;
    }

    public String getPicture_background() {
        return picture_background;
    }

    public void setPicture_background(String picture_background) {
        this.picture_background = picture_background;
    }
}
