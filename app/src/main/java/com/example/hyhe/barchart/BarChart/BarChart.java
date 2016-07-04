package com.example.hyhe.barchart.BarChart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.view.ViewHelper;

import java.util.List;

/**
 * desc:柱状图表
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-12
 */
public class BarChart extends ViewGroup {
    YAxisView yAxisView;
    XAxisView xAxisView;
    BarView barView;
    PopView popView;

    UnitTextView unitTextView;

    TitleView titleView;


    //相关属性

    //柱状图宽度

    //popview背景色

    //popView文字颜色

    //座标轴文字颜色

    //座标轴文字大小


    //相关数据

    //x轴文字数组

    private List<String> xAxisDatas;


    //y轴数据

    private List<Integer> yAxisData;

    //柱状图数据

    private List<BarDataList> chartDatas;

    //y轴单位

    String unitText = "";


    public BarChart(Context context) {
        super(context);
        init(context, null);
    }


    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    /**
     * 图表显示时默认气泡框显示
     */
    public void showDefaultPopView() {
        BarView.RectItem rectItem = barView.getDefaultSelectedRect();
        if (rectItem != null)

            showPopView(rectItem.showText, rectItem.rect);

    }

    /**
     * @param chartDatas 图表数据
     * @param xAxisDatas x轴显示文字
     * @param yAxisData  y轴数据
     * @param unitText   y轴单位
     */
    public void setChartDatas(List<BarDataList> chartDatas,
                              List<String> xAxisDatas,
                              List<Integer> yAxisData,
                              String unitText) {

        this.chartDatas = chartDatas;
        this.xAxisDatas = xAxisDatas;
        this.yAxisData = yAxisData;
        this.unitText = unitText;

        barView.setDatas(chartDatas);
        barView.setyAxis(yAxisData);
        barView.setxAxisSize(xAxisDatas.size());
        xAxisView.setDatas(xAxisDatas);
        yAxisView.setDatas(yAxisData);
        unitTextView.setText(unitText);
        titleView.setDatas(chartDatas);

        barViewAnimationShow = true;


        requestLayout();
        invalidate();


    }

    OnBarClickListener barClickListener;

    public void setOnBarClickListener(OnBarClickListener barClickListener) {
        this.barClickListener = barClickListener;

    }


    void init(final Context context, AttributeSet attrs) {


        barView = new BarView(context, attrs);

        xAxisView = new XAxisView(context, attrs);

        yAxisView = new YAxisView(context, attrs);
        popView = new PopView(context);
        popView.setVisibility(View.INVISIBLE);

        unitTextView = new UnitTextView(context, attrs);


        titleView = new TitleView(context, attrs);


        this.addView(xAxisView);
        this.addView(yAxisView);
        this.addView(barView);
        this.addView(popView);
        this.addView(unitTextView);
        this.addView(titleView);


        barView.setOnBarClickListener(new OnBarClickListener() {
            @Override
            public void onBarClick(String text, Rect rect) {
                if (text == null || rect == null) {
                    popView.setVisibility(View.INVISIBLE);
                    barView.clearSelect();
                } else {


                    showPopView(text, rect);

                    if (barClickListener != null)
                        barClickListener.onBarClick(text, rect);

                }
            }
        });

    }

    void showPopView(String text, Rect rect) {

        popView.setText(text);


        //通过换算，获取rect相对于barChart的相对位置；
        int l = rect.left + barView.getLeft();
        //int t = rect.top + barView.getTop();

        //通过计算，获取popView相对于barChart的位置,使得popview的中间线对齐bar的中间线
        popViewL = l + barView.getBarWidth() / 2 - popView.getContentWidth() / 2;
        // popViewT = t - popView.getMeasuredHeight();
        popViewR = popViewL + popView.getContentWidth();
        // popViewB = popViewT + popView.getMeasuredHeight();


        // 处理popView左右超出柱状图范围

        int popViewOffset = 0;
        if (popViewL < barView.getLeft()) {
            popViewOffset = barView.getLeft() - popViewL;
            popViewL = barView.getLeft();
            popViewR = popViewL + popView.getContentWidth();

        }

        if (popViewR > barView.getRight()) {
            popViewOffset = barView.getRight() - popViewR;

            popViewR = barView.getRight();
            popViewL = popViewR - popView.getContentWidth();
        }

        popView.setOffset(popViewOffset);


        if (popView.getVisibility() == View.VISIBLE) {
            BarChart.this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    float tx = ViewHelper.getTranslationX(popView);
                    ValueAnimator anim = ValueAnimator.ofFloat(tx, popViewL * 1.0f);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float val = (Float) valueAnimator.getAnimatedValue();

                            ViewHelper.setTranslationX(popView, val);

                        }
                    });


                    anim.setInterpolator(new DecelerateInterpolator());
                    anim.setDuration(300);
                    anim.start();
                }
            }, 100);
        } else {

            ViewHelper.setTranslationX(popView, popViewL);
            popView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        popView.setVisibility(View.INVISIBLE);
        barView.clearSelect();

        return false;
    }

    int popViewL = -1;
    int popViewT = -1;
    int popViewR = -1;
    int popViewB = -1;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int yAxisViewWidth = yAxisView.getContentWidth();
        int xAxisViewHeight = xAxisView.getContentHeight();


        measureChild(popView, widthMeasureSpec, heightMeasureSpec);

        measureChild(titleView, widthMeasureSpec, heightMeasureSpec);


        barView.measure(width - yAxisViewWidth, height - xAxisViewHeight - yAxisView.getTextHalfHeight()
                - popView.getMeasuredHeight() - titleView.getMeasuredHeight());


        xAxisView.measure(width - yAxisViewWidth, xAxisViewHeight);

        yAxisView.measure(width, height - xAxisViewHeight + yAxisView.getTextHalfHeight() - popView.getMeasuredHeight() - titleView.getMeasuredHeight());


        measureChild(barView, widthMeasureSpec, heightMeasureSpec);
        measureChild(xAxisView, widthMeasureSpec, heightMeasureSpec);

        measureChild(yAxisView, widthMeasureSpec, heightMeasureSpec);

        measureChild(unitTextView, widthMeasureSpec, heightMeasureSpec);


        setMeasuredDimension(width, height);
    }


    private boolean barViewAnimationShow = false;


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        popView.layout(0, 0, popView.getMeasuredWidth(), popView.getMeasuredHeight());


        final int yAxisViewContentWidth = yAxisView.getContentWidth();
        int xAxisViewHeight = xAxisView.getContentHeight();


        final int barViewL = yAxisViewContentWidth;
        final int barViewT = popView.getMeasuredHeight();
        final int barViewR = getWidth();
        final int barViewB = getHeight() - xAxisViewHeight - titleView.getMeasuredHeight();


        if (barViewAnimationShow) {
            barViewAnimationShow = false;
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator anim = ValueAnimator.ofInt(barViewB, barViewT);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            int val = (Integer) valueAnimator.getAnimatedValue();


                            barView.layout(barViewL, val, barViewR, barViewB);

                        }
                    });


                    anim.setInterpolator(new DecelerateInterpolator());
                    anim.setDuration(500);
                    anim.start();
                }
            }, 100);

        } else {
            barView.layout(barViewL, barViewT, barViewR
                    , barViewB);
        }


        xAxisView.layout(yAxisViewContentWidth, barViewB, getWidth(), barViewB + xAxisViewHeight);

        yAxisView.layout(0, 0 + popView.getMeasuredHeight() - yAxisView.getTextHalfHeight(), getWidth(), getHeight() - xAxisViewHeight +
                yAxisView.getTextHalfHeight() - titleView.getMeasuredHeight());


        unitTextView.layout(0, xAxisView.getBottom() - unitTextView.getMeasuredHeight(), unitTextView.getMeasuredWidth(),
                xAxisView.getBottom());


        titleView.layout(getWidth() / 2 - titleView.getMeasuredWidth() / 2,

                xAxisView.getBottom(),

                getWidth() / 2 + titleView.getMeasuredWidth() / 2,
                xAxisView.getBottom() + titleView.getMeasuredHeight());
    }


}
