package com.example.oliver.l18_googlemaps.Activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.oliver.l18_googlemaps.Constants;
import com.example.oliver.l18_googlemaps.CustomView.MyMarker;
import com.example.oliver.l18_googlemaps.DB.MarkerQueryHelper;
import com.example.oliver.l18_googlemaps.Dialogs.AddMarkerDialog;
import com.example.oliver.l18_googlemaps.Dialogs.LocationInfoDialog;
import com.example.oliver.l18_googlemaps.FetchAddressIntentService;
import com.example.oliver.l18_googlemaps.MyLocationListener;
import com.example.oliver.l18_googlemaps.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
                                                               GoogleMap.OnMapLongClickListener{

    private static final long LOCATION_REFRESH_TIME = 5000;
    private static final float LOCATION_REFRESH_DISTANCE = 10f ;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MarkerQueryHelper mMarkerHelper;
    private LocationManager mLocationManager;
    private MyLocationListener mMyLocationListener;
    private Location mCurrentLocation;
    private AddressResultReceiver mResultReceiver;
//    private Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_AM);
        setSupportActionBar(toolbar);

        mMyLocationListener = new MyLocationListener(this);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mMyLocationListener);

        mMarkerHelper = new MarkerQueryHelper(this);
        mMarkerHelper.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMarkerHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        if (mMap != null) {
            mMap.clear();
            addMarkers();
        }
        Log.d(Constants.TAG, "MapActivity onResume");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(Constants.TAG, "onOptionsItemSelected item = " + item.getTitle());
        switch (item.getItemId()) {
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_mark_list:
                Intent intent = new Intent(this, MarkersActivity.class);
                startActivity(intent);
                break;
            case R.id.action_clear_all:
                mMarkerHelper.clearAll();
                mMap.clear();
                break;
            case R.id.action_location_info:
                if (mCurrentLocation != null) {
                    startIntentService();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(this);

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(Constants.TAG, "MapsActivity onMapReady");
        mMap = googleMap;
        if (mMap == null) return;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);

        addMarkers();
    }

    private void addMarkers() {
        List<MyMarker> markers = mMarkerHelper.getItems();
        for (MyMarker current : markers) {
            mMap.addMarker(current.getMarkerOptions());
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_LAT_LONG, latLng);

        DialogFragment addMarkerDialog = new AddMarkerDialog();
        addMarkerDialog.setArguments(args);
        addMarkerDialog.show(getFragmentManager(), null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ADD_MARKER_REQUEST) {
            Log.d(Constants.TAG, "MapActivity onActivityResult AddMarker");
            if (resultCode == RESULT_OK) {
                LatLng latLng   = data.getParcelableExtra(Constants.ARG_LAT_LONG);
                String text     = data.getStringExtra(Constants.ARG_TEXT);
                String iconUri  = data.getStringExtra(Constants.ARG_ICON_URI);

                MyMarker marker = new MyMarker(this)
                        .position(latLng)
                        .title(text)
                        .icon(iconUri);
                mMarkerHelper.insert(marker);
                mMap.addMarker(marker.getMarkerOptions());
            }
        }
    }

    public void setMyLocation(Location location) {
        Log.d(Constants.TAG, "setMyLocation: " + location);
        mCurrentLocation = location;
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, new AddressResultReceiver(null));
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
        startService(intent);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                Log.d(Constants.TAG, "onReceiverResult success result:  " + resultData.getString(Constants.RESULT_DATA_KEY));
                DialogFragment locationInfoDialog = new LocationInfoDialog();
                locationInfoDialog.setArguments(resultData);
                locationInfoDialog.show(getFragmentManager(), null);
            }
            if (resultCode == Constants.FAILURE_RESULT) {
                String errorMessage = resultData.getString(Constants.RESULT_DATA_KEY);
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }

        }
    }

}

