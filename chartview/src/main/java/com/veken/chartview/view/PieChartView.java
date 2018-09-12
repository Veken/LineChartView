package com.veken.chartview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.veken.chartview.DensityUtils;
import com.veken.chartview.bean.PieChartBean;
import com.veken.linecharviewmodule.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veken on 2018/9/11 17:04
 *
 * @desc
 */

public class PieChartView extends View {
    //扇形画笔
    private Paint arcPaint;
    //文字画笔
    private Paint textPaint;

    //文字颜色
    private int textColor;

    //圆半径
    private int raduis;
    //角度数组
    private float[] angle;

    private RectF rectF;

    private Context mContext;

    private float pointCircleX;
    private float pointCircleY;

    public int getRaduis() {
        return raduis;
    }

    public void setRaduis(int raduis) {
        this.raduis = raduis;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    //数据
    private List<PieChartBean> mList = new ArrayList<>();

    public PieChartView(Context context) {
        this(context,null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PieChartView);
        raduis = typedArray.getDimensionPixelSize(R.styleable.PieChartView_radius, DensityUtils.dip2px(mContext,100));
        textColor = typedArray.getColor(R.styleable.PieChartView_textColor,0XFFFFFFFF);
        init();
    }

    private void init() {
        pointCircleX = raduis/2;
        pointCircleY = raduis/2;
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(DensityUtils.dip2px(mContext,14));
        rectF = new RectF(0,0,raduis*2,raduis*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
    }

    /**
     * 画扇形
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        float startAngle = 0;
        for(int i=0;i<mList.size();i++){
            float sweepAngle = angle[i];
            arcPaint.setColor(mList.get(i).getColor());
            canvas.drawArc(rectF,startAngle,sweepAngle,true,arcPaint);
            startAngle += sweepAngle;
            drawText(canvas,startAngle,sweepAngle,DensityUtils.floatFormat(angle[i])+"%");
        }
    }

    private void drawText(Canvas canvas,float startAngle,float sweepAngle,String value) {
        float textWidth = textPaint.measureText(value);
        if(sweepAngle<=90){
            //第四象限
            canvas.drawText(value,pointCircleX+raduis*(float)Math.sin(sweepAngle/2)-textWidth/2,pointCircleY+raduis*(float)Math.cos(sweepAngle/2),textPaint);
        }
    }


    public void setData(List<PieChartBean> list){
        this.mList = list;
        angle = new float[mList.size()];
        float sum = 0 ;
        for(int i=0;i<mList.size();i++){
            sum += mList.get(i).getValue();
        }
        for(int i=0;i<mList.size();i++){
            angle[i] = mList.get(i).getValue()/sum * 360;
        }
    }
}
