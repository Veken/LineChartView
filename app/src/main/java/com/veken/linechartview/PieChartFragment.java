package com.veken.linechartview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veken.chartview.bean.PieChartBean;
import com.veken.chartview.view.PieChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Veken on 2018/9/11 17:03
 *
 * @desc
 */

public class PieChartFragment extends Fragment {

    private PieChartView pieChartView;
    private List<PieChartBean> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_piechart_view, container, false);
        pieChartView = view.findViewById(R.id.piechart_view);
        init();
        return view;
    }


    private void init() {
        if(mList==null){
            mList = new ArrayList<>();
        }
        int[] colors = new int[]{getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.default_color),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.endColor)};
        Random random = new Random();
        for(int i = 0;i<4;i++){
            PieChartBean pieChartBean = new PieChartBean();
            pieChartBean.setValue(random.nextInt(10)+i);
            pieChartBean.setColor(colors[i]);
            mList.add(pieChartBean);
        }

        pieChartView.setData(mList);
        pieChartView.setInsideText("Hello");
        pieChartView.setIsNeedAnimation(true,5000);
        pieChartView.setNeedInside(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mList!=null&&mList.size()>0){
            mList.clear();
        }
    }
}
