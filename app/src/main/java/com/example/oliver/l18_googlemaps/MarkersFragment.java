package com.example.oliver.l18_googlemaps;

import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;

import com.example.oliver.l18_googlemaps.Activities.MapsActivity;
import com.example.oliver.l18_googlemaps.DB.MarkerQueryHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 */
public class MarkersFragment extends ListFragment {

    private MarkerQueryHelper markerQueryHelper;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MarkersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG, "       MarkersFragment onCreate");
        setListAdapter(new MarkerAdapter(getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "       MarkersFragment onDestroy");
    }

}
