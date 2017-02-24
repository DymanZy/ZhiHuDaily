package com.dyman.zhihudaily.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
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
    private ImageView mHeaderMask;
    private ImageView mImgView;
    private TextView mTextView;
    private TextView mImgSourceTv;

    public MyImageTextLayout(Context context) {
        this(context, null);
    }

    public MyImageTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        LayoutInflater.from(context).inflate(R.layout.view_image_text, this, true);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.body_image_text_view);
        mHeaderMask = (ImageView) findViewById(R.id.mask);
        mImgView = (ImageView) findViewById(R.id.image_iv);
        mTextView = (TextView) findViewById(R.id.title_tv);
        mImgSourceTv = (TextView) findViewById(R.id.imageSource_tv);

        mImgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    /** 设置图片接口 */
    public void setImageBitmap(Bitmap bitmap) {
        mImgView.setImageBitmap(bitmap);
    }

    /** 设置文字接口 */
    public void setTitle(String str) {
        mTextView.setText(str);
    }

    /** 设置文字大小 */
    public void setTextSize(float size) {
        mTextView.setTextSize(size);
    }

    /** 设置图片版权信息 */
    public void setImageSourceInfo(String imageSource) {
        mImgSourceTv.setText(imageSource);
    }

    /** 设置点击接口 */
    public void setOnClick(OnClickListener listener) {
        mRelativeLayout.setOnClickListener(listener);
    }

    /** 返回 ImageView 方便网络加载图片 */
    public ImageView getImageView() {return mImgView;}

    /** 是否隐藏头部掩饰控件 */
    public void isHideHeaderMask(boolean b) {
        if (b) {
            mHeaderMask.setVisibility(GONE);
        } else {
            mHeaderMask.setVisibility(VISIBLE);
        }
    }

}
