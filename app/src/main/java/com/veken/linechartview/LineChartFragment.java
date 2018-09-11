package com.veken.linechartview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.veken.chartview.bean.ChartBean;
import com.veken.chartview.drawtype.DrawBgType;
import com.veken.chartview.drawtype.DrawConnectLineType;
import com.veken.chartview.drawtype.DrawLineType;
import com.veken.chartview.view.LineChartView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Veken on 2018/9/10 16:12
 *
 * @desc
 */

public class LineChartFragment extends Fragment {

    private LineChartView lineChartView;
    private ArrayList<ChartBean> lineChartBeanList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_linechart_view, container,false);
        lineChartView = view.findViewById(R.id.line_chart_view);
        initData();
        return view;
    }


    private void initData() {
        if(lineChartBeanList ==null){
            lineChartBeanList = new ArrayList<>();
        }
        lineChartView.setDefaultTextSize(24);
        Random random = new Random();
        for(int i=0;i<7;i++){
            ChartBean lineChartBean = new ChartBean();
            lineChartBean.setValue(String.valueOf(random.nextInt(10000)));
            lineChartBean.setDate(String.valueOf(i));
            lineChartBeanList.add(lineChartBean);
        }
        lineChartView.setData(lineChartBeanList);
        lineChartView.setyLableText("折线图");
        lineChartView.setDrawBgType(DrawBgType.DrawBitmap);
        lineChartView.setShowPicResource(R.mipmap.click_icon);
        lineChartView.setDrawConnectLineType(DrawConnectLineType.DrawDottedLine);
        lineChartView.setClickable(true);
        lineChartView.setNeedDrawConnectYDataLine(true);
        lineChartView.setConnectLineColor(getResources().getColor(R.color.default_color));
        lineChartView.setNeedBg(true);
        lineChartView.setDrawLineType(DrawLineType.Draw_Curve);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lineChartView.recycleBitmap();
    }
}
