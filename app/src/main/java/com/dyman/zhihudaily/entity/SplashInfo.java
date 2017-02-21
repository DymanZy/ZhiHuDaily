package com.dyman.zhihudaily.entity;

/**
 * Created by dyman on 2017/2/19.
 */

public class SplashInfo {

    /**
     * text : © Fido Dido(图片版权信息)
     * img : http://p2.zhimg.com/10/7b/107bb4894b46d75a892da6fa80ef504a.jpg （图片的URL）
     */

    private String text;
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
