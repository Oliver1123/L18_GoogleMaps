package com.example.oliver.l18_googlemaps;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.oliver.l18_googlemaps.CustomView.MarkerListItemView;
import com.example.oliver.l18_googlemaps.CustomView.MyMarker;
import com.example.oliver.l18_googlemaps.DB.MarkerQueryHelper;
import com.example.oliver.l18_googlemaps.Interfaces.MarkerActionCallbackListener;

import java.util.ArrayList;

/**
 * Created by oliver on 09.09.15.
 */
public class MarkerAdapter extends BaseAdapter implements MarkerActionCallbackListener {
    private MarkerQueryHelper markerQueryHelper;
    Context mContext;
    ArrayList<MyMarker> mData = null;

    MarkerAdapter(Context context) {
        mContext = context;

        markerQueryHelper = new MarkerQueryHelper(mContext);
        markerQueryHelper.open();
        mData = markerQueryHelper.getItems();
    }

    @Override
    protected void finalize() throws Throwable {
        markerQueryHelper.close();
        super.finalize();

    }

    @Override
    public int getCount() {
        return (mData == null) ? 0: mData.size();
    }

    @Override
    public Object getItem(int position) {
        return (mData == null) ? null: mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyMarker marker = mData.get(position);

        View rootView = convertView;
        if (rootView == null) {
            rootView = new MarkerListItemView(mContext, this);
        }

        ((MarkerListItemView)rootView).setMarker(marker);

        return rootView;
    }

    @Override
    public void actionDelete(MyMarker marker) {
        Log.d(Constants.TAG, "actionDelete marker:" + marker);
        mData.remove(marker);
        markerQueryHelper.delete(marker);
        notifyDataSetChanged();
    }
}
