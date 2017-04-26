package com.lucky.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.baselib.R;

import static android.view.View.inflate;

/**
 * Created by liuxuehao on 16/12/26.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
        implements View.OnClickListener, IBaseView {

    protected LinearLayout mRootLayout;
    protected ConstraintLayout mSimpleLayout;
    protected Toolbar mToolbar;
    protected ImageView mBack;
    protected TextView mTitle;
    protected ImageView mOptions;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.baselib_activity_base);

        initViews();
        initToolbar();
        initListeners();

        mPresenter = createPresenter();
        if (null != mPresenter) mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) mPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.base_toolbar_back) {
            back();
        } else if (id == R.id.base_toolbar_title) {
            titleClick();
        } else if (id == R.id.base_toolbar_options) {
            optionsClick();
        }
    }

    protected void back() {
        finish();
    }

    protected void titleClick() {

    }

    protected void optionsClick() {

    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void initViews() {
        mRootLayout = (LinearLayout) findViewById(R.id.baselib_root_layout);
        mSimpleLayout = (ConstraintLayout) findViewById(R.id.baselib_toolbar_simple);
        mToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        mBack = (ImageView) findViewById(R.id.base_toolbar_back);
        mTitle = (TextView) findViewById(R.id.base_toolbar_title);
        mOptions = (ImageView) findViewById(R.id.base_toolbar_options);
    }

    protected void initToolbar() {
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
        }

        if (null != mTitle) {
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected void initListeners() {
        mBack.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mOptions.setOnClickListener(this);
    }

    public void setContentView(int layoutId) {
        setContentView(inflate(this, layoutId, null));
    }

    public void setContentView(View view) {
        mRootLayout.addView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (!isChild()) {
            onTitleChanged(getTitle(), getTitleColor());
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (TextUtils.isEmpty(mTitle.getText())) {
            mTitle.setText(title);
        }
    }

    protected void resetTitle() {
        for (int i = mToolbar.getChildCount() - 1; i > 0; i--) {
            mToolbar.removeViewAt(i);
        }
        mBack.setVisibility(View.GONE);
        mTitle.setText("");
        mOptions.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
    }

    /**
     * 自定义标题栏
     *
     * @param layoutRest
     * @return
     */
    private View enableCustomTitle(int layoutRest) {
        resetTitle();
        mSimpleLayout.setVisibility(View.GONE);
        return LayoutInflater.from(this).inflate(layoutRest, null);
    }

    /**
     * 加载自定义View进入Toolbar,返回自定义View
     * PS:自行控制View事件
     *
     * @param layoutRes
     * @return customView
     */
    protected View enableCustomView(int layoutRes) {
        View customView = enableCustomTitle(layoutRes);
        mToolbar.addView(customView, new RelativeLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        return customView;
    }

    protected T createPresenter() {
        return null;
    }

    @Override
    public <T> void display(T data, int which) {

    }

    @Override
    public void error(String error, int which) {

    }
}
