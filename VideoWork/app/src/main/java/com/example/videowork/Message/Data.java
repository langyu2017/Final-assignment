package com.example.videowork.Message;

import com.google.gson.annotations.SerializedName;

public class Data {

    //"_id":"5e9830b0ce330a0248e89d86",
    //"feedurl":"http://jzvd.nathen.cn/video/1137e480-170bac9c523-0007-1823-c86-de200.mp4",
    //"nickname":"王火火",
    //"description":"这是第一条Feed数据",
    //"likecount":10000,"
    //avatar":"http://jzvd.nathen.cn/snapshot/f402a0e012b14d41ad07939746844c5e00005.jpg"

    @SerializedName("_id")
    public String _id;
    @SerializedName("feedurl")
    public String feedurl;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("likecount")
    public int likecount;
    @SerializedName("avatar")
    public String avatar;

    @Override
    public String toString() {
        return "DATA{" +
                "_id=" + _id +
                ", feedurl=" + feedurl +
                ", nickname=" + nickname +
                ", description=" + description +
                ", likecount=" + likecount +
                ", avatar=" + avatar +
                '}';
    }
}
