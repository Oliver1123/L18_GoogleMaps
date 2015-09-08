package com.example.oliver.l18_googlemaps;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.oliver.l18_googlemaps.Dialogs.AddMarkerDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
                                                               GoogleMap.OnMapLongClickListener{
    public  static final String TAG = "tag";
    public static final int ADD_MARKER_REQUEST = 123;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ImageView mMarkerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_AM);
        setSupportActionBar(toolbar);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
        mMap = googleMap;
        if (mMap == null) return;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);
//        mMap.setOnMapLongClickListener(this);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48, 22), 14));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        DialogFragment addMarkerDialog = new AddMarkerDialog();

        addMarkerDialog.show(getFragmentManager(), null);
//
//        Marker marker = mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .title("Marker | " + String.format("%1$s | %2$s",
//                        String.valueOf(latLng.latitude), String.valueOf(latLng.longitude)))
//                .snippet("My first marker")
//                .icon(BitmapDescriptorFactory.defaultMarker()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_MARKER_REQUEST) {
            Log.d(TAG, "MapActivity onActivityResult AddMarker");
        }
    }
}
