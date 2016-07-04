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
 * date: 16-4-13
 */
class TitleView extends BaseAttrsView {

    Paint iconPaint;
    Paint textPaint;


    int iconPadding = 20;

    int iconRadius = 10;

    int titlePadding = 60;


    List<Title> titles;

    int paddingTop = 20;


    public void setDatas(List<? extends Title> titles) {
        this.titles = (List<Title>) titles;
        requestLayout();
        invalidate();
    }


    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        iconPaint = new Paint();
        textPaint = new Paint();

        textPaint.setColor(titleTextColor);
        textPaint.setTextSize(titleTextSize);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getTextWidth(), getTextHeight() + paddingTop);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (titles == null || titles.size() == 0)
            return;


        int offset = 0;
        for (int i = 0; i < titles.size(); i++) {


            Rect rect = new Rect();
            textPaint.getTextBounds(titles.get(i).getText(), 0, titles.get(i).getText().length(),
                    rect);

            iconPaint.setColor(titles.get(i).getColor());
            canvas.drawCircle((offset * i) + iconRadius,
                    getTextHeight() / 2 + paddingTop, iconRadius, iconPaint);

            canvas.drawText(titles.get(i).getText(), (offset * i) + iconRadius * 2 + iconPadding,
                    getTextHeight() - 5 + paddingTop, textPaint);

            offset += iconRadius * 2 + iconPadding + rect.width() + titlePadding;
        }


    }


    public int getTextHeight() {
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();


        return fontMetrics.descent - fontMetrics.ascent;
    }


    public int getTextWidth() {
        int offset = 0;

        if (titles != null && titles.size() > 0) {
            for (int i = 0; i < titles.size(); i++) {

                Rect rect = new Rect();
                textPaint.getTextBounds(titles.get(i).getText(), 0, titles.get(i).getText().length(),
                        rect);


                offset += iconRadius * 2 + iconPadding + rect.width() + (i == 0 ? 0 : titlePadding);
            }
        }
        return offset + 10;
    }


}
