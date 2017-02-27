package com.dyman.zhihudaily.database.db;

/**
 * Created by dyman on 2017/2/27.
 */

public class TableConfig {

    public static final String TABLE_READ_SCHEDULE = "read_schedule";

    public static class ReadSchedule {

        public static final String ID = "id";   // 记录 ID, 自增
        public static final String ARTICLE_ID = "articleID";   //  文章的 ID
        public static final String READ_TIME = "readTime";     //  上次阅读的时间
        public static final String READ_RATIO = "readRatio";   //  上次阅读的进度
    }
}
