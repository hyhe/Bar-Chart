package com.example.hyhe.barchart.BarChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.hyhe.barchart.R;


/**
 * desc: 气泡框
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-12
 */
class PopView extends View {
    Paint backgroundPaint;
    Paint textPaint;

    String text = "0元/平米";

    int space = 10;

    int padding;

    int offset = 0;

    public PopView(Context context) {
        super(context);
        init(context, null);
    }

    public PopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setText(String t) {
        text = t;
        width = -1;
        requestLayout();
        invalidate();
    }

    public void setTextSize(int textSize) {
        textPaint.setTextSize(textSize);
    }

    //设置小三角形的偏移量
    public void setOffset(int offset) {
        this.offset = offset;
        invalidate();
    }


    void init(Context context, AttributeSet attrs) {
        backgroundPaint = new Paint();
        textPaint = new Paint();

        backgroundPaint.setColor(Color.parseColor("#ebebeb"));

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.popview_text_size));
        textPaint.setColor(Color.parseColor("#69A410"));

        padding = getResources().getDimensionPixelSize(R.dimen.popview_padding);
    }

    public int getContentWidth() {

        return getTextWidth() + padding * 2;
    }

    int width = -1;

    private int getTextWidth() {
        if (width == -1) {
            if (text != null) {
                width = (int) textPaint.measureText(text, 0, text.length());
            }
        }
        return width;
    }

    public int getTextHeight() {
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        return fontMetricsInt.descent - fontMetricsInt.ascent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        setMeasuredDimension(getContentWidth(),

                getResources().getDimensionPixelSize(R.dimen.popview_h) + space);

    }

    @Override
    protected void onDraw(Canvas canvas) {


        RectF rectF = new RectF(0, 0, getContentWidth(), getHeight() - space);

        Path path = new Path();
        path.moveTo(getContentWidth() / 2 - space - offset, getHeight() - space);
        path.lineTo(getContentWidth() / 2 + space - offset, getHeight() - space);
        path.lineTo(getContentWidth() / 2 - offset, getHeight());

        canvas.drawPath(path, backgroundPaint);

        canvas.drawRoundRect(rectF, getResources().getDimensionPixelSize(R.dimen.popview_r) * 1.0f
                , getResources().getDimensionPixelSize(R.dimen.popview_r) * 1.0f, backgroundPaint);

        canvas.drawText(text, getContentWidth() / 2 , getTextHeight() + 5, textPaint);
        super.onDraw(canvas);


    }
}
