package com.example.oliver.l18_googlemaps.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.oliver.l18_googlemaps.Activities.MapsActivity;
import com.example.oliver.l18_googlemaps.Constants;
import com.example.oliver.l18_googlemaps.CustomView.MarkerView;
import com.example.oliver.l18_googlemaps.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

public class AddMarkerDialog extends DialogFragment implements DialogInterface.OnClickListener,
                                                               View.OnClickListener {
    private static final int PICK_IMAGE = 0;

    String mIconUri;
    MarkerView markerView;
    LatLng latLng;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mIconUri = "android.resource://com.example.oliver.l18_googlemaps/" + R.drawable.ic_marker;
        Bundle args = getArguments();
        latLng = args.getParcelable(Constants.ARG_LAT_LONG);

        markerView = new MarkerView(getActivity());
        markerView.getIconView().setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(markerView)
                   .setPositiveButton(android.R.string.ok, this);

        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d(Constants.TAG, "AddMarkerDialog onClick");
        Log.d(Constants.TAG, "    text: " + markerView.getText());
        Log.d(Constants.TAG, "    iconUri: " + mIconUri);
        Intent data = new Intent();

        data.putExtra(Constants.ARG_LAT_LONG, latLng);
        data.putExtra(Constants.ARG_TEXT, markerView.getText());
        data.putExtra(Constants.ARG_ICON_URI, mIconUri);

        ((MapsActivity) getActivity()).onActivityResult(Constants.ADD_MARKER_REQUEST, Activity.RESULT_OK, data);
    }

    @Override
    public void onClick(View v) {
        Log.d(Constants.TAG, "AddMarkerDialog mIcon Onclick");
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE);
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Constants.TAG, "AddMarkerDialog onActivityResult");
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    mIconUri = data.getData().toString();
                    Log.d(Constants.TAG, "AddMarkerDialog mIconUri: " + mIconUri);
                    Picasso.with(getActivity()).load(data.getData()).into(markerView.getIconView());
                }
            }
        }
        Log.d(Constants.TAG, "AddMarkerDialog onActivityResult end");
    }
}
