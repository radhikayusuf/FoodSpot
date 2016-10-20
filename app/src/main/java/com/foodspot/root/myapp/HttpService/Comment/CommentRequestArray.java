package com.foodspot.root.myapp.HttpService.Comment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 11/09/16.
 */
public class CommentRequestArray {
    @SerializedName("comment")
    public ArrayList<CommentRequest> comment_result;
}
