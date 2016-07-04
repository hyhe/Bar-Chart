package com.example.hyhe.barchart.BarChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.util.List;

/**
 * desc:
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-11
 */
class XAxisView extends BaseAttrsView {


    Paint paint;
    public XAxisView(Context context) {
        super(context);
    }


    public XAxisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(axisTextSize);
        paint.setColor(axisTextColor);
    }

    public void setDatas(List<String> xTitles) {
        this.xTitles = xTitles;
    }



    List<String> xTitles;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, getContentHeight());

    }

    public int getContentHeight() {
        return getTextHeight() + axisPadding;
    }

    private int getTextHeight() {

        String text = "0.0";
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds.height();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        if (xTitles == null || xTitles.size() == 0)
            return;
        int perWidth = width / xTitles.size();
        for (int i = 0; i < xTitles.size(); i++) {
            
            canvas.drawText(xTitles.get(i), perWidth * (i + 0.5f), getHeight()-5, paint);
        }

    }
}
