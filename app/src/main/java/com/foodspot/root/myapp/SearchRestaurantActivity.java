package com.foodspot.root.myapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.foodspot.root.myapp.Adapter.AdapterSearchRestaurant;
import com.foodspot.root.myapp.HttpService.Restaurant.RestaurantRequest;
import com.foodspot.root.myapp.HttpService.Restaurant.RestaurantRequestArray;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.google.gson.GsonBuilder;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchRestaurantActivity extends AppCompatActivity {

    ImageButton btnSearch;
    EditText etSearch;
    RecyclerView rc_search;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    List<RestaurantRequest> restaurantRequests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurant);

        btnSearch = (ImageButton) findViewById(R.id.btnSearchRestaurant);
        etSearch = (EditText) findViewById(R.id.etSearchRestaurant);
        rc_search = (RecyclerView) findViewById(R.id.rc_hasil_search_restaurant);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching ...");
        sharedPreferences = getApplicationContext().getSharedPreferences("data",0);
    }


    @Override
    protected void onStart() {
        super.onStart();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if(!etSearch.getText().toString().equals("")){
                    new backgroundTaskMaps(etSearch.getText().toString()).execute();
                }else{
                    rc_search.setAdapter(null);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnSearch.performClick();
                    return true;
                }else if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    btnSearch.performClick();
                    return true;
                }
                return false;
            }
        });

    }


    public class backgroundTaskMaps extends AsyncTask<Void,Void,Void> {
        String nama;

        public backgroundTaskMaps(String nama) {
            this.nama = nama;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(restaurantRequests.size()>0){
            rc_search.setAdapter(null);
            AdapterSearchRestaurant adapterSearchRestaurant = new AdapterSearchRestaurant(restaurantRequests);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_search.getContext());
            rc_search.setLayoutManager(layoutManager);
            rc_search.setAdapter(adapterSearchRestaurant);
            adapterSearchRestaurant.notifyDataSetChanged();
            progressDialog.dismiss();
            }else{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            //String id_user = sharedPreferences.getString("id_user","0");
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/User.php?api_key=restoran&action=SearchRestoranByName" +
                    "&nama_lengkap="+ URLEncoder.encode(nama);
            String response = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            RestaurantRequestArray restaurantRequestArray = new GsonBuilder().create().fromJson(response, RestaurantRequestArray.class);
            restaurantRequests.clear();

            int i =0 ;
            for(RestaurantRequest lq : restaurantRequestArray.hasilSearch){
                if(lq.getType_user().equals("1")){
                    restaurantRequests.add(lq);
                    System.out.println("typebaru "+restaurantRequests.get(i).getType_user());
                }
                i++;
            }
            return null;
        }
    }
}
