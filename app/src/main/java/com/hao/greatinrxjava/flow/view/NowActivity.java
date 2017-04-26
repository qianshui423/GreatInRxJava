package com.hao.greatinrxjava.flow.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.hao.greatinrxjava.R;
import com.lucky.baselib.base.BaseActivity;
import com.hao.greatinrxjava.flow.model.bean.Now;
import com.hao.greatinrxjava.flow.presenter.NowPresenter;
import com.hao.greatinrxjava.flow.vendor.Constant;
import com.lucky.baselib.base.widget.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuxuehao on 16/12/26.
 */

public class NowActivity extends BaseActivity<NowPresenter> {
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_fl)
    TextView mTvFl;
    @BindView(R.id.tv_hum)
    TextView mTvHum;
    @BindView(R.id.tv_pcpn)
    TextView mTvPcpn;
    @BindView(R.id.tv_pres)
    TextView mTvPres;
    @BindView(R.id.tv_tmp)
    TextView mTvTmp;
    @BindView(R.id.tv_vis)
    TextView mTvVis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now);
        ButterKnife.bind(this);

        bindListeners();

        mPresenter.dispatchNow(Constant.BEIJING, Constant.HE_WEATHER_KEY, "");
    }

    private void bindListeners() {
        mSwipeRefreshLayout = ButterKnife.findById(this, R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RxBus.get().post("Hello", "我是来自NowActivity的消息");

                mPresenter.dispatchNow(Constant.BEIJING, Constant.HE_WEATHER_KEY, "");

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected NowPresenter createPresenter() {
        return new NowPresenter();
    }

    @Override
    public <T> void display(T data, int which) {
        Now now = (Now) data;
        mTvFl.setText("体感温度：" + now.getFl());
        mTvHum.setText("相对湿度（％）：" + now.getHum());
        mTvPcpn.setText("降水量（mm）：" + now.getPcpn());
        mTvPres.setText("气压：" + now.getPres());
        mTvTmp.setText("温度：" + now.getTmp());
        mTvVis.setText("能见度：" + now.getVis());
    }

    @Override
    public void error(String error, int which) {

    }
}
