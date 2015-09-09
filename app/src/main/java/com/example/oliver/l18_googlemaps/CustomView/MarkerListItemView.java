package com.example.oliver.l18_googlemaps.CustomView;

import android.content.Context;
import android.net.Uri;
import android.view.View;
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
    private TextView mSnippet, mText;
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

        mText       = (TextView) findViewById(R.id.tvText_MMV);
        mIcon       = (ImageView) findViewById(R.id.ivIcon_MMV);
        mSnippet    = (TextView) findViewById(R.id.tvSnippet_MMV);

      findViewById(R.id.ibDelete_MMV).setOnClickListener(this);
    }

    public void setMarker(MyMarker marker) {
        mMarker = marker;
        mText.setText(marker.getTitle());
        mSnippet.setText(marker.getSnippet());
        Picasso.with(mContext).load(Uri.parse(marker.getIconUri())).into(mIcon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibDelete_MMV:
                mListener.actionDelete(mMarker);
                break;
        }
    }
}