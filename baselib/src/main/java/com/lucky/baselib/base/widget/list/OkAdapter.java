package com.lucky.baselib.base.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liuxuehao on 17/4/11.
 */

public class OkAdapter<V> extends RecyclerView.Adapter<OkNormalViewHolder> {
    public static final int TYPE_NORMAL_ITEM = 0;
    public static final int TYPE_HEADER_ITEM = 1;
    public static final int TYPE_FOOTER_ITEM = 2;

    private List<V> mData;
    private Context mContext;

    public OkAdapter(List<V> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public OkNormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEADER_ITEM:
                break;
            case TYPE_NORMAL_ITEM:
                break;
            case TYPE_FOOTER_ITEM:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(OkNormalViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
