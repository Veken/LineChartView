package com.veken.linechartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.veken.linecharviewmodule.DrawType;
import com.veken.linecharviewmodule.LineChartBean;
import com.veken.linecharviewmodule.LineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LineChartView chartView;
    private List<LineChartBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = findViewById(R.id.chart_view);
        setData();
    }

    private void setData() {
        if(mList==null){
            mList = new ArrayList<>();
        }
        chartView.setDefaultTextSize(24);
        Random random = new Random();
        for(int i=0;i<7;i++){
            LineChartBean lineChartBean = new LineChartBean();
            lineChartBean.setValue(String.valueOf(random.nextInt(10000)));
            lineChartBean.setDate(String.valueOf(i));
            mList.add(lineChartBean);
        }
        chartView.setData(mList);
        chartView.setyLableText("分钟");
        chartView.setDrawType(DrawType.DrawBackground);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chartView.recycleBitmap();
    }
}
