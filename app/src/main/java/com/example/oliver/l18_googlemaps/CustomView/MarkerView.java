package com.example.oliver.l18_googlemaps.CustomView;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.oliver.l18_googlemaps.PickImageListener;
import com.example.oliver.l18_googlemaps.R;
import com.google.android.gms.maps.model.LatLng;


public class MarkerView extends RelativeLayout implements View.OnClickListener {

    private final PickImageListener mListener;
    private EditText  mText;
     private ImageView mIcon;


    public MarkerView(Activity activity, PickImageListener pickImageListener) {
        super(activity);
        mListener = pickImageListener;

        inflate(activity, R.layout.marker_view, this);

        findViews();

    }

    private void findViews() {

        mText       = (EditText) findViewById(R.id.etText_MV);
        mIcon       = (ImageView) findViewById(R.id.ivIcon_MV);

        mIcon.setImageResource(R.drawable.ic_marker);
        mIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Pick image from gallery;
        mListener.pickImageIntoView(mIcon);
    }

    public String getText() {
        return mText.getText().toString();
    }


}