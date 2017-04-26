package com.hao.greatinrxjava.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.hao.greatinrxjava.R;

/**
 * Created by liuxuehao on 17/1/18.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private DecorationCallback mCallback;
    private TextPaint mTextPaint;
    private Paint mPaint;
    private int mTopGap;
    private int mAlignLeft;

    public SectionDecoration(Context context, DecorationCallback callback) {
        Resources res = context.getResources();
        mCallback = callback;
        //设置悬浮栏的画笔－－mPaint
        mPaint = new Paint();
        mPaint.setColor(0x000000);

        //设置悬浮栏中文本的画笔－－mTextPaint
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(res.getDimensionPixelSize(R.dimen.text_paint));
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        //决定悬浮栏的高度
        mTopGap = res.getDimensionPixelSize(R.dimen.sectioned_top);
        //决定文本的显示位置
        mAlignLeft = res.getDimensionPixelSize(R.dimen.sectioned_alignLeft);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        String preGroupId = "";
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupId = groupId;
            groupId = mCallback.getGroupId(position);
            if (groupId.equals("-1") || groupId.equals(preGroupId)) continue;

            String textLine = mCallback.getGroupFirstLine(position).toLowerCase();

            if (TextUtils.isEmpty(textLine)) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(mTopGap, view.getTop());
            if (position + 1 < itemCount) {
                String nextGroupId = mCallback.getGroupId(position + 1);
                if (!nextGroupId.equals(groupId) && viewBottom < textY) {
                    textY = viewBottom;
                }
            }

            c.drawRect(left, textY - mTopGap, right, textY, mPaint);
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            int baseline = (int) (textY + textY - mTopGap - fontMetrics.bottom - fontMetrics.top) / 2;
            c.drawText(textLine, left + 2 * mAlignLeft, baseline, mTextPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String groupId = mCallback.getGroupId(pos);
        if (groupId.equals("-1")) return;
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mTopGap;
            if ("".equals(mCallback.getGroupId(pos))) {
                outRect.top = 0;
            }
        } else {
            outRect.top = 0;
        }
    }

    private boolean isFirstInGroup(int pos) {
        if (0 == pos) {
            return true;
        } else {
            String preGroupId = mCallback.getGroupId(pos - 1);
            String groupId = mCallback.getGroupId(pos);
            if (preGroupId.equals(groupId)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public interface DecorationCallback {
        String getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
