package com.example.hyhe.barchart.BarChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
class YAxisView extends BaseAttrsView {

    Paint paint;
    Paint axisLinePaint;

    public YAxisView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        paint = new Paint();
        paint.setColor(axisTextColor);
        paint.setTextSize(axisTextSize);
        paint.setTextAlign(Paint.Align.RIGHT);

        axisLinePaint = new Paint();

        axisLinePaint.setColor(Color.parseColor("#D6D6D6"));
    }

    private List<Integer> datas;

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
    }


    public int getTextHalfHeight() {

        String text = "0.0";
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds.height() / 2;

    }

    public int getContentWidth() {


        return getTextWidth() + axisPadding;
    }

    public int getTextWidth() {
        int width = 0;


        if (datas != null) {
            //计算文字的最大宽度
            for (Integer data : datas) {
                String text = getStringText(data);
                int textwidth = (int)paint.measureText(text,0,text.length());

                if (textwidth > width) {
                    width = textwidth;
                }
            }
        }
        return width;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (datas == null || datas.size() == 0)
            return;
        int height = getHeight();
        int perHeight = (height - getTextHalfHeight() * 2) / (datas.size() - 1);


        for (int i = 0; i < datas.size(); i++) {

            float t = height - perHeight * i - getTextHalfHeight();
            canvas.drawLine(getContentWidth(), t, getWidth(), t, axisLinePaint);
        }

        for (int j = 0; j < datas.size(); j++)

        {
            Integer price = datas.get(j);


            canvas.drawText(getStringText(price), getContentWidth() - axisPadding, height - perHeight * j, paint);
        }
    }

    private String getStringText(int price){

        float text = price/10000f;

        return String.format("%.2f",text);

    }


}
