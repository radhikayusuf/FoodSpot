package com.foodspot.root.myapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.foodspot.root.myapp.CommentActivity;
import com.foodspot.root.myapp.HttpService.Status.StatusRequest;
import com.foodspot.root.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 10/09/16.
 */
public class AdapterMyStatus extends RecyclerView.Adapter<AdapterMyStatus.ViewHolder> {

    Context context;
    List<StatusRequest> statusRequests;
    String name, id_user_orang;


    public AdapterMyStatus(List<StatusRequest> statusRequests, @Nullable String img_dp_name, @Nullable String id_user_orang) {
        this.statusRequests = statusRequests;
        this.name = img_dp_name;
        this.id_user_orang = id_user_orang;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mystatus, parent, false);
        ViewHolder viewHandler = new ViewHolder(view);
        return viewHandler;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final StatusRequest status = statusRequests.get(position);
        Picasso.with(context)
                .load("http://morefoods.hol.es/img/"+status.getGambar_status())
                .centerCrop()
                .fit()
                .into(holder.imageView);

        holder.card_mystatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CommentActivity.class);
                i.putExtra("id_user_orang",id_user_orang);
                i.putExtra("ids", status.getIds());
                i.putExtra("judul",status.getJudul_status());
                i.putExtra("isi",status.getIsi_status());
                i.putExtra("pic_dp", name);
                i.putExtra("pic_content",status.getGambar_status());
                i.putExtra("num_like", status.getNum_like());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return statusRequests.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
       CardView card_mystatus;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            card_mystatus = (CardView) itemView.findViewById(R.id.id_card_mystatus);
            imageView = (ImageView) itemView.findViewById(R.id.bg_Card);
        }
    }
}

