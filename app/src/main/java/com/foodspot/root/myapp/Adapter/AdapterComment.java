package com.foodspot.root.myapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodspot.root.myapp.HttpService.Comment.CommentRequest;
import com.foodspot.root.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 11/09/16.
 */
public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    List<CommentRequest> commentRequests= new ArrayList<>();
    private Context context;
    SharedPreferences sharedPreferences;

    public AdapterComment(List<CommentRequest> contents, Context context) {
        this.commentRequests = contents;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        sharedPreferences = context.getSharedPreferences("data",0);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentRequest komentar = commentRequests.get(position);

        System.out.println("namaadapter"+komentar.getNama_lengkap());

        holder.nama.setText(komentar.getNama_lengkap());
            holder.comment.setText(komentar.getIsi_comment());



        Picasso.with(context)
                .load("http://morefoods.hol.es/img/"+komentar.getPicture_profile())
                .into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return commentRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView nama, comment;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.comment_pic_other);
            nama = (TextView) itemView.findViewById(R.id.comment_name_other);
            comment = (TextView) itemView.findViewById(R.id.comment_value_other);
        }
    }
}
