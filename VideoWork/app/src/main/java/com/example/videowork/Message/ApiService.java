package com.example.videowork.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    //地址：https://beiyou.bytedance.com/api/invoke/video/invoke/video
    @GET("api/invoke/video/invoke/video")
    Call<List<Data>> getArticles();

}
