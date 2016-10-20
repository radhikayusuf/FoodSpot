package com.foodspot.root.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.foodspot.root.myapp.Adapter.ViewPagerAdapter;
import com.foodspot.root.myapp.Fragments.LovedRestaurant;
import com.foodspot.root.myapp.Fragments.Profile;
import com.foodspot.root.myapp.Fragments.TimeLine;
import com.foodspot.root.myapp.HttpService.NearbyRestaurantRequest.NearbyRequest;
import com.foodspot.root.myapp.HttpService.NearbyRestaurantRequest.NearbyRequestArray;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.firebase.client.Firebase;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView tv_name, tv_email;
    Intent i;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static List<NearbyRequest> nearbyRequests = new ArrayList<>();
    Double hasil[];
    ArrayList<String> lokasiId = new ArrayList<>();
    ArrayList<String> lokasiHasil = new ArrayList<>();
    ArrayList<String> lokasiPic_profile = new ArrayList<>();
    ArrayList<String> lokasiPic_bg = new ArrayList<>();
    ArrayList<String> lokasiNama = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        i = getIntent();
        sharedPreferences = getApplicationContext().getSharedPreferences("data",0);
        lokasiHasil.clear();
        new backgroundTaskMaps().execute();

        System.out.println("lokasi size "+String.valueOf(lokasiHasil.size()));

        initComponent();


        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("FoodSpot");
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());


        viewPagerAdapter.addFragment(new TimeLine(), "");
        viewPagerAdapter.addFragment(new LovedRestaurant(),"");
        viewPagerAdapter.addFragment(new Profile(),"");
        //viewPagerAdapter.addFragment(new Maps(),"");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        int icon[] = {R.drawable.ic_home_white_24dp, R.drawable.ic_restaurant_menu_white_24dp,  R.drawable.face};


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icon[i]);
        }

        Firebase.setAndroidContext(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(sharedPreferences.getString("type_user","kosong").equals("1")? View.VISIBLE : View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(getApplicationContext(), InsertPostActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        Log.d("Main-cekShared", sharedPreferences.getString("id_user","kosong"));
        Log.d("Main-cekShared", sharedPreferences.getString("usernname","kosong"));
        Log.d("Main-cekShared", sharedPreferences.getString("lat","kosong"));
        Log.d("Main-cekShared", sharedPreferences.getString("lang","kosong"));


    }


    @Override
    protected void onStart() {
        super.onStart();
//        GPSTracker gpsTracker = new GPSTracker(this);
//        if (gpsTracker.getIsGPSTrackingEnabled()) {
//            Toast.makeText(MainActivity.this, "Lat = " + String.valueOf(gpsTracker.getLatitude()) +
//                    "\nLang = " + String.valueOf(gpsTracker.getLongitude()), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(1);
                //finish();
                this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.id_maps) {
            //Log.d("response saat tap maps " ,LovedRestaurant.RESPONSE_LOKASI);
            new backgroundTaskMaps().execute();

            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            i.putExtra("id","jamak");
            i.putStringArrayListExtra("lokasiID",lokasiId);
            i.putStringArrayListExtra("lokasiArray",lokasiHasil);
            i.putStringArrayListExtra("nama",lokasiNama);
            i.putStringArrayListExtra("pic_bg",lokasiPic_bg);
            i.putStringArrayListExtra("pic_dp",lokasiPic_profile);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));

        } else if (id == R.id.nav_share) {
            editor = sharedPreferences.edit();
            editor.remove("id_user");
            editor.commit();
            finish();
            this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if(id == R.id.id_search){
            startActivity(new Intent(MainActivity.this, SearchRestaurantActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void initComponent(){

    }



    public class backgroundTaskMaps extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

        @Override
        protected Void doInBackground(Void... params) {
            //String id_user = sharedPreferences.getString("id_user","0");
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/User.php?api_key=restoran&action=getNearRestoran&" +
                    "longitude="+ sharedPreferences.getString("long","kosong")+
                    "&latitude="+ sharedPreferences.getString("lat","kosong");
            String response = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            NearbyRequestArray nearbyRequestArray = new GsonBuilder().create().fromJson(response, NearbyRequestArray.class);
            nearbyRequests.clear();

            for(NearbyRequest lq : nearbyRequestArray.nearbyRequestsList){
                nearbyRequests.add(lq);
                Log.d("dapet di maps ",lq.getLatitude()+","+lq.getLongitude());
                lokasiId.add(lq.getId_user());
                lokasiHasil.add(String.valueOf(lq.getLatitude()+","+lq.getLongitude()));
                lokasiNama.add(lq.getNama_lengkap());
                lokasiPic_bg.add(lq.getPicture_background());
                lokasiPic_profile.add(lq.getPicture_profile());
            }
            return null;
        }
    }

}
