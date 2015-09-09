package com.example.oliver.l18_googlemaps.Interfaces;

import com.example.oliver.l18_googlemaps.CustomView.MyMarker;

/**
 * Created by oliver on 09.09.15.
 */
public interface MarkerActionCallbackListener {
    void actionEdit(MyMarker marker, String title);
    void actionDelete(MyMarker marker);

}
