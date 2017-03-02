package com.dyman.zhihudaily.network.api;

import com.dyman.zhihudaily.entity.AppVersionInfo;
import com.dyman.zhihudaily.entity.CommentsInfo;
import com.dyman.zhihudaily.entity.NewsBeforeInfo;
import com.dyman.zhihudaily.entity.NewsDetailInfo;
import com.dyman.zhihudaily.entity.NewsHotInfo;
import com.dyman.zhihudaily.entity.NewsLatestInfo;
import com.dyman.zhihudaily.entity.RecommendersInfo;
import com.dyman.zhihudaily.entity.SectionBeforeInfo;
import com.dyman.zhihudaily.entity.SectionDetailInfo;
import com.dyman.zhihudaily.entity.SectionsInfo;
import com.dyman.zhihudaily.entity.SplashInfo;
import com.dyman.zhihudaily.entity.StoryExtraInfo;
import com.dyman.zhihudaily.entity.ThemeInfo;
import com.dyman.zhihudaily.entity.ThemeListInfo;
import com.dyman.zhihudaily.entity.ThemeStoryInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 *  知乎日报api
 *
 * Created by dyman on 2017/2/19.
 */
public interface ZhiHuAppService {

    /**
     *  获取启动界面图像
     * @param size 格式为：1080*1776
     * @return
     */
    @GET("api/4/start-image/{size}")
    Observable<SplashInfo> getSplash(@Path("size") String size);

    /**
     *  查询软件版本
     * @return
     */
    @GET("api/4/version/android/2.3.0")
    Observable<AppVersionInfo> getAppVersion();

    /**
     *  获取最新消息
     * @return
     */
    @GET("api/4/news/latest")
    Observable<NewsLatestInfo> getNewsLatestList();

    /**
     *  获取消息具体内容
     * @param new_id 消息对应ID
     * @return
     */
    @GET("api/4/news/{new_id}")
    Observable<NewsDetailInfo> getNewsDetail(@Path("new_id") String new_id);

    /**
     *  获取过往消息
     * @param time 若果需要查询2013年11月18日的消息，before 后的数字应为 20131119
     * @return
     */
    @GET("api/4/news/before/{time}")
    Observable<NewsLatestInfo> getNewsBeforeList(@Path("time") String time);

    /**
     *  获取对应新闻的额外信息，如评论数量，所获得"赞"的数量
     * @param new_id 新闻对应的ID
     * @return
     */
    @GET("api/4/story-extra/{new_id}")
    Observable<StoryExtraInfo> getStoryExtra(@Path("new_id") String new_id);

    /**
     *  获取新闻对应长评论信息
     * @param new_id 新闻对应的ID
     * @return
     */
    @GET("api/4/story/{new_id}/long-comments")
    Observable<CommentsInfo> getCommentsLong(@Path("new_id") String new_id);

    /**
     *  获取新闻对应短评论信息
     * @param new_id 新闻对应的ID
     * @return
     */
    @GET("api/4/story/{new_id}/short-comments")
    Observable<CommentsInfo> getCommentsShort(@Path("new_id") String new_id);

    /**
     *  获取主题日报列表信息
     * @return
     */
    @GET("api/4/themes")
    Observable<ThemeListInfo> getThemesList();

    /**
     *  获取特定主题的信息
     * @param theme_id 该主题对应的ID
     * @return
     */
    @GET("api/4/theme/{theme_id}")
    Observable<ThemeInfo> getThemeInfo(@Path("theme_id") String theme_id);

    /**
     *  获取消息具体内容
     * @param story_id 消息对应ID
     * @return
     */
    @GET("api/4/news/{story_id}")
    Observable<ThemeStoryInfo> getThemeStoryInfo(@Path("story_id") String story_id);

    /**
     *  获取热门信息
     * @return
     */
    @GET("api/3/news/hot")
    Observable<NewsHotInfo> getNewsHotList();

    /**
     *  栏目总览
     * @return
     */
    @GET("api/3/sections")
    Observable<SectionsInfo> getSectionList();


    /**
     *  获取栏目具体消息
     * @param section_id 该栏目对应的ID
     * @return
     */
    @GET("api/3/section/{section_id}")
    Observable<SectionDetailInfo> getSection(@Path("section_id") String section_id);

    /**
     *  查看新闻的推荐者
     * @param new_id
     * @return
     */
    @GET("api/4/story/{new_id}/recommenders")
    Observable<RecommendersInfo> getRecommenders(@Path("new_id") String new_id);

    /**
     *  获取某个专栏之前的新闻信息
     * @param section_id 专栏ID
     * @param timestamp 时间戳
     * @return
     */
    @GET("api/4/section/{section_id}/before/{timestamp}")
    Observable<SectionBeforeInfo> getSectionBeforeInfo(@Path("section_id") String section_id,
                                                       @Path("timestamp") String timestamp);

    /**
     *  查看Editor的主页
     * @param editor_id 编辑对应的ID
     * @return
     */
    @GET("api/4/editor/{editor_id}/profile-page/android")
    Observable<String> getEditorPage(@Path("editor_id") String editor_id);

}
