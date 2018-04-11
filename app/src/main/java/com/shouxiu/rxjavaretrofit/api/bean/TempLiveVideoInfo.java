package com.shouxiu.rxjavaretrofit.api.bean;

/**
 * @author yeping
 * @date 2018/4/11 10:13
 * TODO
 */

public class TempLiveVideoInfo {
    private int room_id;
    private String hls_url;

    public int getRoom_id() {
        return room_id;
    }

    public String getHls_url() {
        return hls_url;
    }

    @Override
    public String toString() {
        return "GsonDouyuRoom [room_id=" + room_id + ", hls_url" + hls_url + "]";
    }
}
