package com.example.hyhe.barchart.BarChart;

/**
 * desc: 图表数据
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-14
 */
public class BarDataItem {
    private int data;
    private String dataShowText;
    boolean PopViewIsDefaultShow;//是否初始显示

    public BarDataItem(Integer data, String dataShowText) {
        this.data = data;
        this.dataShowText = dataShowText;
    }

    public BarDataItem() {

    }

    public boolean isPopViewIsDefaultShow() {
        return PopViewIsDefaultShow;
    }

    public void setPopViewIsDefaultShow(boolean popViewIsDefaultShow) {
        PopViewIsDefaultShow = popViewIsDefaultShow;
    }

    public float getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public String getDataShowText() {
        return dataShowText;
    }

    public void setDataShowText(String dataShowText) {
        this.dataShowText = dataShowText;
    }
}
