package com.foodspot.root.myapp.HttpService.Image;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 10/09/16.
 */
public class ImageRequestArray {
    @SerializedName("file")
    public ArrayList<ImageRequest> file_result;
}
