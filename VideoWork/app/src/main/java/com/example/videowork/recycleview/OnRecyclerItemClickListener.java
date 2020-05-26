package com.example.videowork.recycleview;

import com.example.videowork.Message.Data;

import java.util.List;


public interface OnRecyclerItemClickListener {
    void onItemClick(int Position, List<Data> messages);
}
