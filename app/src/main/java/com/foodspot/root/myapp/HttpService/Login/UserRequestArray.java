package com.foodspot.root.myapp.HttpService.Login;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 04/09/16.
 */
public class UserRequestArray {
    @SerializedName("user")
    public ArrayList<UserRequest> resultUser;
}
