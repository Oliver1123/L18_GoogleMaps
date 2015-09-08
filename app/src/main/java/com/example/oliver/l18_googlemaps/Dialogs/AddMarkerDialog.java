package com.example.oliver.l18_googlemaps.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oliver.l18_googlemaps.CustomView.MarkerView;
import com.example.oliver.l18_googlemaps.MapsActivity;
import com.example.oliver.l18_googlemaps.PickImageListener;
import com.example.oliver.l18_googlemaps.R;
import com.google.android.gms.maps.model.LatLng;

public class AddMarkerDialog extends DialogFragment implements DialogInterface.OnClickListener,
                                                                PickImageListener {
    private static final int PICK_IMAGE         = 0;

    public static final String ARG_TEXT         = "com.example.oliver.l18_googlemaps.Text";
    public static final String ARG_ICON_URI     = "com.example.oliver.l18_googlemaps.IconUri";

    TextView mLatitude, mLongitude, mText;
    ImageView mIcon;
    String mIconUri;
    MarkerView markerView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mIconUri = "android.resource://com.example.oliver.l18_googlemaps/" + R.drawable.ic_marker;

        markerView = new MarkerView(getActivity(), this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(markerView)
                   .setPositiveButton(android.R.string.ok, this);

        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d(MapsActivity.TAG, "AddMarkerDialog onClick");
        Intent data = new Intent();
        data.putExtra(ARG_TEXT, markerView.getText());
        data.putExtra(ARG_ICON_URI, mIconUri);

        ((MapsActivity) getActivity()).onActivityResult(MapsActivity.ADD_MARKER_REQUEST, Activity.RESULT_OK, data);
    }

    @Override
    public void pickImageIntoView(ImageView imageView) {
        Log.d(MapsActivity.TAG, "AddMarkerDialog pickImageIntoView");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        mIcon = imageView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(MapsActivity.TAG, "AddMarkerDialog onActivityResult");
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                        mIconUri = data.getData().toString();
                        Log.d(MapsActivity.TAG, "AddMarkerDialog mIconUri: " + mIconUri);
                        mIcon.setImageURI(data.getData());
                }
            }
        }
    }

}
