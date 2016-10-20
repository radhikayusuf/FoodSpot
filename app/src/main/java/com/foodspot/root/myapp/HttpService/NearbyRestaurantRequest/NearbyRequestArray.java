package com.foodspot.root.myapp.HttpService.NearbyRestaurantRequest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 10/09/16.
 */
public class NearbyRequestArray {
    @SerializedName("restoran")
    public ArrayList<NearbyRequest> nearbyRequestsList;
}
