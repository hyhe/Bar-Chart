package com.example.hyhe.barchart.BarChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * desc:
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-18
 */
public class UnitTextView extends BaseAttrsView {

    String text = "";

    Paint paint;

    public UnitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setTextSize(axisTextSize);
        paint.setColor(axisTextColor);
    }

    public void setText(String text) {
        if(TextUtils.isEmpty(text))
            return;
        this.text = text;
        requestLayout();
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getTextWidth(), getTextHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(text, 0, getTextHeight() - 5, paint);

    }

    private int getTextWidth() {
        if(!TextUtils.isEmpty(text)) {
            Rect textBounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), textBounds);
            return textBounds.width();
        }else
            return 0;
    }


    public int getTextHeight() {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();


        return fontMetrics.descent - fontMetrics.ascent;
    }
}



