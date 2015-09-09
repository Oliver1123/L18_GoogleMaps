package com.example.oliver.l18_googlemaps.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by oliver on 09.09.15.
 */
public class MarkerDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "markerDB.";

    private static final int DB_VERSION = 1;

    public static final String MARKER_TABLE_NAME = "markers";


    public static final String MARKER_ID        = "_id";
    public static final String MARKER_LAT       = "marker_lat";
    public static final String MARKER_LONG      = "marker_long";
    public static final String MARKER_TITLE     = "marker_title";
    public static final String MARKER_ICON_URI  = "marker_icon_uri";


    private static final String MARKER_TABLE_CREATE =
            "CREATE TABLE " + MARKER_TABLE_NAME+ " (" +
                    MARKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MARKER_LAT + " REAL, " +
                    MARKER_LONG + " REAL, " +
                    MARKER_TITLE + " TEXT, " +
                    MARKER_ICON_URI + " TEXT);";

    public MarkerDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MARKER_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
