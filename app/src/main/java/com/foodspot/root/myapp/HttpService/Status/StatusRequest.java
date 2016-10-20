package com.foodspot.root.myapp.HttpService.Status;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 10/09/16.
 */
public class StatusRequest {

    @SerializedName("ids")
    private String ids;

    @SerializedName("judul_status")
    private String judul_status;

    @SerializedName("isi_status")
    private String isi_status;

    @SerializedName("gambar_status")
    private String gambar_status;

    @SerializedName("num_like")
    private String num_like;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getJudul_status() {
        return judul_status;
    }

    public void setJudul_status(String judul_status) {
        this.judul_status = judul_status;
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

    public String getNum_like() {
        return num_like;
    }

    public void setNum_like(String num_like) {
        this.num_like = num_like;
    }
}
