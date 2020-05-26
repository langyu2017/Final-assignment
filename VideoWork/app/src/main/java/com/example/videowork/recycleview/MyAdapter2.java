package com.example.videowork.recycleview;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videowork.Message.Data;
import com.example.videowork.R;
import com.example.videowork.recycleview.OnRecyclerItemClickListener;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder>{
    private List<Data> dataList;
    private Context context;

    private OnRecyclerItemClickListener mOnItemClickListener = null;

    public MyAdapter2(Context context) {
        this.context = context;
    }
    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        mOnItemClickListener = listener;
    }
    public void setData(List<Data> myDataset) {
        dataList = myDataset;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView name;
        TextView description;
        public VideoView videoView;  //视频

        public ViewHolder (View view)
        {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            videoView = view.findViewById(R.id.video_view);

            //设置图片的相对于屏幕的宽高比
            int width = ((Activity) avatar.getContext()).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = avatar.getLayoutParams();
            params.width = width/2;
            //params.height =  (int) (200 + Math.random() * 400) ;
            avatar.setLayoutParams(params);
            videoView.setLayoutParams(params);
            name.setLayoutParams(params);

            FrameLayout.LayoutParams lytp = new FrameLayout.LayoutParams(width*2/5, FrameLayout.LayoutParams.MATCH_PARENT);
            lytp .gravity = Gravity.BOTTOM;
            description.setLayoutParams(lytp);



            //将监听传递给自定义接口
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(getAdapterPosition(),dataList);
                    }
                }
            });
        }
    }
    public  MyAdapter2 (List <Data> itemList){
        dataList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_avatar,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Log.d("toString", "onBindViewHolder: "+position+" to "+dataList.get(1));
        Data item = dataList.get(position);
        holder.name.setText(item.nickname);
        holder.description.setText(item.description);
        //holder.avatar.setImageResource(R.drawable.circle_big_red);
        //holder.videoView.setVideoURI(Uri.parse(item.feedurl));
        Glide.with(context).load(item.avatar).into(holder.avatar);
    }

    @Override
    public int getItemCount(){
        return dataList == null ? 0 : dataList.size();
    }
}
