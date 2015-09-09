package com.example.oliver.l18_googlemaps.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.oliver.l18_googlemaps.BitmapResize;
import com.example.oliver.l18_googlemaps.Constants;
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
    String mIconUri;

    public MyMarker(Context context) {
        markerOptions = new MarkerOptions();
        mContext = context;
        mIconUri = null;
    }

    public MyMarker position (LatLng latLng) {
        markerOptions.position(latLng);
        String snippet = String.format("%.6f,\n%.6f", latLng.latitude, latLng.longitude);
        markerOptions.snippet(snippet);
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
        Log.d(Constants.TAG, "MyMarker getIcon start ");
        mIconUri = iconUri;
//        new GetBitmapTask(mContext).execute(iconUri);
//        or
        {
            int dstWidth = mContext.getResources().getDimensionPixelSize(R.dimen.marker_icon_width);
            int dstHeight = mContext.getResources().getDimensionPixelSize(R.dimen.marker_icon_height);
            try {
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapResize.decodeBitmapFromUri(mContext, iconUri, dstWidth, dstHeight)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  this;
    }


    public MarkerOptions getMarkerOptions(){
        // TODO wait for GetBitmapTask finished;
        return markerOptions;
    }
    public String getTitle(){
        return markerOptions.getTitle();
    }
    public String getSnippet(){
        return markerOptions.getSnippet();
    }
    public String getIconUri(){
        return mIconUri;
    }
    public LatLng getPosition(){
        return markerOptions.getPosition();
    }

    @Override
    public String toString() {
        return "MyMarker(" + String.format("%.4f", getPosition().latitude) +
               ", " + String.format("%.4f", getPosition().longitude)+") " +
               " title: " + getTitle() +
               " iconUri: " + getIconUri();

    }

    class GetBitmapTask extends AsyncTask<String, Void, Bitmap> {
        private Context mContext;

        GetBitmapTask(Context context) {
            mContext = context;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            Log.d(Constants.TAG, "GetBitmapTask doInBackground uri:" + params[0]);
                int dstWidth = mContext.getResources().getDimensionPixelSize(R.dimen.marker_icon_width);
                int dstHeight = mContext.getResources().getDimensionPixelSize(R.dimen.marker_icon_height);
                try {
                    bitmap = Picasso.with(mContext).load(Uri.parse(params[0])).resize(dstWidth, dstHeight).get();
//                    bitmap = BitmapResize.decodeBitmapFromUri(mContext, params[0], dstWidth, dstHeight);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Log.d(MapsActivity.TAG, bitmap.toString());

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            Log.d(Constants.TAG, "MyMarker getIcon end");
        }
    }
}
