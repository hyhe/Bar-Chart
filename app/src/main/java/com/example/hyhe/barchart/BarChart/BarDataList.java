package com.example.hyhe.barchart.BarChart;

import java.util.List;

/**
 * desc: 图表数据
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-14
 */
public class BarDataList extends Title {
    private List<BarDataItem> dataItemList;

    public BarDataList() {
        super();
    }

    public BarDataList(String text, int color, List<BarDataItem> dataItemList) {
        super(text, color);
        this.dataItemList = dataItemList;
    }

    public List<BarDataItem> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(List<BarDataItem> dataItemList) {
        this.dataItemList = dataItemList;
    }
}
