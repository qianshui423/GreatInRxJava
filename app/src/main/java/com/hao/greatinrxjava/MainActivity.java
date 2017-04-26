package com.hao.greatinrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hao.greatinrxjava.flow.view.NowActivity;
import com.hao.greatinrxjava.rxjava.backpressure.BackPressure;
import com.lucky.baselib.base.BaseActivity;
import com.lucky.baselib.base.widget.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by liuxuehao on 16/12/21.
 * <p>
 * mTvNow.setText(new String(Character.toChars(0x2618)));
 * String str = new String(Character.toChars(0x1F340));
 * char[] chs = new char[2];
 * chs[0] = '\ue110';
 * chs[1] = '\ue15a';
 * CharSequence cs = "\uE110\uE15A";
 * int unicode = Character.toCodePoint(chs[0], chs[1]);
 */

public class MainActivity extends BaseActivity
        implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Observable<String> mHello;

    /*@BindView(R.id.tv_observable_range)
    TextView mTvObservableRange;
    @BindView(R.id.tv_observable_interval)
    TextView mTvObservableInterval;
    @BindView(R.id.tv_flowable_range)
    TextView mTvFlowableRange;
    @BindView(R.id.tv_flowable_interval)
    TextView mTvFlowableInterval;
    @BindView(R.id.tv_flowable_onbackpressurebuffer)
    TextView mTvBuffer;
    @BindView(R.id.tv_flowable_onbackpressuredrop)
    TextView mTvDrop;
    @BindView(R.id.tv_flowable_onbackpressurelatest)
    TextView mTvLatest;
    @BindView(R.id.tv_now)
    TextView mTvNow;*/

    private TextView mTvObservableRange;
    private TextView mTvObservableInterval;
    private TextView mTvFlowableRange;
    private TextView mTvFlowableInterval;
    private TextView mTvBuffer;
    private TextView mTvDrop;
    private TextView mTvLatest;
    private TextView mTvNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHello = RxBus.get().register("Hello", String.class);

        mHello.subscribe(s -> {
            Log.d(TAG, "" + s);
        });

        bindListeners();
    }

    public void bindListeners() {
        mTvObservableRange = (TextView) findViewById(R.id.tv_observable_range);
        mTvObservableInterval = (TextView) findViewById(R.id.tv_observable_interval);
        mTvFlowableRange = (TextView) findViewById(R.id.tv_flowable_range);
        mTvFlowableInterval = (TextView) findViewById(R.id.tv_flowable_interval);
        mTvBuffer = (TextView) findViewById(R.id.tv_flowable_onbackpressurebuffer);
        mTvDrop = (TextView) findViewById(R.id.tv_flowable_onbackpressuredrop);
        mTvLatest = (TextView) findViewById(R.id.tv_flowable_onbackpressurelatest);
        mTvNow = (TextView) findViewById(R.id.tv_now);

        mTvObservableRange.setOnClickListener(this);
        mTvObservableInterval.setOnClickListener(this);
        mTvFlowableRange.setOnClickListener(this);
        mTvFlowableInterval.setOnClickListener(this);
        mTvBuffer.setOnClickListener(this);
        mTvDrop.setOnClickListener(this);
        mTvLatest.setOnClickListener(this);
        mTvNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_observable_range:
                BackPressure.testObservableRange();
                break;
            case R.id.tv_observable_interval:
                BackPressure.testObservaleInterval();
                break;
            case R.id.tv_flowable_range:
                BackPressure.testFlowableRange();
                break;
            case R.id.tv_flowable_interval:
                BackPressure.testFlowableInterval();
                break;
            case R.id.tv_flowable_onbackpressurebuffer:
                BackPressure.testFlowableIntervalBuffer();
                break;
            case R.id.tv_flowable_onbackpressuredrop:
                BackPressure.testFlowableIntervalDrop();
                break;
            case R.id.tv_flowable_onbackpressurelatest:
                BackPressure.testFlowableIntervalLatest();
                break;
            case R.id.tv_now:
                startActivity(new Intent(this, NowActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("Hello", mHello);
    }
}
