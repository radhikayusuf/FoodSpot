package com.foodspot.root.myapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodspot.root.myapp.HttpService.LovedRestaurant.Lovedrequest;
import com.foodspot.root.myapp.MapsActivity;
import com.foodspot.root.myapp.ProfileActivity;
import com.foodspot.root.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 22/08/16.
 */
public class AdapterDataRestaurant extends RecyclerView.Adapter<AdapterDataRestaurant.ViewHolder> {

    List<Lovedrequest> restaurants;
    Context context;

    public AdapterDataRestaurant(List<Lovedrequest> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_profile, parent, false);
        ViewHolder viewHandler = new ViewHolder(view);
        return viewHandler;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Lovedrequest restaurant = restaurants.get(position);
        System.out.println("Nama dagangan "+restaurant.getNama_dagangan());
        holder.Name.setText(restaurant.getNama_dagangan());
        Picasso.with(context).load("http://morefoods.hol.es/img/"+restaurant.getDagangan_background()).into(holder.img_background);
        Picasso.with(context).load("http://morefoods.hol.es/img/"+restaurant.getDagangan_picture()).into(holder.pic_profile);

        holder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("id", "satu");
                i.putExtra("id_user", restaurant.getId_lestoran());
                i.putExtra("lokasi", restaurant.getLatitude()+","+restaurant.getLongitude());
                i.putExtra("namasatu",  restaurant.getNama_dagangan());
                i.putExtra("pic_dpsatu", restaurant.getDagangan_picture());
                i.putExtra("pic_bgsatu", restaurant.getDagangan_background());
                context.startActivity(i);

                Toast.makeText(context, "Lokasi"+ restaurant.getLatitude()+","+restaurant.getLongitude(),Toast.LENGTH_LONG).show();
            }
        });

        holder.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);

                i.putExtra("id_user", restaurant.getId_pemilik());
                i.putExtra("nama",  restaurant.getNama_dagangan());
                i.putExtra("pic_dp", restaurant.getDagangan_picture());
                i.putExtra("pic_bg", restaurant.getDagangan_background());
                i.putExtra("status", restaurant.getStatus_buka());

                context.startActivity(i);
            }
        });
        //Picasso.with(context).load(R.drawable.dhika).centerCrop().into(holder.pic_profile);
        //Picasso.with(context).load("http://morefoods.hol.es/img/"+content.getLink_img_content()).into(holder.img_content);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView pic_profile;
        ImageView img_background;
        TextView Name;
        Button btnLocation, btnHome;

        public ViewHolder(View itemView) {
            super(itemView);
            pic_profile = (CircleImageView) itemView.findViewById(R.id.cardviewProfile_img_profile);
            img_background = (ImageView) itemView.findViewById(R.id.cardProfile_Background);
            btnLocation = (Button) itemView.findViewById(R.id.cardProfile_btn_Location);
            Name = (TextView) itemView.findViewById(R.id.cardProfile_Nama);
            btnHome = (Button) itemView.findViewById(R.id.btnHome);
        }
    }
}
