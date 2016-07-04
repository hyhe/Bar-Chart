package com.example.hyhe.barchart.BarChart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hyhe.barchart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-11
 */
class BarView extends View {

    Paint barPaint;

    //


    List<BarDataList> datas = new ArrayList<BarDataList>();

    List<Integer> yAxis;

    Rect selectedRect = null;
    RectItem defaultSelectedRect = null;


    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }


    public RectItem getDefaultSelectedRect() {

        for (int j = 0; j < rectItemList.size(); j++) {
            if (rectItemList.get(j).isDefaultShow) {
                defaultSelectedRect = rectItemList.get(j);
                break;
            }

        }

        return defaultSelectedRect;
    }

    public void clearSelect() {
        selectedRect = null;
        postInvalidate();
    }


    // HashMap<String, Rect> rectMap = new HashMap<String, Rect>();

    List<RectItem> rectItemList = new ArrayList<>();


    int barWidth;
    int xAxisSize = 0;


    void init(Context context, AttributeSet attrs) {
        barPaint = new Paint();

        barPaint.setColor(Color.YELLOW);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BarChart);
            try {
                barWidth = a.getDimensionPixelSize(R.styleable.BarChart_barWidth, 30);
            } finally {
                a.recycle();
            }
        } else {

        }
    }

    public void setDatas(List<BarDataList> datas) {
        this.datas = datas;
        postInvalidate();
    }

    public void setyAxis(List<Integer> yAxis) {
        this.yAxis = yAxis;
    }

    public void setxAxisSize(int size) {
        this.xAxisSize = size;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (datas == null || datas.size() == 0 || yAxis == null || yAxis.size() == 0)
            return;
        int width = getWidth();
        int height = getHeight();


        float max = yAxis.get(yAxis.size() - 1);
        float min = yAxis.get(0);

        int perWidth = getWidth() / xAxisSize;

        int centerWidth = perWidth / 2;
        // rectMap.clear();
        rectItemList.clear();

        //坐标内柱状
        for (int j = 0; j < xAxisSize; j++) {


            for (int m = 0; m < datas.size(); m++) {
                int h = (int) (height * (datas.get(m).getDataItemList().get(j).getData() - min) / (max - min));

                int l = 0;
                if (m == 0) {
                    l = perWidth * j + centerWidth - barWidth * (datas.size() - m) / 2;
                } else {


                    if (rectItemList.get(rectItemList.size() - 1).rect != null)
                        l = rectItemList.get(rectItemList.size() - 1).rect.right;
                }
                int t = height - h;
                int r = l + barWidth;
                int b = height;


                Rect rect = new Rect(l, t, r, b);


                barPaint.setColor(datas.get(m).getColor());

                rectItemList.add(new RectItem(datas.get(m).getDataItemList().get(j).getDataShowText(),
                        rect, datas.get(m).getDataItemList().get(j).isPopViewIsDefaultShow()));


                canvas.drawRect(rect, barPaint);

                if (selectedRect != null && selectedRect.left == l && selectedRect.right == r) {
                    barPaint.setColor(datas.get(m).getSelectedColor());
                    canvas.drawRect(selectedRect, barPaint);
                }

            }


        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "";
            Rect rect = null;


            for (RectItem item : rectItemList) {
                if (item.rect.contains((int) event.getX(), (int) event.getY())) {
                    text = item.showText;
                    rect = item.rect;
                    break;
                }
            }

            if (!TextUtils.isEmpty(text) && rect != null && barClickListener != null) {

                barClickListener.onBarClick(text, rect);
                selectedRect = rect;
                postInvalidate();
            } else {
                barClickListener.onBarClick(null, null);
            }

        }

        return true;
    }

    public int getBarWidth() {
        return barWidth;
    }


    OnBarClickListener barClickListener;

    public void setOnBarClickListener(OnBarClickListener listener) {
        barClickListener = listener;
    }


    static class RectItem {
        String showText;
        Rect rect;
        boolean isDefaultShow;

        public RectItem(String showText, Rect rect, boolean isDefaultShow) {
            this.showText = showText;
            this.rect = rect;
            this.isDefaultShow = isDefaultShow;
        }

        public RectItem(String showText, Rect rect) {
            this.showText = showText;
            this.rect = rect;
        }
    }
}
