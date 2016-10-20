package com.foodspot.root.myapp.Fragments;


import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodspot.root.myapp.Adapter.AdapterMyStatus;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.foodspot.root.myapp.HttpService.Status.StatusRequest;
import com.foodspot.root.myapp.HttpService.Status.StatusRequestArray;
import com.foodspot.root.myapp.R;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment{


    String name, status;
    TextView tvProfileName,tvProfileStatus;
    View v;
    SharedPreferences sharedPreferences;
    CollapsingToolbarLayout toolbar_layout;
    FloatingActionButton floatingActionButton;
    CircleImageView circleImageView;
    String ID_USER =null;
    List<StatusRequest> statusRequest = new ArrayList<>();
    RecyclerView rc_mystatus;
    AdapterMyStatus adapterMyStatus;

    public Profile() {
        //this.name = name;
        //status = profile;
    }

    //TextView tvProfileName, tvProfileStatus;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar_layout = (CollapsingToolbarLayout) v.findViewById(R.id.fragment_toolbar_layout);
        //floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab_fragment_profile);
        circleImageView = (CircleImageView) v.findViewById(R.id.de_fragment_profile);
        rc_mystatus = (RecyclerView) v.findViewById(R.id.id_my_status);
        //initComponent(v);
        return v;
    }



    void initComponent(View v){
        tvProfileName = (TextView) v.findViewById(R.id.profile_name);
        tvProfileStatus = (TextView) v.findViewById(R.id.profile_status);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("data",0);



        ID_USER = sharedPreferences.getString("id_user","kosong");
        name = sharedPreferences.getString("nama","kosong");
        status = sharedPreferences.getString("status","kosong");

        System.out.println("foto" + sharedPreferences.getString("pic_profile","kosong"));
        System.out.println("foto bg " + sharedPreferences.getString("pic_bg","kosong"));



        Picasso.with(getActivity().getApplicationContext())
                .load("http://morefoods.hol.es/img/"+sharedPreferences.getString("pic_bg","kosong"))
                .into(new Target() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        toolbar_layout.setBackground(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

//        Picasso.with(getActivity().getApplicationContext())
//                .load("http://morefoods.hol.es/img/"+sharedPreferences.getString("pic_profile","kosong"))
//                .into(new Target() {
//                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        floatingActionButton.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
        Picasso.with(getActivity().getApplicationContext())
                    .load("http://morefoods.hol.es/img/"+sharedPreferences.getString("pic_profile","kosong"))
                    .into(circleImageView);

        initComponent(v);
        Log.d("nama", name);
        super.onCreate(savedInstanceState);
        tvProfileName.setText(name);
        tvProfileStatus.setText(status);
        new backgroundTask().execute();
        //tvProfileStatus.setText("Testing");
    }


    class backgroundTask extends AsyncTask<Void, Void, Void>{


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterMyStatus = new AdapterMyStatus(statusRequest, sharedPreferences.getString("pic_profile","kosong"), sharedPreferences.getString("id_user","kosong"));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_mystatus.getContext());
            rc_mystatus.setLayoutManager(layoutManager);
            rc_mystatus.setAdapter(adapterMyStatus);
            adapterMyStatus.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Status.php?api_key=restoran&action=getStatusById&id_user="+ID_USER;
            String response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);

            Log.d("response mystatus", response);

            StatusRequestArray statusRequestArray = new GsonBuilder().create().fromJson(response, StatusRequestArray.class);
            statusRequest.clear();

            for(StatusRequest sq : statusRequestArray.myStatus){
                statusRequest.add(sq);
                Log.d("Hasil", " id " +sq.getJudul_status());
            }


            return null;
        }
    }
}
