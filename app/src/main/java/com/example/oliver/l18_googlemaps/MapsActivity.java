package com.example.oliver.l18_googlemaps;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.oliver.l18_googlemaps.CustomView.MyMarker;
import com.example.oliver.l18_googlemaps.DB.MarkerQueryHelper;
import com.example.oliver.l18_googlemaps.Dialogs.AddMarkerDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
                                                               GoogleMap.OnMapLongClickListener {
    public static final String TAG = "tag";
    public static final int ADD_MARKER_REQUEST = 123;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ImageView mMarkerIcon;
    private MarkerQueryHelper mMarkerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_AM);
        setSupportActionBar(toolbar);

        mMarkerHelper = new MarkerQueryHelper(this);
        mMarkerHelper.open();
//        mMarkerHelper.clearAll();
//        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMarkerHelper.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MapActivity onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        if (mMap != null) {
            mMap.clear();
            addMarkers();
        }
        Log.d(TAG, "MapActivity onResume");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected item = " + item.getTitle());
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
        Log.d(TAG, "MapsActivity onMapReady");
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
        args.putParcelable(AddMarkerDialog.ARG_LAT_LONG, latLng);

        DialogFragment addMarkerDialog = new AddMarkerDialog();
        addMarkerDialog.setArguments(args);
        addMarkerDialog.show(getFragmentManager(), null);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_MARKER_REQUEST) {
            Log.d(TAG, "MapActivity onActivityResult AddMarker");
            if (resultCode == RESULT_OK) {
                LatLng latLng = data.getParcelableExtra(AddMarkerDialog.ARG_LAT_LONG);
                String text = data.getStringExtra(AddMarkerDialog.ARG_TEXT);
                String iconUri = data.getStringExtra(AddMarkerDialog.ARG_ICON_URI);

                MyMarker marker = new MyMarker(this)
                        .position(latLng)
                        .title(text)
                        .icon(iconUri);
                mMarkerHelper.insert(marker);
                mMap.addMarker(marker.getMarkerOptions());

            }
        }
    }
}

