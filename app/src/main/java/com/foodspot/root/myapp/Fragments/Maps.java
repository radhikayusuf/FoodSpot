package com.foodspot.root.myapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foodspot.root.myapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Maps extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    List<CircleOptions> circleOptionsList = new ArrayList<>();
    List<String> posisiString = new ArrayList<>();
    List<LatLng> posisi = new ArrayList<>();
    Context context;

    public Maps() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
//        supportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.mapsDiActivity_Fragment);
        this.context = context;
//        supportMapFragment.getMapAsync(this);
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.mapsDiActivity_Fragment);
        mapFragment.getMapAsync(this);

        mMap = ((MapFragment)(getActivity().getFragmentManager().findFragmentById(R.id.mapsDiActivity_Fragment))).getMap();



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-6.914356, 107.656996);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Home"));
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Home"));

//        posisi.add(new LatLng(-6.912784, 107.649289));
//        posisi.add(new LatLng(-6.914579, 107.655843));

        for(int i=0; i<posisi.size(); i++){
            mMap.addCircle(new CircleOptions()
                    .center(posisi.get(i))
                    .radius(100)
                    .strokeColor(R.color.colorPrimaryDark)
                    .fillColor(R.drawable.ic_restaurant_white_24dp));
        }

        MarkerOptions marker = new MarkerOptions().position(new LatLng(-6.914579, 107.655843)).title("Masjid");
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_white_24dp));

        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        Toast.makeText(context, "bisa", Toast.LENGTH_SHORT).show();
        Log.d("Test"," Tereksekusi");
    }





    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
