package com.se25.healthcare.Support;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class ItemSquare extends FrameLayout {
    public ItemSquare(Context context) {
        super(context);
    }

    public ItemSquare(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemSquare(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
