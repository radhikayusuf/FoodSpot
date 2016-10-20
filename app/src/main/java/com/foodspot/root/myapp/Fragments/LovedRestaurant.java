package com.foodspot.root.myapp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodspot.root.myapp.Adapter.AdapterDataRestaurant;
import com.foodspot.root.myapp.HttpService.LovedRestaurant.LovedRequestArray;
import com.foodspot.root.myapp.HttpService.LovedRestaurant.Lovedrequest;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.foodspot.root.myapp.R;
import com.foodspot.root.myapp.dao.LovedRestaurantdao;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LovedRestaurant extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    List<LovedRestaurantdao> restaurants = new ArrayList<>();
    public static String RESPONSE_LOKASI;
    AdapterDataRestaurant adapterDataRestaurant;
    RecyclerView rc_lovedRestaurant;
    List<Lovedrequest> lovedrequests = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SwipeRefreshLayout swipeRefreshLayout;
    public String ID_USER;
    public LovedRestaurant() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loved_restaurant, container, false);
        initComponent(v);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("data",0);
        editor = sharedPreferences.edit();
        new backgroundTask().execute();


        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperLoved);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rc_lovedRestaurant.setAdapter(null);
                new backgroundTask().execute();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return v;
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initComponent(View v){
        rc_lovedRestaurant = (RecyclerView) v.findViewById(R.id.rc_loved_restaurant);
    }

    private void getRecycler(){
    }

    @Override
    public void onRefresh() {

    }

    public class backgroundTask extends AsyncTask<Void, Void, Void>{


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterDataRestaurant = new AdapterDataRestaurant(lovedrequests);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_lovedRestaurant.getContext());
            rc_lovedRestaurant.setLayoutManager(layoutManager);
            rc_lovedRestaurant.setAdapter(adapterDataRestaurant);
            adapterDataRestaurant.notifyDataSetChanged();

            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ID_USER = sharedPreferences.getString("id_user","kosong");
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Subscribe.php?api_key=restoran&action=getLestoranSubscribe&id_user="+ID_USER;
            RESPONSE_LOKASI = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            editor.putString("responseLokasi",RESPONSE_LOKASI);
            LovedRequestArray lovedRequestArray = new GsonBuilder().create().fromJson(RESPONSE_LOKASI, LovedRequestArray.class);
            lovedrequests.clear();

            for(Lovedrequest lq : lovedRequestArray.subcriber){
                lovedrequests.add(lq);
                System.out.println("dapet "+lq.getNama_dagangan());
            }
            return null;
        }
    }

}
