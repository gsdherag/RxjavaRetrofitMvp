package com.shouxiu.rxjavaretrofit.api.bean;

import java.io.Serializable;

/**
 * @author yeping
 * @date 2018/3/31 14:08
 * TODO
 */

public class HomeCateList implements Serializable {

    /**
     * title : 手游
     * show_order : 1
     * identification : 3e760da75be261a588c74c4830632360
     * is_video : 0
     */

    private String title;
    private String show_order;
    private String identification;
    private int is_video;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShow_order() {
        return show_order;
    }

    public void setShow_order(String show_order) {
        this.show_order = show_order;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public int getIs_video() {
        return is_video;
    }

    public void setIs_video(int is_video) {
        this.is_video = is_video;
    }

    @Override
    public String toString() {
        return "{" +
                "title:'" + title + '\'' +
                ", show_order:'" + show_order + '\'' +
                ", identification:'" + identification + '\'' +
                ", is_video:" + is_video +
                '}';
    }
}
