package com.example.oliver.l18_googlemaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MarkersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MapsActivity.TAG, "   MarkersActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);
    }

    @Override
    protected void onDestroy() {
        Log.d(MapsActivity.TAG, "   MarkersActivity onDestroy");
        super.onDestroy();
    }
}
