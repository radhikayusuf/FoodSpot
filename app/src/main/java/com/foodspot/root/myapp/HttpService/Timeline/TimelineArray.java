package com.foodspot.root.myapp.HttpService.Timeline;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 04/09/16.
 */
public class TimelineArray {
    @SerializedName("status")
    public ArrayList<TimelineRequest> resultTimeline;
}
