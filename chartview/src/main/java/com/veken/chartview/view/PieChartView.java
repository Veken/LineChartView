package com.veken.chartview.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

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
    //内部圆画笔
    private Paint insidePaint;

    //文字颜色
    private int textColor;

    //是否将文字画在外面
    private boolean isTextOutCircle = true;

    //数据文本的大小
    private Rect dataTextBound = new Rect();
    //圆半径
    private int radius;
    //内部圆的半径是外圆的百分比
    private float insideRadiusPercent;
    //是否需要内部圆
    private boolean isNeedInside = false;
    //内部的文字
    private String insideText = "";
    //内部圆的背景颜色
    private int insideBgColor;
    //内部圆的文字颜色
    private int insideTextColor;
    //内部文字大小
    private int insideTextSize;
    //角度数组
    private float[] angle;
    //外部百分比文字描述矩形
    private RectF rectF;
    //内部文字矩形
    private Rect insideRect;

    private Context mContext;
    //圆心X
    private float pointCircleX;
    //圆心Y
    private float pointCircleY;
    //文字在外面的间隔
    private float textOutCircleMargin;
    //属性动画的进度
    private float progress = 360.0f;
    //最大值的百分比
    private float maxText;

    public float getInsideRadiusPercent() {
        return insideRadiusPercent;
    }

    public void setInsideRadiusPercent(float insideRadiusPercent) {
        this.insideRadiusPercent = insideRadiusPercent;
    }


    public int getInsideTextColor() {
        return insideTextColor;
    }

    public void setInsideTextColor(int insideTextColor) {
        this.insideTextColor = insideTextColor;
    }

    public int getInsideTextSize() {
        return insideTextSize;
    }

    public void setInsideTextSize(int insideTextSize) {
        this.insideTextSize = insideTextSize;
    }

    public boolean isTextOutCircle() {
        return isTextOutCircle;
    }

    public void setTextOutCircle(boolean textOutCircle) {
        isTextOutCircle = textOutCircle;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isNeedInside() {
        return isNeedInside;
    }

    public void setNeedInside(boolean needInside) {
        isNeedInside = needInside;
    }

    public String getInsideText() {
        return insideText;
    }

    public void setInsideText(String insideText) {
        this.insideText = insideText;
    }

    public int getInsideBgColor() {
        return insideBgColor;
    }

    public void setInsideBgColor(int insideBgColor) {
        this.insideBgColor = insideBgColor;
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
        textColor = typedArray.getColor(R.styleable.PieChartView_textColor,0XFFFFFFFF);
        textOutCircleMargin = typedArray.getDimensionPixelSize(R.styleable.PieChartView_textOutCircleMargin,5);
        //内部圆半径默认为原半径的一半
        insideRadiusPercent = typedArray.getFloat(R.styleable.PieChartView_insideRadiusPercent,0.5f);
        isNeedInside = typedArray.getBoolean(R.styleable.PieChartView_isNeedInside,true);
        insideText = typedArray.getString(R.styleable.PieChartView_insideText);
        insideBgColor = typedArray.getColor(R.styleable.PieChartView_insideBgColor,0XFFFFFFFF);
        insideTextSize = typedArray.getDimensionPixelSize(R.styleable.PieChartView_insideTextSize,20);
        insideTextColor = typedArray.getColor(R.styleable.PieChartView_insideTextColor,0X000000);
        init();
    }

    private void init() {
        radius = DensityUtils.dip2px(mContext,radius);
        textOutCircleMargin = DensityUtils.dip2px(mContext,textOutCircleMargin);
        insideTextSize = DensityUtils.dip2px(mContext,insideTextSize);
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(DensityUtils.dip2px(mContext,14));

        insidePaint = new Paint();
        insidePaint.setAntiAlias(true);
        insidePaint.setColor(insideBgColor);
        insidePaint.setTextSize(insideTextSize);

        insideRect = new Rect();
    }



    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width, height;
        if (wideMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            width = wideSize;
        } else {
            width = radius * 2 + getPaddingLeft() + getPaddingRight();
            if (wideMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, wideSize);
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            height = heightSize;
        } else {
            height = radius * 2 + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }

        }
        setMeasuredDimension(width, height);
        pointCircleX = width/2;
        pointCircleY = height/2;
        //获取名称文本大小
        textPaint.getTextBounds(String.valueOf(DensityUtils.floatFormat(maxText*100))+"%", 0, String.valueOf(DensityUtils.floatFormat(maxText*100)+"%").length(), dataTextBound);
        //文字间隔和文字宽高
        float textMargin = textOutCircleMargin+Math.max(dataTextBound.width(),dataTextBound.height());
        radius = (int) (width/2-textMargin);
        rectF = new RectF(pointCircleX-radius,pointCircleY-radius,pointCircleX+radius,pointCircleY+radius);
        insidePaint.getTextBounds(insideText,0,insideText.length(),insideRect);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        if(isNeedInside){
            insidePaint.setColor(insideBgColor);
            canvas.drawCircle(pointCircleX,pointCircleY,radius*insideRadiusPercent,insidePaint);
            insidePaint.setColor(insideTextColor);
            if(!TextUtils.isEmpty(insideText)){
                canvas.drawText(insideText,pointCircleX-insideRect.width()/2,pointCircleY+insideRect.height()/2,insidePaint);
            }
        }
    }

    /**
     * 画扇形
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        float startAngle = 0;
        for(int i=0;i<mList.size();i++){
            float sweepAngle;
            if(i!=mList.size()-1){
                //每一部分根据值所占比例，在圆中所占的角度
                sweepAngle = angle[i]*360;
            }else{
                sweepAngle = 360 - startAngle;
            }
            if(sweepAngle==0)continue;
            arcPaint.setColor(mList.get(i).getColor());
            if(progress-startAngle>=0){
                canvas.drawArc(rectF,startAngle,progress-startAngle,true,arcPaint);
                //每段扇形中心点所对应的角度
                float centerAngle =  startAngle+sweepAngle/2;
                //每段扇形最终的角度
                float endAngle = startAngle+sweepAngle;
                drawText(canvas, centerAngle,endAngle,DensityUtils.floatFormat(angle[i]*100)+"%");
            }
            startAngle += sweepAngle;
        }
    }

    /**
     * 画圆外的文字
     * @param canvas
     * @param centerAngle
     * @param endAngle
     * @param s
     */
    private void drawText(Canvas canvas,float centerAngle,float endAngle,String s) {
        //获取名称文本大小
        textPaint.getTextBounds(s, 0, s.length(), dataTextBound);
        float x = 0;
        float y = 0;
        if(centerAngle>=0&&centerAngle<90){
            //第四象限
            if(isTextOutCircle){
                x = pointCircleX + (float) Math.cos(Math.toRadians(centerAngle))*(radius+textOutCircleMargin);
                y = pointCircleY + (float) Math.sin(Math.toRadians(centerAngle))*(radius+textOutCircleMargin);
                if(endAngle>=90&&endAngle<135){
                    canvas.drawText(s,x,y+dataTextBound.height()/2,textPaint);

                }else{
                    canvas.drawText(s,x,y+dataTextBound.height(),textPaint);
                }
            }
        }else if(centerAngle>=90&&centerAngle<180){
            //第三象限
            if(isTextOutCircle){
                x = pointCircleX - (float) Math.cos(Math.toRadians(180-centerAngle))*(radius+textOutCircleMargin);
                y = pointCircleY + (float) Math.sin(Math.toRadians(180-centerAngle))*(radius+textOutCircleMargin);
                canvas.drawText(s,x-dataTextBound.width(),y+dataTextBound.height(),textPaint);
            }
        }else if(centerAngle>=180&&centerAngle<270){
            //第二象限
            if(isTextOutCircle){
                x = pointCircleX - (float) Math.cos(Math.toRadians(centerAngle-180))*(radius+textOutCircleMargin);
                y = pointCircleY - (float) Math.sin(Math.toRadians(centerAngle-180))*(radius+textOutCircleMargin);
                canvas.drawText(s,x-dataTextBound.width(),y,textPaint);
            }
        }else if(centerAngle>=270&&centerAngle<=360){
            //第一象限
            if(isTextOutCircle){
                x = pointCircleX + (float) Math.cos(Math.toRadians(360-centerAngle))*(radius+textOutCircleMargin);
                y = pointCircleY - (float) Math.sin(Math.toRadians(360-centerAngle))*(radius+textOutCircleMargin);
                //考虑到不同角度，文字宽度的原因，会造成文字没法在中间显示
                if(endAngle>=270&&endAngle<=315){
                    canvas.drawText(s,x-dataTextBound.width()/2,y,textPaint);
                }else{
                    canvas.drawText(s,x,y,textPaint);
                }
            }
        }
        //扇形中心点与圆心的连线
//        canvas.drawLine(pointCircleX,pointCircleY,x,y,textPaint);
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<PieChartBean> list){
        this.mList = list;
        angle = new float[mList.size()];
        float sum = 0 ;
        for(int i=0;i<mList.size();i++){
            sum += mList.get(i).getValue();
        }
        for(int i=0;i<mList.size();i++){
            angle[i] = Float.parseFloat(DensityUtils.float2Format(mList.get(i).getValue()/sum ));
        }
        if(angle!=null&&angle.length>0){
            for (int i = 0; i < angle.length-1; i++) {
                maxText = angle[i];
                if (maxText<angle[i+1]){
                    maxText = angle[i+1];
                }
            }
        }

    }

    /**
     * 加载动画
     * @param isNeedAnimation
     */
    public void setIsNeedAnimation(boolean isNeedAnimation,long duration) {
        if(isNeedAnimation){
            ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    progress = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.setDuration(duration);
            animator.start();
        }
    }

    /**
     * 加载动画
     * @param isNeedAnimation
     */
    public void setIsNeedAnimation(boolean isNeedAnimation) {
        if(!isNeedAnimation){
            progress = 360;
        }
    }

}
