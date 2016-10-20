package com.foodspot.root.myapp.HttpService.Comment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11/09/16.
 */
public class CommentRequest {

    @SerializedName("id_comment")
    private String id_comment;

    @SerializedName("isi_comment")
    private String isi_comment;


    @SerializedName("id_user")
    private String id_user;

    @SerializedName("nama_lengkap")
    private String nama_lengkap;

    @SerializedName("picture_profile")
    private String picture_profile;

    @SerializedName("type_user")
    private String type_user;

    @SerializedName("tanggal_entri_comment")
    private String tanggal_entri_comment;

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }

    public String getIsi_comment() {
        return isi_comment;
    }

    public void setIsi_comment(String isi_comment) {
        this.isi_comment = isi_comment;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getPicture_profile() {
        return picture_profile;
    }

    public void setPicture_profile(String picture_profile) {
        this.picture_profile = picture_profile;
    }

    public String getType_user() {
        return type_user;
    }

    public void setType_user(String type_user) {
        this.type_user = type_user;
    }

    public String getTanggal_entri_comment() {
        return tanggal_entri_comment;
    }

    public void setTanggal_entri_comment(String tanggal_entri_comment) {
        this.tanggal_entri_comment = tanggal_entri_comment;
    }
}
