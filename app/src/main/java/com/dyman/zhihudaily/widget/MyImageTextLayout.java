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
 * Created by dyman on 2017/2/20.
 */

public class MyImageTextLayout extends RelativeLayout {

    private static final String TAG = MyImageTextLayout.class.getSimpleName();
    
    private RelativeLayout mRelativeLayout;
    private ImageView mImgView;
    private TextView mTextView;
    private Context mContext;

    public MyImageTextLayout(Context context) {
        this(context, null);
    }

    public MyImageTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        LayoutInflater.from(context).inflate(R.layout.image_text_view, this, true);
        mContext = context;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.body_image_text_view);
        mImgView = (ImageView) findViewById(R.id.image_iv);
        mTextView = (TextView) findViewById(R.id.title_tv);

        mImgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    /** 设置图片接口 */
    public void setImageBitmap(Bitmap bitmap) {
        mImgView.setImageBitmap(bitmap);
    }

    /** 设置文字接口 */
    public void setText(String str) {
        mTextView.setText(str);
    }

    /** 设置文字大小 */
    public void setTextSize(float size) {
        mTextView.setTextSize(size);
    }

    /** 设置点击接口 */
    public void setOnClick(OnClickListener listener) {
        mRelativeLayout.setOnClickListener(listener);
    }

    public ImageView getImageView() {return mImgView;}

}
