package com.example.hyhe.barchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.hyhe.barchart.BarChart.BarChart;
import com.example.hyhe.barchart.BarChart.BarDataList;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barChart = (BarChart) findViewById(R.id.chart_view);

        List<BarDataList> barDataList = JSON.parseArray("[{\"color\":-2302756,\"dataItemList\":[{\"data\":118357,\"dataShowText\":\"118357元/m²\",\"popViewIsDefaultShow\":false},{\"data\":132000,\"dataShowText\":\"132000元/m²\",\"popViewIsDefaultShow\":false},{\"data\":132166,\"dataShowText\":\"132166元/m²\",\"popViewIsDefaultShow\":false},{\"data\":132166,\"dataShowText\":\"132166元/m²\",\"popViewIsDefaultShow\":false},{\"data\":141666,\"dataShowText\":\"141666元/m²\",\"popViewIsDefaultShow\":false},{\"data\":141666,\"dataShowText\":\"141666元/m²\",\"popViewIsDefaultShow\":false}],\"selectedColor\":-5197648,\"text\":\"陆家嘴滨江房价（住宅/万）\"},{\"color\":-9853936,\"dataItemList\":[{\"data\":70000,\"dataShowText\":\"70000元/m²\",\"popViewIsDefaultShow\":false},{\"data\":77000,\"dataShowText\":\"77000元/m²\",\"popViewIsDefaultShow\":false},{\"data\":78000,\"dataShowText\":\"78000元/m²\",\"popViewIsDefaultShow\":false},{\"data\":78000,\"dataShowText\":\"78000元/m²\",\"popViewIsDefaultShow\":false},{\"data\":80000,\"dataShowText\":\"80000元/m²\",\"popViewIsDefaultShow\":false},{\"data\":80000,\"dataShowText\":\"80000元/m²\",\"popViewIsDefaultShow\":true}],\"selectedColor\":-11697395,\"text\":\"本楼盘房价（住宅/万）\"}]", BarDataList.class);

        List<String> xAxis = JSON.parseArray("[\"02\",\"03\",\"04\",\"05\",\"06\",\"07\"]", String.class);
        List<Integer> yAxis = JSON.parseArray("[63000,89222,115444,141666]", Integer.class);

        barChart.setChartDatas(barDataList, xAxis, yAxis, "元/平米");
    }
}
