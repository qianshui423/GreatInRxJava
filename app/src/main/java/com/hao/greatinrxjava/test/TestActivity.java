package com.hao.greatinrxjava.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hao.greatinrxjava.R;
import com.hao.greatinrxjava.recyclerview.SectionDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuxuehao on 17/1/6.
 */

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.rv_test_list)
    RecyclerView mRecyclerView;

    private TestListAdapter mAdapter;
    private List<Flash> mListSource = new ArrayList<>(10);
    private List<Flash> mList = new ArrayList<>(10);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        initDatas();
        initViews();
        testProblem();
    }

    private void initDatas() {
        for (int i = 0; i < 100; i++) {
            Flash flash = new Flash();
            flash.setGroupId("" + i / 10);
            flash.setUrl("https://lh5.ggpht.com/tq3WqEUxtRyBn-d_0t3j6WKNHuJDrmLq-FE3GAYrsAMQFIaS7FIgRLfzzql2SvfvLqto=w300");
            flash.setTitle("Google" + i + "号");
            mListSource.add(flash);
        }
        mList.addAll(mListSource);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (!TextUtils.isEmpty(mList.get(position).getGroupId())) {
                    return mList.get(position).getGroupId();
                }
                return "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                if (!TextUtils.isEmpty(mList.get(position).getGroupId())) {
                    return mList.get(position).getGroupId();
                }
                return "";
            }
        }));
        mAdapter = new TestListAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void testProblem() {
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                Log.d("Recycler", "getAdapterPosition" + holder.getAdapterPosition());
            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mSwipe.isRefreshing();
            }
        });

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                mList.addAll(mListSource);
                mAdapter.onRefreshSuccess(mList);
                mSwipe.setRefreshing(false);
            }
        }, 2000);
    }

    public static class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.TestViewHolder> {
        private List<Flash> mData;
        private Context mContext;

        public TestListAdapter(Context context, List<Flash> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public TestListAdapter.TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test_view, parent, false));
        }

        @Override
        public void onBindViewHolder(TestListAdapter.TestViewHolder holder, int position) {
            holder.bindDataToView(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public void onRefreshSuccess(List<Flash> list) {
            mData = list;
            notifyDataSetChanged();
        }

        public void onLoadMoreSuccess(List<Flash> list) {
            mData.addAll(list);
            notifyItemRangeInserted(mData.size() - list.size(), list.size());
        }

        static class TestViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.sdv_image)
            SimpleDraweeView mSdvImage;
            @BindView(R.id.tv_str)
            TextView mTextView;

            public TestViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindDataToView(Flash flash) {
                mSdvImage.setImageURI(flash.getUrl());
                mTextView.setText(flash.getTitle());
            }
        }
    }
}