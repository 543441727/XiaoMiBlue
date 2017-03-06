package com.qianmo.xiaomiblue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianmo.xiaomiblue.R;

/**
 * Created by Administrator on 2017/3/6 0006.
 * E-Mail：543441727@qq.com
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private static final int ITEM_COUNT = 10;
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new HeadViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_head, parent, false));
        }
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
