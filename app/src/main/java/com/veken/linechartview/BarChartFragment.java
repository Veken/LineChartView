package com.veken.linechartview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.veken.chartview.bean.ChartBean;
import com.veken.chartview.drawtype.DrawConnectLineType;
import com.veken.chartview.view.BarChartView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Veken on 2018/9/10 16:17
 *
 * @desc
 */

public class BarChartFragment extends Fragment {

    private BarChartView barChartView;
    private ArrayList<ChartBean> barChartBeanList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_barchart_view,container,false);
        barChartView = view.findViewById(R.id.bar_chart_view);
        initData();
        return view;
    }

    private void initData() {
        if(barChartBeanList == null){
            barChartBeanList = new ArrayList<>();
        }
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            ChartBean bean = new ChartBean();
            bean.setDate(i+"");
            bean.setValue(100*random.nextInt(10)+"");
            barChartBeanList.add(bean);
        }
        barChartView.setNeedBg(true);
        barChartView.setData(barChartBeanList);
        barChartView.setyLableText("柱状图");
        barChartView.setNeedDrawConnectYDataLine(true);
        barChartView.setNeedDrawYScale(true);
        barChartView.setDrawConnectLineType(DrawConnectLineType.DrawDottedLine);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(barChartBeanList!=null&&barChartBeanList.size()>0){
            barChartBeanList.clear();
        }
    }
}
