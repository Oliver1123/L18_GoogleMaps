package com.example.oliver.l18_googlemaps.CustomView;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.oliver.l18_googlemaps.R;


public class MarkerView extends RelativeLayout {


    private EditText  mText;
    private ImageView mIcon;


    public MarkerView(Activity activity) {
        super(activity);

        inflate(activity, R.layout.add_marker_view, this);

        findViews();

    }

    private void findViews() {

        mText       = (EditText) findViewById(R.id.etText_MV);
        mIcon       = (ImageView) findViewById(R.id.ivIcon_MV);

        mIcon.setImageResource(R.drawable.ic_marker);
    }

    public String getText() {
        return mText.getText().toString();
    }

    public ImageView getIconView() {
        return mIcon;
    }

}