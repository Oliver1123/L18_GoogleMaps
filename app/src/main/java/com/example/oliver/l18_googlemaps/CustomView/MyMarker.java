package com.example.oliver.l18_googlemaps.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.oliver.l18_googlemaps.MapsActivity;
import com.example.oliver.l18_googlemaps.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by oliver on 08.09.15.
 */
public class MyMarker {
    MarkerOptions markerOptions;
    Context mContext;

    public MyMarker(Context context) {
        markerOptions = new MarkerOptions();
        mContext = context;
    }

    public MyMarker position (LatLng latLng) {
        markerOptions.position(latLng);
        return this;
    }

    public MyMarker title(String title) {
        markerOptions.title(title);
        return  this;
    }

    public MyMarker snippet(String snippet) {
        markerOptions.snippet(snippet);
        return  this;
    }

    public MyMarker icon(String iconUri) {
        new GetBitmapTask(mContext).execute(iconUri);
        return  this;
    }


    public MarkerOptions getMarkerOptions(){
        return markerOptions;
    }


    class GetBitmapTask extends AsyncTask<String, Void, Bitmap> {
        private Context mContext;

        GetBitmapTask(Context context) {
            mContext = context;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d(MapsActivity.TAG, "GetBitmapTask doInBackground uri:" + params[0]);
            int dstWidth    = mContext.getResources().getDimensionPixelSize(R.dimen.marker_icon_width);
            int dstHeight   = mContext.getResources().getDimensionPixelSize(R.dimen.marker_icon_height);
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(mContext).load(Uri.parse(params[0])).resize(dstWidth, dstHeight).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(MapsActivity.TAG, bitmap.toString());
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        }
    }
}
