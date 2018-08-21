package com.veken.linechartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LineChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = findViewById(R.id.chart_view_1);
        setData();
    }

    private void setData() {
        String[] xLabel1 = {"01", "02", "03", "04", "05", "06", "07"};
        String[] data1 = {"1000", "10000", "90", "1000","0","0", "90"};
        chartView.setxLabel(xLabel1);
        chartView.setData(data1);
        chartView.setyLableText("分钟");
        chartView.setShowPicResource(R.mipmap.click_icon);
        chartView.setxLableTextColor(R.color.ylable_textColor);
    }

}
