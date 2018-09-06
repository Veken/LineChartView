package com.veken.linechartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.veken.linecharviewmodule.DrawType;
import com.veken.linecharviewmodule.bean.ChartBean;
import com.veken.linecharviewmodule.view.BarCharView;
import com.veken.linecharviewmodule.view.LineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LineChartView chartView;
    private List<ChartBean> lineChartBeanList;
    private List<ChartBean> barChartBeanList;
    private BarCharView barCharView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = findViewById(R.id.chart_view);
        barCharView = findViewById(R.id.barchar_view);
        setLineChartData();
        setBarChartData();
    }

    /**
     * 柱状图数据
     */
    private void setBarChartData() {
        if(barChartBeanList == null){
            barChartBeanList = new ArrayList<>();
        }
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            ChartBean bean = new ChartBean();
            bean.setDate(i+"");
            bean.setValue(i*10+"");
            barChartBeanList.add(bean);
        }
        barCharView.setNeedBg(false);
        barCharView.setData(barChartBeanList);
        barCharView.setyLableText("柱状图");
        barCharView.setNeedDrawConnectYDataLine(true);
        barCharView.setDrawType(DrawType.DrawDottedLine);
    }

    /**
     * 折线图数据
     */
    private void setLineChartData() {
        if(lineChartBeanList==null){
            lineChartBeanList = new ArrayList<>();
        }
        chartView.setDefaultTextSize(24);
        Random random = new Random();
        for(int i=0;i<7;i++){
            ChartBean lineChartBean = new ChartBean();
            lineChartBean.setValue(String.valueOf(random.nextInt(10000)));
            lineChartBean.setDate(String.valueOf(i));
            lineChartBeanList.add(lineChartBean);
        }
        chartView.setData(lineChartBeanList);
        chartView.setyLableText("分钟");
        chartView.setDrawType(DrawType.DrawBackground);
        chartView.setClickBgColor(R.color.colorAccent);
        chartView.setClickable(true);
        chartView.setNeedBg(true);
        barCharView.setNeedDrawConnectYDataLine(true);
        barCharView.setDrawType(DrawType.DrawFullLine);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        chartView.recycleBitmap();
    }
}
