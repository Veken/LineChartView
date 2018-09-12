package com.veken.chartview.bean;

/**
 * Created by Veken on 2018/9/11 17:17
 *
 * @desc
 */

public class PieChartBean {
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    private float value;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;
}
