package com.example.hyhe.barchart.BarChart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.example.hyhe.barchart.R;


/**
 * desc:
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-18
 */
class BaseAttrsView extends View {
    protected int axisPadding = 30;
    protected float axisTextSize = 30;
    protected int axisTextColor = Color.LTGRAY;

    protected float titleTextSize = 30;
    protected int titleTextColor = Color.LTGRAY;


    public BaseAttrsView(Context context) {
        super(context);
        init(context, null);
    }

    public BaseAttrsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BarChart);
            try {
                axisPadding = a.getDimensionPixelSize(R.styleable.BarChart_axisPadding, 30);
                axisTextColor = a.getColor(R.styleable.BarChart_axisTextColor, Color.LTGRAY);
                axisTextSize = a.getDimensionPixelSize(R.styleable.BarChart_axisTextSize, 34);
                titleTextSize = a.getDimensionPixelSize(R.styleable.BarChart_titleTextSize, 36);
                titleTextColor = a.getColor(R.styleable.BarChart_titleContentColor, Color.LTGRAY);
            } finally {
                a.recycle();
            }
        } else {

        }


    }
}
