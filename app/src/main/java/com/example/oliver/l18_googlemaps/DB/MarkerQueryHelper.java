package com.example.oliver.l18_googlemaps.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.oliver.l18_googlemaps.CustomView.MyMarker;
import com.example.oliver.l18_googlemaps.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oliver on 09.09.15.
 */
public class MarkerQueryHelper {
    private Context mContext;
    private MarkerDBHelper mDBHelper;
    private SQLiteDatabase mDataBase;

    public MarkerQueryHelper(Context context) {
        mContext = context;
    }

    public void open(){
        mDBHelper = new MarkerDBHelper(mContext);
        mDataBase = mDBHelper.getWritableDatabase();
    }

    public void close(){
        if (mDBHelper != null) mDBHelper.close();
    }

    /**
     * Clear tables contacts and phones
     */
    public void clearAll() {
        mDataBase.delete(MarkerDBHelper.MARKER_TABLE_NAME,null,null);
    }
    /**
     * Insert given MyMarker into base
     * @param item MyMarker that  will be inserted
     */
    public void insert(MyMarker item) {
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(MarkerDBHelper.MARKER_LAT, item.getPosition().latitude);
            cv.put(MarkerDBHelper.MARKER_LONG, item.getPosition().longitude);
            cv.put(MarkerDBHelper.MARKER_TITLE, item.getTitle());
            cv.put(MarkerDBHelper.MARKER_ICON_URI, item.getIconUri());

            mDataBase.insert(MarkerDBHelper.MARKER_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
        } finally {
            mDataBase.endTransaction();
            Log.d(MapsActivity.TAG, "inserted " + item.toString());
        }
    }


    /**
     * Get all Contact Items from data base
     * @return list of Contact Items
     */
    public ArrayList<MyMarker> getItems() {
        ArrayList<MyMarker> result = new ArrayList<>();
        Cursor c = mDataBase.query(MarkerDBHelper.MARKER_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (c != null) {
            while (c.moveToNext()) {
                MyMarker item = new MyMarker(mContext);

                double marker_lat = c.getDouble(c.getColumnIndex(MarkerDBHelper.MARKER_LAT));
                double marker_long = c.getDouble(c.getColumnIndex(MarkerDBHelper.MARKER_LONG));
                String marker_title = c.getString(c.getColumnIndex(MarkerDBHelper.MARKER_TITLE));
                String marker_icon_uri = c.getString(c.getColumnIndex(MarkerDBHelper.MARKER_ICON_URI));

                item.position(new LatLng(marker_lat, marker_long))
                    .title(marker_title)
                    .icon(marker_icon_uri);

                result.add(item);
                Log.d(MapsActivity.TAG, "get " + item.toString());
            }

            c.close();
        }
        Log.d(MapsActivity.TAG, "------------get from DB :" + result.size() + " items");
        return result;
    }

    public void update(MyMarker marker) {
        LatLng latLng = marker.getPosition();
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(MarkerDBHelper.MARKER_LAT, marker.getPosition().latitude);
            cv.put(MarkerDBHelper.MARKER_LONG, marker.getPosition().longitude);
            cv.put(MarkerDBHelper.MARKER_TITLE, marker.getTitle());
            cv.put(MarkerDBHelper.MARKER_ICON_URI, marker.getIconUri());

            int updated = mDataBase.update(MarkerDBHelper.MARKER_TABLE_NAME,
                    cv,
                    MarkerDBHelper.MARKER_LAT + " = ? AND " + MarkerDBHelper.MARKER_LONG + " = ?",
                    new String[]{String.valueOf(latLng.latitude), String.valueOf(latLng.longitude)});
            Log.d(MapsActivity.TAG, "Helper updated:" + updated + " marker: " + marker);
            mDataBase.setTransactionSuccessful();
        } finally {
            mDataBase.endTransaction();
        }
    }

    public void delete(MyMarker marker) {
        LatLng latLng = marker.getPosition();
        mDataBase.beginTransaction();
        try {

           int deleted = mDataBase.delete(MarkerDBHelper.MARKER_TABLE_NAME,
                    MarkerDBHelper.MARKER_LAT + " = ? AND " + MarkerDBHelper.MARKER_LONG + " = ?",
                    new String[]{String.valueOf(latLng.latitude), String.valueOf(latLng.longitude)});

            mDataBase.setTransactionSuccessful();
            Log.d(MapsActivity.TAG, "Deleted: " + deleted + " marker:" + marker );
        } finally {
            mDataBase.endTransaction();
        }
    }
}
