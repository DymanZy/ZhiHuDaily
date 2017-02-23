package com.dyman.zhihudaily.utils;

import java.util.List;

/**
 * Created by dyman on 2017/2/23.
 */

public class WebUtils {

    public static final String BASE_URL = "file://android_asset/";
    public static final String MIME_TYPE = "text/html";
    public static final String ENCODING = "utf-8";
    public static final String FAIL_URL = "http://daily.zhihu.com";

    private static final String CSS_LINK_PATTERN = " <link href=\"%s\" type=\"text/css\" rel=\"stylesheet\" />";
    private static final String NIGHT_DIV_TAG_START = "<div class=\"night\">";
    private static final String NIGHT_DIV_TAG_END = "</div>";

    private static final String DIV_IMAGE_PLACE_HOLDED = "class=\"img-place-holder\"";
    private static final String DIV_IMAGE_PLACE_HOLDED_IGNORED = "class=\"img-place-holder-ignored\"";


    public static String buildHtmlWithCss(String html, List<String> cssUrls, boolean isNigheMode) {
        StringBuilder result = new StringBuilder();
        for (String cssUrl: cssUrls) {
            result.append(String.format(CSS_LINK_PATTERN, cssUrl));
        }

        if (isNigheMode) {
            result.append(NIGHT_DIV_TAG_START);
        }
        result.append(html.replace(DIV_IMAGE_PLACE_HOLDED, DIV_IMAGE_PLACE_HOLDED_IGNORED));
        if (isNigheMode) {
            result.append(NIGHT_DIV_TAG_END);
        }
        return result.toString();
    }





}
