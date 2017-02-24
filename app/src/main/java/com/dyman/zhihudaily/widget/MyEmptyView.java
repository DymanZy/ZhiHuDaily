package com.dyman.zhihudaily.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dyman.zhihudaily.R;

/**
 * Created by dyman on 2017/2/23.
 */

public class MyEmptyView extends RelativeLayout {

    private ImageView emptyImage;
    private TextView emptyTips;

    public MyEmptyView(Context context) {
        this(context, null);
    }

    public MyEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_empty, this, true);
        emptyImage = (ImageView) findViewById(R.id.image_view_empty);
        emptyTips = (TextView) findViewById(R.id.content_view_empty);
    }


    private void setEmptyImage(Bitmap bitmap) {
        emptyImage.setImageBitmap(bitmap);
    }


    private void setEmptyTips(String tips) {
        emptyTips.setText(tips);
    }



}
