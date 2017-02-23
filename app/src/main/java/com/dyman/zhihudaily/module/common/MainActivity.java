package com.dyman.zhihudaily.module.common;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import com.dyman.zhihudaily.utils.common.ToastUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Fragment[] fragments;

    private int currentTabIndex;

    private int index;

    private long exitTime;

    private HomePageFragment mHomePageFragment;


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
        //...

        fragments = new Fragment[] {mHomePageFragment};

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_app_bar_main, mHomePageFragment)
                .show(mHomePageFragment).commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userInfo_ll_nav_header_main:
                ToastUtil.ShortToast("点击了用户信息");
                break;
            case R.id.userCollectInfo_ll_nav_header_main:

                break;
            case R.id.offlineDownload_ll_nav_header_main:

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hots) {
            // Handle the camera action
        } else if (id == R.id.nav_themes) {

        } else if (id == R.id.nav_sections) {

        } else if (id == R.id.nav_before) {

        } else if (id == R.id.nav_share) {
            Intent it = new Intent(MainActivity.this, NewsDetailActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
