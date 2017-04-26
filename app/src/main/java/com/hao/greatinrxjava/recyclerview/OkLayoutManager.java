package com.hao.greatinrxjava.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuxuehao on 17/1/18.
 */

public class OkLayoutManager extends RecyclerView.LayoutManager {
    private int verticalScrollOffset = 0;
    private int totalHeight = 0;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //没有item，直接返回
        if (getItemCount() <= 0) return;

        //跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) return;

        detachAndScrapAttachedViews(recycler);

        int offsetY = 0;
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, 0, offsetY, width, offsetY + height);

            offsetY += height;

            totalHeight += height;
        }

        totalHeight = Math.max(totalHeight, getVerticalSpace());
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int travel = dy;

        if (verticalScrollOffset + dy < 0) {  //滚动到最顶部
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {  //滚动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        verticalScrollOffset += travel;
        offsetChildrenVertical(-travel);

        Log.d("lxh", "count of childView = " + getChildCount());

        return travel;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
