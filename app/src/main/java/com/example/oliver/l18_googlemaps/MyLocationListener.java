package com.example.oliver.l18_googlemaps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.oliver.l18_googlemaps.Activities.MapsActivity;

/**
 * Created by oliver on 09.09.15.
 */
public class MyLocationListener implements LocationListener {

    private MapsActivity mActivity;

    public MyLocationListener(MapsActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        mActivity.setMyLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

}
