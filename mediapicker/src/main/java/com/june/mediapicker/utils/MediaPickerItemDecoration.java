package com.june.mediapicker.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MediaPickerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable itemDecorationDrawable;

    public MediaPickerItemDecoration(Context context, int drawableResourceId) {
        itemDecorationDrawable = ContextCompat.getDrawable(context, drawableResourceId);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int right = itemDecorationDrawable.getIntrinsicWidth();
        int bottom = itemDecorationDrawable.getIntrinsicHeight();

        int position = parent.getChildAdapterPosition(view);

        if (isLastRow(position, parent)) {
            bottom = 0;
        }

        if (isLastColumn(position, parent)) {
            right = 0;
        }

        outRect.set(0, 0, right, bottom);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    //是否为最后一行
    private boolean isLastRow(int position, RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        //GridLayoutManager
        //GridLayoutManager extends LinearLayoutManager
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (((GridLayoutManager) layoutManager).getOrientation() == GridLayoutManager.VERTICAL) {
                //垂直
                if (null != adapter) {
                    return (adapter.getItemCount() - position - 1) < spanCount;
                }
            } else {
                //水平
                return (position + 1) % spanCount == 0;
            }
        }
        //LinearLayoutManager
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                //垂直
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (null != adapter) {
                    return position == adapter.getItemCount() - 1;
                }
            }
            //水平永远应该是false
        }
        return false;
    }

    //是否为最后一列
    private boolean isLastColumn(int position, RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        //GridLayoutManager
        //GridLayoutManager extends LinearLayoutManager
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (((GridLayoutManager) layoutManager).getOrientation() == GridLayoutManager.VERTICAL) {
                //垂直
                return (position + 1) % spanCount == 0;

            } else {
                //水平
                if (null != adapter) {
                    return (adapter.getItemCount() - position - 1) < spanCount;
                }
            }
        }
        //LinearLayoutManager
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                //水平
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (null != adapter) {
                    return position == adapter.getItemCount() - 1;
                }
            }
            //垂直永远应该是false
        }
        return false;
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int top = child.getTop() - params.topMargin;
            int right = left + itemDecorationDrawable.getIntrinsicWidth();
            int bottom = child.getBottom() + params.bottomMargin;
            itemDecorationDrawable.setBounds(left, top, right, bottom);
            itemDecorationDrawable.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.bottomMargin;
            int right = child.getRight() + params.rightMargin;
            int bottom = top + itemDecorationDrawable.getMinimumHeight();
            itemDecorationDrawable.setBounds(left, top, right, bottom);
            itemDecorationDrawable.draw(c);
        }
    }
}
