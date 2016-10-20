package com.foodspot.root.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodspot.root.myapp.Data.GPSTracker;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity implements LocationListener {

    Button btnSaveSetting;
    CircleImageView img_profile;
    ImageView img_content;
    SharedPreferences sharedPreferences;
    EditText etNohp, etStatus, et_name_setting;
    Intent i;
    LocationListener locationListener;
    LocationManager locationManager;
    LinearLayout linearLayout;
    GPSTracker gps;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        img_content = (ImageView) findViewById(R.id.setting_bg);
        img_profile = (CircleImageView) findViewById(R.id.setting_dp);
        et_name_setting = (EditText) findViewById(R.id.setting_nama);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", 0);
        editor = sharedPreferences.edit();
        etNohp = (EditText) findViewById(R.id.noHp);
        etStatus = (EditText) findViewById(R.id.status);
        linearLayout = (LinearLayout) findViewById(R.id.linearUpdateLocation);
        btnSaveSetting = (Button) findViewById(R.id.btnSaveSetting);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");


        et_name_setting.setText(sharedPreferences.getString("nama", "kosong"));

        Picasso.with(getApplicationContext())
                .load(("http://morefoods.hol.es/img/" + sharedPreferences.getString("pic_profile", "kosong")))
                .into(img_profile);

        Picasso.with(getApplicationContext())
                .load(("http://morefoods.hol.es/img/" + sharedPreferences.getString("pic_bg", "kosong")))
                .into(img_content);

        etNohp.setText(sharedPreferences.getString("noTelp", "kosong"));
        etStatus.setText(sharedPreferences.getString("status", "kosong"));


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(SettingActivity.this);
                // check if GPS enabled
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    // \n is for new line
                    // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"latitude is :"+ latitude + "&" + "longitude is :"+ longitude,Toast.LENGTH_LONG).show();
                new backgroundTask(String.valueOf(latitude), String.valueOf(longitude)).execute();
            }
        });

        btnSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String nama = et_name_setting.getText().toString();
                new changeNameTask(nama).execute();

            }
        });

        etNohp.setVisibility(View.GONE);
        etStatus.setVisibility(View.GONE);

}

    @Override
    protected void onStart() {
        super.onStart();

        i = new Intent(getApplicationContext(),BuffImageActivity.class);

        img_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("id","bg");
                startActivity(i);
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("id","dp");
                startActivity(i);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Location Lat", String.valueOf(location.getLatitude()));
        Log.d("Location Long", String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    class backgroundTask extends AsyncTask<Void, Void, Void>{
            String Lat, Lang;

        public backgroundTask(String lat, String lang) {
            Lat = lat;
            Lang = lang;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Status.php?api_key=restoran&action=update" +
                        "&table=t_user" +
                        "&longitude="+Lang +
                        "&where=id_user&value="+ sharedPreferences.getString("id_user","kosong");

            String url1 = "http://morefoods.hol.es/Status.php?api_key=restoran&action=update" +
                    "&table=t_user" +
                    "&latitude="+Lat +
                    "&where=id_user&value="+ sharedPreferences.getString("id_user","kosong");

            String response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);
            String response1 = serviceHandler.makeServiceCall(url1,ServiceHandler.GET);

            editor.putString("long", Lang);
            editor.putString("lat", Lat);
            editor.commit();

            Log.d("response URLnyatuh", " "+response);
            Log.d("response URL1nyatuh", " "+response1);

            return null;
        }
    }

    class changeNameTask extends AsyncTask<Void, Void, Void>{
       String name;

        public changeNameTask(String name) {
            this.name = name;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();


        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Status.php?api_key=restoran&action=update" +
                    "&table=t_user" +
                    "&nama_lengkap="+ URLEncoder.encode(name)+
                    "&where=id_user" +
                    "&value="+ sharedPreferences.getString("id_user","kosong");

            String response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);

            editor.putString("nama", name);
            editor.commit();

            Log.d("response changename", " "+response);

            return null;
        }
    }
}
