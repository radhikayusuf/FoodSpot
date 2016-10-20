package com.foodspot.root.myapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodspot.root.myapp.CommentActivity;
import com.foodspot.root.myapp.HttpService.Timeline.TimelineRequest;
import com.foodspot.root.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 22/08/16.
 */
public class AdapterDataContent extends RecyclerView.Adapter<AdapterDataContent.ViewHolder> {
    List<TimelineRequest> contents;
    private Context context;
    SharedPreferences sharedPreferences;
    static String id_user_orang;
    String hasil_like;
    static ImageButton ib;

    public AdapterDataContent(List<TimelineRequest> contents) {
        this.contents = contents;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        sharedPreferences = context.getSharedPreferences("data",0);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TimelineRequest content = contents.get(position);
        //hasil_like ="";
        holder.cardView_header_text.setText(content.getJudul_status());
        holder.cardview_name_profile.setText(content.get_Nama_Lengkap());
        holder.tvDate.setText(content.getTanggal_entri());

        Log.d("ratenya"," "+hasil_like);

        holder.btnRating.setVisibility(View.GONE);

        holder.cardView_tvLikes.setText(content.getNum_like()+" Likes This");

        holder.btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_user_orang = content.getIds();
               // new backgroundTask().execute();
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goComment(content.getId_lestoran(),content.getIds(),content.getJudul_status(),content.getIsi_status(),content.getPicture_profile(), content.getGambar_status(),content.getNum_like(), content.get_Nama_Lengkap());
            }
        });

        holder.img_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goComment(content.getId_lestoran(),content.getIds(),content.getJudul_status(),content.getIsi_status(),content.getPicture_profile(), content.getGambar_status(),content.getNum_like(), content.get_Nama_Lengkap());

            }
        });

        //holder
        //holder

//        new backgroundTask().execute();



        //Log.d("link ", content.getLink_img_content());
        Picasso.with(context).load("http://morefoods.hol.es/img/"+content.getGambar_status()).into(holder.img_content);
        Picasso.with(context).load("http://morefoods.hol.es/img/"+content.getPicture_profile()).into(holder.img_profile);



    }

    @Override
    public int getItemCount() {
        return contents.size();
    }


    void goComment(String id_user_orang,String ids, String judul, String isi, String pic_dp, String pic_content, String numlike, String nama){
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("ids",ids);
        intent.putExtra("id_user_orang",id_user_orang);
        intent.putExtra("judul",judul);
        intent.putExtra("isi",isi);
        intent.putExtra("pic_dp",pic_dp);
        intent.putExtra("pic_content",pic_content);
        intent.putExtra("num_like",numlike);
        intent.putExtra("nama",nama);
        context.startActivity(intent);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardview_name_profile, cardView_header_text, tvDate, cardView_tvLikes;
        CircleImageView img_profile;
        ImageView img_content;
        ImageButton btnRating;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_content);
            tvDate = (TextView)itemView.findViewById(R.id.cardview_date_content);
            cardview_name_profile = (TextView)itemView.findViewById(R.id.cardview_name_profile);
            img_profile = (CircleImageView)itemView.findViewById(R.id.cardview_img_profile);
            img_content = (ImageView)itemView.findViewById(R.id.cardview_img_content);
            cardView_header_text = (TextView)itemView.findViewById(R.id.cardview_text_header);
            cardView_tvLikes = (TextView)itemView.findViewById(R.id.card_etLike);
            btnRating = (ImageButton) itemView.findViewById(R.id.card_btnLike);
        }
    }



}
