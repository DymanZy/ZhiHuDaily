package com.dyman.zhihudaily.entity;

/**
 *  软件版权信息
 *
 * Created by dyman on 2017/2/19.
 */

public class AppVersionInfo {


    /**
     * status : 0
     * latest : 2.2.0
     */

    private int status;
    private String latest;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }
}
