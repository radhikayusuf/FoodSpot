package com.foodspot.root.myapp;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.foodspot.root.myapp.Adapter.AdapterMyStatus;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.foodspot.root.myapp.HttpService.Status.StatusRequest;
import com.foodspot.root.myapp.HttpService.Status.StatusRequestArray;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView tvProfileName,tvProfileStatus, tvKeterangan;
    View v;
    SharedPreferences sharedPreferences;
    CollapsingToolbarLayout toolbar_layout;
    FloatingActionButton floatingActionButton;
    CircleImageView circleImageView;
    String ID_USER =null;
    List<StatusRequest> statusRequest = new ArrayList<>();
    RecyclerView rc_mystatus;
    AdapterMyStatus adapterMyStatus;
    String id_user_orang, nama, status, cek, pic_dp, pic_bg;
    ImageButton btnSubscribe;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("data",0);

        ID_USER = sharedPreferences.getString("id_user","kosong");
        Intent i = getIntent();
        id_user_orang = i.getStringExtra("id_user");
        nama = i.getStringExtra("nama");
        status = i.getStringExtra("status");
        pic_dp = i.getStringExtra("pic_dp");
        pic_bg = i.getStringExtra("pic_bg");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");


        tvKeterangan = (TextView) findViewById(R.id.tvKeterangan);
        tvProfileName = (TextView) findViewById(R.id.activity_profile_name);
        tvProfileStatus = (TextView) findViewById(R.id.activity_profile_status);
        btnSubscribe = (ImageButton) findViewById(R.id.btnSubscribe);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.activity_toolbar_layout);
        circleImageView = (CircleImageView)findViewById(R.id.de_activity_profile);
        rc_mystatus = (RecyclerView) findViewById(R.id.activity_id_my_status);

        progressDialog.show();

        new backgroundTask().execute();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //cek = "0";

        //btnSubscribe.setVisibility(cek.equals("1")? View.GONE:View.VISIBLE);



        tvProfileName.setText(nama);
        tvProfileStatus.setText(status.equals("1")? "Restaurant":"Food Lover");

        Picasso.with(getApplicationContext())
                .load("http://morefoods.hol.es/img/"+pic_bg)
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

        Picasso.with(getApplicationContext())
                .load("http://morefoods.hol.es/img/"+pic_dp)
                .into(circleImageView);
    }


    class backgroundTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new cekSubscribe().execute();
            adapterMyStatus = new AdapterMyStatus(statusRequest, pic_dp, id_user_orang);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_mystatus.getContext());
            rc_mystatus.setLayoutManager(layoutManager);
            rc_mystatus.setAdapter(adapterMyStatus);
            adapterMyStatus.notifyDataSetChanged();
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Status.php?api_key=restoran&action=getStatusById&id_user="+id_user_orang;
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

    class cekSubscribe extends AsyncTask<Void, Void, Void> {

        String response;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Void aVoid) {
            Drawable d1= getResources().getDrawable(R.drawable.ic_person_add_white_24dp);
            Drawable d2= getResources().getDrawable(R.drawable.ic_indeterminate_check_box_white_24dp);

            if(response.equals("0")){
                btnSubscribe.setBackground(d1);
                btnSubscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        new ngeSubscribe().execute();
                    }
                });
                tvKeterangan.setText("Subscribe");
            }else{
                btnSubscribe.setBackground(d2);
                btnSubscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        new ngeUnSubscribe().execute();
                    }
                });
                tvKeterangan.setText("unSubscribe");
            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Subscribe.php?api_key=restoran" +
                    "&action=isSubscribe&id_user="+sharedPreferences.getString("id_user","kosong")+
                    "&id_restoran="+id_user_orang;
            Log.d("URLFinalbanget", " "+url);
             response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);


            Log.d("response mystatus", response);



            return null;
        }
    }

    class ngeSubscribe extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btnSubscribe.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Subscribe.php?api_key=restoran&action=subscribe&id_user="+sharedPreferences.getString("id_user","")+"&id_restoran="+id_user_orang;
            Log.d("URLFinalsebelumakhir", " "+url);



            String response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);


            //http://morefoods.hol.es/Subscribe.php?api_key=restoran&action=subscribe&id_user=5&id_restoran=2

            Log.d("response pasmaufollow", response);

            return null;
        }
    }

    class ngeUnSubscribe extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btnSubscribe.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Subscribe.php?api_key=restoran&action=unsubscribe" +
                    "&id_user="+sharedPreferences.getString("id_user","kosong") +
                    "&id_restoran="+id_user_orang;
            String response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);
            Log.d("response pasmauunfollow", response);

            return null;
        }
    }


}
