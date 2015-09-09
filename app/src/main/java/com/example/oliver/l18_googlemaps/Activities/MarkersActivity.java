package com.example.oliver.l18_googlemaps.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.oliver.l18_googlemaps.Constants;
import com.example.oliver.l18_googlemaps.R;

public class MarkersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.TAG, "   MarkersActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);
    }

    @Override
    protected void onDestroy() {
        Log.d(Constants.TAG, "   MarkersActivity onDestroy");
        super.onDestroy();
    }
}
