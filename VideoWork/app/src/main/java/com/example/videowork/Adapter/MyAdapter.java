package com.example.videowork.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videowork.Message.Data;
import com.example.videowork.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Data> mDataset;
    private Context context;
    private int[] img_Author ={R.mipmap.header_icon_1,R.mipmap.header_icon_2,R.mipmap.header_icon_3,R.mipmap.header_icon_4};

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Data> myDataset) {
        mDataset = myDataset;
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public VideoView videoView;  //视频
        public TextView likecount;   //点赞数
        public ImageView cover;      //封面
        public ImageView imagePlay;  //播放图标
        public TextView title;       //标题
        public TextView description;  //描述
        public RelativeLayout rootView;  //RecycleView
        public ImageView author; //作者封面

        public TextView id;
        public TextView name;

        public MyViewHolder(View v) {
            super(v);
            videoView = v.findViewById(R.id.video_view);
            likecount = v.findViewById(R.id.likecount);
            cover = v.findViewById(R.id.img_fengmian);
            imagePlay = v.findViewById(R.id.img_play);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
            rootView = v.findViewById(R.id.root_view);
            author = v.findViewById(R.id.author);
        }
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
     //   holder.videoView.setVideoURI(Uri.parse(mDataset.get(position).feedurl));  //设置播放

        holder.videoView.setVideoURI(Uri.parse(mDataset.get(position).feedurl));
        float likecount = (float)mDataset.get(position).likecount/10000;
        holder.likecount.setText(Float.toString(likecount)+"w");
        holder.title.setText(mDataset.get(position).nickname);
        holder.description.setText(mDataset.get(position).description);
        Glide.with(context).load(mDataset.get(position).avatar).into(holder.cover);
        int author =position%4;
        holder.author.setImageResource(img_Author[author]);
    }

    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}
