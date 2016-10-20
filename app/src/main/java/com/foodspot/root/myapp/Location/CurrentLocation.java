package com.foodspot.root.myapp.Location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by root on 11/09/16.
 */
public class CurrentLocation implements LocationListener {

    public static String hasil;
    TextView tvhasil;


    @Override
    public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();

            String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();
            hasil = myLocation;

            tvhasil.setText(myLocation);
            //I make a log to see the results
            Log.d("MY CURRENT LOCATION", myLocation);
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
}
