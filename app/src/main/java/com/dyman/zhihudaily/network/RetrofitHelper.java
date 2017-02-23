package com.dyman.zhihudaily.network;

import android.util.Log;

import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.network.api.ZhiHuAppService;
import com.dyman.zhihudaily.network.auxiliary.ApiConstants;
import com.dyman.zhihudaily.utils.common.CommonUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *  Retrofit帮助类
 *
 * Created by dyman on 2017/2/19.
 */

public class RetrofitHelper {
    private static final String TAG = "RetrofitHelper";

    private static OkHttpClient mOkHttpClient;


    static {
        initOkHttpClient();
    }


    public static ZhiHuAppService getZhiHuAPI() {

        return createApi(ZhiHuAppService.class, ApiConstants.ZHIHU_BASE_URL);
    }

    private static <T> T createApi(Class<T> clazz, String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     *  初始化OkHttpClient,设置缓存,设置超时时间,设置打印日志
     */
    private static void initOkHttpClient() {
        Log.i(TAG, "--------  初始化OkHttpClient,设置缓存,设置超时时间,设置打印日志  -------");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(ZhiHuDailyApp.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 10);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())// 自定义添加缓存
                            .addNetworkInterceptor(new StethoInterceptor())// faceBook的网络调试工具，可能要翻墙
                            .retryOnConnectionFailure(true)// 开启重连尝试
                            .connectTimeout(30, TimeUnit.SECONDS)// 超时设置
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }

    }


    /**
     *  为OkHttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okHttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            // 有网络时，设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(ZhiHuDailyApp.getInstance())) {
                // 有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                // 无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(ZhiHuDailyApp.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }


}
