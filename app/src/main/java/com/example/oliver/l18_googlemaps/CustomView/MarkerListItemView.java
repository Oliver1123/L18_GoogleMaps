package com.example.oliver.l18_googlemaps.CustomView;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oliver.l18_googlemaps.Interfaces.MarkerActionCallbackListener;
import com.example.oliver.l18_googlemaps.R;
import com.squareup.picasso.Picasso;

/**
 * Created by oliver on 09.09.15.
 */
public class MarkerListItemView  extends RelativeLayout implements View.OnClickListener {

    private TextView mSnippet;
    private EditText mText;
    private ImageView mIcon;

    private Context mContext;
    private MyMarker mMarker;

    MarkerActionCallbackListener mListener;

    public MarkerListItemView(Context context, MarkerActionCallbackListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        inflate(mContext, R.layout.my_marker_view, this);

        findViews();

    }

    private void findViews() {

        mText       = (EditText) findViewById(R.id.etText_MMV);
        mIcon       = (ImageView) findViewById(R.id.ivIcon_MMV);
        mSnippet    = (TextView) findViewById(R.id.tvSnippet_MMV);

      findViewById(R.id.ibDelete_MMV).setOnClickListener(this);
      findViewById(R.id.ibEdit_MMV).setOnClickListener(this);

        //TODO Editing problem
//        mText.setOnClickListener(this);
//        mText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                mText.setText("onClick + " + System.currentTimeMillis());
//                return false;
//            }
//        });
//        mIcon .setOnClickListener(this);

    }

    public void setMarker(MyMarker marker) {
        mMarker = marker;
        mText.setText(marker.getTitle());
        mSnippet.setText(marker.getSnippet());
        Picasso.with(mContext).load(Uri.parse(marker.getIconUri())).into(mIcon);
//        mIcon.setImageURI(Uri.parse(marker.getIconUri()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibDelete_MMV:
                mListener.actionDelete(mMarker);
                break;
            case R.id.ibEdit_MMV:
                mListener.actionEdit(mMarker, mText.getText().toString());
                break;
        }
//        mText.setText("onClick + " + System.currentTimeMillis());
    }
}