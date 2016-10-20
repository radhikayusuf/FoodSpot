package com.foodspot.root.myapp.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.foodspot.root.myapp.Adapter.AdapterDataContent;
import com.foodspot.root.myapp.HttpService.LikeRequest.LikeRequest;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.foodspot.root.myapp.HttpService.Timeline.TimelineArray;
import com.foodspot.root.myapp.HttpService.Timeline.TimelineRequest;
import com.foodspot.root.myapp.R;
import com.foodspot.root.myapp.SearchRestaurantActivity;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLine extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rc_content;
    public static String ID_USER;
    List<TimelineRequest> timelineRequests = new ArrayList<>();
    //Timelinedao buff_content = new Timelinedao();
    AdapterDataContent adapterDataContent;
    SharedPreferences sharedPreferences;
    List<LikeRequest> likeRequests = new ArrayList<>();
    LinearLayout linearLayout;
    Button buttonFind;

    public TimeLine() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);
        rc_content = (RecyclerView) v.findViewById(R.id.rc_content);
        buttonFind = (Button) v.findViewById(R.id.btnFindRestaurnt);
        linearLayout = (LinearLayout) v.findViewById(R.id.linearCek);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiper);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rc_content.setAdapter(null);
                new backgroundTaskTL().execute();


            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), SearchRestaurantActivity.class));
            }
        });

    sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("data",0);
        new backgroundTaskTL().execute();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //onStart();
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("hasil pref", sharedPreferences.getString("id_user","kosong"));

    }

    @Override
    public void onRefresh() {

    }


    public class backgroundTaskTL extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(timelineRequests.size()>0){
                linearLayout.setVisibility(View.GONE);
                adapterDataContent = new AdapterDataContent(timelineRequests);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_content.getContext());
                rc_content.setLayoutManager(layoutManager);
                rc_content.setAdapter(adapterDataContent);
                adapterDataContent.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }else{
                linearLayout.setVisibility(View.VISIBLE);
            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            ID_USER = sharedPreferences.getString("id_user","kosong");
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Subscribe.php?api_key=restoran&action=getAllStatusSubscriber&id_user="+ID_USER;
            System.out.println(url);
            String response = serviceHandler.makeServiceCall(url, ServiceHandler.GET);



            TimelineArray timelineArray = new GsonBuilder().create().fromJson(response, TimelineArray.class);
            timelineRequests.clear();
            for(TimelineRequest tq : timelineArray.resultTimeline){
                timelineRequests.add(tq);
                Log.d("Hasil", " id " +tq.get_Nama_Lengkap());
            }
            return null;
        }


    }



}
