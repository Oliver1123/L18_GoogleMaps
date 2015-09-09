package com.example.oliver.l18_googlemaps.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.example.oliver.l18_googlemaps.Constants;
import com.example.oliver.l18_googlemaps.R;

/**
 * Created by oliver on 09.09.15.
 */
public class LocationInfoDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String locationInfo = getArguments().getString(Constants.RESULT_DATA_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                 .setTitle(R.string.info_dialog_title)
                 .setMessage(locationInfo)
                 .setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }
}
