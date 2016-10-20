package com.foodspot.root.myapp.HttpService.Image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 10/09/16.
 */
public class ImageRequest {

    @SerializedName("file_name")
    private String file_name;

    @SerializedName("full_path")
    private String full_path;


    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFull_path() {
        return full_path;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }
}
