package com.dyman.zhihudaily.module.common;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.module.home.HomePageFragment;
import com.dyman.zhihudaily.module.news.NewsDetailActivity;
import com.dyman.zhihudaily.module.section.SectionTotalFragment;
import com.dyman.zhihudaily.module.theme.ThemeListFragment;
import com.dyman.zhihudaily.utils.common.ToastUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Fragment[] fragments;

    private int currentTabIndex;

    private int index;

    private long exitTime;

    private HomePageFragment mHomePageFragment;

    private String[] titles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
    }


    private void initView() {
        //  初始化Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  抽屉布局控件
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //  设置抽屉菜单监听
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //  抽屉头部控件监听
        View headerLayout = navigationView.getHeaderView(0);
        headerLayout.findViewById(R.id.userInfo_ll_nav_header_main).setOnClickListener(this);
        headerLayout.findViewById(R.id.userCollectInfo_ll_nav_header_main).setOnClickListener(this);
        headerLayout.findViewById(R.id.offlineDownload_ll_nav_header_main).setOnClickListener(this);
    }


    private void initFragment() {
        mHomePageFragment = HomePageFragment.newInstance();
        ThemeListFragment mThemeListFragment = ThemeListFragment.newInstance();
        SectionTotalFragment mSectionTotalFragment = SectionTotalFragment.newInstance();
        //...

        fragments = new Fragment[] {mHomePageFragment, mThemeListFragment, mSectionTotalFragment};
        titles = new String[] {"ZhiHuDaily", "主题日报", "栏目总览", "过往消息"};

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_app_bar_main, mHomePageFragment)
                .show(mHomePageFragment).commit();
    }

    /**
     *  侧滑菜单头部控件的点击监听
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.userInfo_ll_nav_header_main:
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
                break;

            case R.id.userCollectInfo_ll_nav_header_main:
                Intent it1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it1);
                break;

            case R.id.offlineDownload_ll_nav_header_main:
                ToastUtil.ShortToast("待完善");
                break;
        }
    }


    /**
     *  先退出菜单，再退出应用
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitAPP();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mode_night:
                ToastUtil.ShortToast("待完善");
                break;
            case R.id.action_settings:
                Intent it = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(it);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_hots:
                // 主页, 最新消息
                changeFragmentIndex(item, 0);
                break;
            case R.id.nav_themes:
                // 主题日报列表
                changeFragmentIndex(item, 1);
                break;
            case R.id.nav_sections:
                // 专栏列表
                changeFragmentIndex(item, 2);
                break;
            case R.id.nav_before:
                //  过往消息
                ToastUtil.ShortToast("有待完善");
                break;
            case R.id.nav_share:
                // 分享
                ToastUtil.ShortToast("有待完善");
                break;
            case R.id.nav_send:
                // 发送
                ToastUtil.ShortToast("有待完善");
                break;
        }

        return true;
    }


    /**
     *  Fragment切换
     */
    private void switchFragment() {

        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container_app_bar_main, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }


    /**
     *  切换 Fragment 的下标
     * @param item
     * @param currentIndex
     */
    private void changeFragmentIndex(MenuItem item, int currentIndex) {

        index = currentIndex;
        switchFragment();
        item.setChecked(true);
        getSupportActionBar().setTitle(titles[currentIndex]);
    }


    /**
     *  双击退出APP
     */
    private void exitAPP() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


}
