package com.example.oliver.l18_googlemaps;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        Log.d(MapsActivity.TAG, "       MarkersFragment onCreate");
        // TODO: Change Adapter to display your content
        setListAdapter(new MarkerAdapter(getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MapsActivity.TAG, "       MarkersFragment onDestroy");
    }

}
