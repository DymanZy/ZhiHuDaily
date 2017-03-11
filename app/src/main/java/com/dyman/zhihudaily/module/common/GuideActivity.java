package com.dyman.zhihudaily.module.common;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.GuideViewPagerAdapter;
import com.dyman.zhihudaily.database.db.DataBaseInit;
import com.dyman.zhihudaily.utils.SPUtils;
import com.dyman.zhihudaily.utils.common.SystemUiVisibilityUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private Button startBtn;

    private static final int[] pics = {R.layout.guide_view_a, R.layout.guide_view_b, R.layout.guide_view_c};

    private ImageView[] dots;

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);

        initView();
    }


    private void initView() {
        //  初始化引导页视图列表
        views = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);

            if (i == pics.length - 1) {
                startBtn = (Button) view.findViewById(R.id.nowBegin_btn_guide_view_c);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }

        vp = (ViewPager) findViewById(R.id.guide_vp_activity_guide);
        //  初始化 adapter
        adapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new PageChangeListener());

        initDots();
    }


    /**
     *   初始化引导小点图
     */
    private void initDots() {

        LinearLayout ll = (LinearLayout) findViewById(R.id.dotList_ll_activity_guide);
        dots = new ImageView[pics.length];

        //  循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(true);
    }


    /**
     *  设置当前 View
     * @param position
     */
    private void setCurrView(int position) {

        if (position < 0 || position >= pics.length) return;
        vp.setCurrentItem(position);
    }


    /**
     *  设置当前指示点
     * @param position
     */
    private void setCurrDot(int position) {

        if (position < 0 || position >= pics.length || currentIndex == position) return;
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }


    @Override
    public void onClick(View v) {

        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        setCurrView(position);
        setCurrDot(position);
    }


    /**
     *  转跳到主界面, 并在 SharedPreference 中保存记录
     */
    private void enterMainActivity() {

        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        // 在 SharedPreference 中保存记录
        SPUtils.alreadyOpen();
        finish();
    }


    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

        }

        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurrDot(position);
        }

    }
}
