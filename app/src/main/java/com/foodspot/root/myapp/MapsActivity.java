package com.foodspot.root.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.foodspot.root.myapp.Adapter.AdapterNearbyRestaurant;
import com.foodspot.root.myapp.Data.GPSTracker;
import com.foodspot.root.myapp.dao.LovedRestaurantdao;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    List<CircleOptions> circleOptionsList = new ArrayList<>();
    static List<LatLng> posisi = new ArrayList<>();
    Context context;
    GPSTracker gpsTracker;
    //List<LatLng>latLngs = new ArrayList<>();
    Intent intent;
    List<String> posisiArray = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String id_satu, lokasisatu, pic_bgsatu, pic_dpsatu, namasatu;
    Double hasil[];
    List<LovedRestaurantdao> lovedRestaurantdaos = new ArrayList<>();

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> lokasi = new ArrayList<>();
    ArrayList<String> nama = new ArrayList<>();
    ArrayList<String> pic_bg = new ArrayList<>();
    ArrayList<String> pic_dp = new ArrayList<>();
    LatLng satu;
    RecyclerView rc_maps_nearby;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar_Maps);
        Intent i = getIntent();




        id = i.getStringArrayListExtra("lokasiID");
        lokasi = i.getStringArrayListExtra("lokasiArray");
        nama = i.getStringArrayListExtra("nama");
        pic_bg = i.getStringArrayListExtra("pic_bg");
        pic_dp = i.getStringArrayListExtra("pic_dp");

        lokasisatu = null;

        id_satu = i.getStringExtra("id_user");
        lokasisatu = i.getStringExtra("lokasi");
        namasatu = i.getStringExtra("namasatu");
        pic_dpsatu = i.getStringExtra("pic_dpsatu");
        pic_bgsatu = i.getStringExtra("pic_bgsatu");

        setSupportActionBar(toolbar);
        toolbar.setTitle("Nearby");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        System.out.println("Satuan "+lokasisatu);
        System.out.println("satuan "+namasatu);
        System.out.println("Satuan "+pic_dpsatu);
        System.out.println("Satuan "+pic_bgsatu);
        //Intent in = getIntent();
        //Bundle b = new Bundle();

        //List<Parcelable> nearbyRequestlist = (List<Parcelable>)in.getParcelableArrayListExtra("hasil");
        //List<NearbyRequest> nearbyRequestlist = b.getParcelableArrayList("hasil");
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapsDiActivity_Fragment);
        supportMapFragment.getMapAsync(this);

        rc_maps_nearby = (RecyclerView) findViewById(R.id.rc_nearby_restaurant);


        if(lokasi != null){
            lovedRestaurantdaos = new ArrayList<>();
            for(int x=0; x<lokasi.size(); x++){
                lovedRestaurantdaos.add(new LovedRestaurantdao(id.get(x), nama.get(x), lokasi.get(x), pic_dp.get(x), pic_bg.get(x)));
            }
        }else{
            Double buff[] = getLatLang(lokasisatu);
            satu = new LatLng(buff[0],buff[1]);
            lovedRestaurantdaos.add(new LovedRestaurantdao(id_satu,namasatu,lokasisatu,pic_dpsatu,pic_bgsatu));
        }
        rc_maps_nearby.setAdapter(null);
        AdapterNearbyRestaurant adapterDataRestaurant = new AdapterNearbyRestaurant(lovedRestaurantdaos,getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_maps_nearby.getContext());
        rc_maps_nearby.setLayoutManager(layoutManager);
        rc_maps_nearby.setAdapter(adapterDataRestaurant);
        adapterDataRestaurant.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        sharedPreferences = getApplicationContext().getSharedPreferences("data",0);
        //new backgroundTaskMaps().execute();
        mMap = googleMap;
        LatLng base = new LatLng(Double.parseDouble(sharedPreferences.getString("lat","")), Double.parseDouble(sharedPreferences.getString("long","")));
        //System.out.println("size di map "+ lokasi.size());
        posisi.clear();

        if(satu!=null){
            posisi.add(satu);
        }

        if(lokasi!=null){
        for(int x=0; x<lokasi.size(); x++){
            hasil = getLatLang(lokasi.get(x));
            posisi.add(new LatLng(hasil[0],hasil[1]));
         }
        }
        System.out.println("size di map "+ posisi.size());

        if(posisi.size()>0 && lokasi!=null) {
            for (int j = 0; j < posisi.size(); j++) {
                MarkerOptions marker = new MarkerOptions().position(posisi.get(j)).title(nama.get(j));
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo20x20));
                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(base));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }else{
            //Toast.makeText(getApplicationContext(),"kosong",Toast.LENGTH_SHORT).show();
            MarkerOptions marker = new MarkerOptions().position(satu).title(namasatu);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo20x20));
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(satu));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

    }

    Double[] getLatLang(String value){

        Double a[] = new Double[2];

        for(int i=0; i<value.length();i++){
            if(value.charAt(i) == ','){
                a[0] = Double.parseDouble(value.substring(0,i));
                a[1] = Double.parseDouble(value.substring(i+1,value.length()));
                break;
            }
        }

        System.out.println("Lat = "+String.valueOf(a[0]));
        System.out.println("Lang = "+String.valueOf(a[1]));

        return a;
    }


}
