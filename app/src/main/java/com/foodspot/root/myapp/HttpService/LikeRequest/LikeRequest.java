package com.foodspot.root.myapp.HttpService.LikeRequest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11/09/16.
 */
public class LikeRequest {
    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
