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

import com.foodspot.root.myapp.ProfileActivity;
import com.foodspot.root.myapp.R;
import com.foodspot.root.myapp.dao.LovedRestaurantdao;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 10/09/16.
 */
public class AdapterNearbyRestaurant extends RecyclerView.Adapter<AdapterNearbyRestaurant.ViewHolder>{
    List<LovedRestaurantdao> lovedRestaurantdaos;
    Context context;

    public AdapterNearbyRestaurant(List<LovedRestaurantdao> lovedRestaurantdaos, Context context) {
        this.lovedRestaurantdaos = lovedRestaurantdaos;
        this.context = context;
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
        final LovedRestaurantdao restaurant = lovedRestaurantdaos.get(position);
        System.out.println("Nama dagangan "+restaurant.getName());
        holder.Name.setText(restaurant.getName());
        Picasso.with(context).load("http://morefoods.hol.es/img/"+restaurant.getLink_img_bg()).into(holder.img_background);
        Picasso.with(context).load("http://morefoods.hol.es/img/"+restaurant.getLink_img_profile()).into(holder.pic_profile);

        holder.btnLocation.setVisibility(View.GONE);

        holder.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);

                i.putExtra("id_user", restaurant.getId());
                i.putExtra("nama",  restaurant.getName());
                i.putExtra("pic_dp", restaurant.getLink_img_profile());
                i.putExtra("pic_bg", restaurant.getLink_img_bg());
                i.putExtra("status", "1");

                context.startActivity(i);
            }
        });
        //Picasso.with(context).load(R.drawable.dhika).centerCrop().into(holder.pic_profile);
        //Picasso.with(context).load("http://morefoods.hol.es/img/"+content.getLink_img_content()).into(holder.img_content);
    }

    @Override
    public int getItemCount() {
        return lovedRestaurantdaos.size();
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
