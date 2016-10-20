package com.foodspot.root.myapp.HttpService.Status;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 10/09/16.
 */
public class StatusRequestArray {
    @SerializedName("status")
    public ArrayList<StatusRequest> myStatus;
}
