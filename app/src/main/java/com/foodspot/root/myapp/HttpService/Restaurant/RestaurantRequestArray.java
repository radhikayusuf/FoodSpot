package com.foodspot.root.myapp.HttpService.Restaurant;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 11/09/16.
 */
public class RestaurantRequestArray {
    @SerializedName("restoran")
    public ArrayList<RestaurantRequest> hasilSearch;
}
