package com.veken.chartview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.veken.chartview.bean.ChartBean;
import com.veken.chartview.DensityUtils;
import com.veken.chartview.drawtype.DrawConnectLineType;
import com.veken.linecharviewmodule.R;

import java.util.ArrayList;
import java.util.List;

public class BarChartView extends View {

    private float xLength;
    private float yLength;
    private float startPointX = 0.0f;
    private float startPointY = 0.0f;
    private float xMarginWidth;
    private float startX = 0.0f;
    private float startY = 0.0f;
    //数据
    private List<ChartBean> mList = new ArrayList<>();


    //y轴lable文字
    private String yLableText = "柱状图";

    //画笔
    //坐标轴的画笔
    private Paint mScaleLinePaint;
    //数据连线的画笔
    private Paint mDataLinePaint;
    //Y轴lable画笔
    private Paint yTextLablePaint;
    //X轴lable画笔
    private Paint xTextLablePaint;
    //Y轴上的数据画笔
    private Paint yDataPaint;
    //图片画笔
    private Paint bgPaint;

    //文字和X轴Y轴高度间隔
    private int axisMarginHeight;
    //Y轴数据的文字宽度
    private float yDataWidth;

    private int defaultStrokeWidth;
    //默认文字大小
    private int defaultTextSize;
    //Y轴lable颜色
    private int yLableTextColor;
    //默认数据颜色
    private int defaultColor;
    //X轴Lable颜色
    private int xLableTextColor;
    //坐标轴的颜色
    private int axisColor;
    //Y轴柱状背景颜色
    private int bgColor;

    //是否需要背景
    private boolean isNeedBg;


    //是否需要画Y轴刻度
    private boolean isNeedDrawYScale;

    //画实线还是虚线
    private DrawConnectLineType drawConnectLineType;
    //是否需要Y轴数据对应的线
    private boolean isNeedDrawConnectYDataLine;

    private Context mContext;
    private float firstDataWidth;
    private float yLableWidth;
    private float viewWidth;
    private float viewHeight;
    private float yDataHeight;
    private float xLableHeight;
    private int axisXItemWidth;
    //虚线的每一小格宽度
    private int dottedLineWidth;
    //连接线的颜色
    private int connectLineColor;

    private RectF rect;

    public int getConnectLineColor() {
        return connectLineColor;
    }

    public void setConnectLineColor(int connectLineColor) {
        this.connectLineColor = connectLineColor;
    }

    public DrawConnectLineType getDrawConnectLineType() {
        return drawConnectLineType;
    }

    public void setDrawConnectLineType(DrawConnectLineType drawConnectLineType) {
        this.drawConnectLineType = drawConnectLineType;
    }

    public int getDottedLineWidth() {
        return dottedLineWidth;
    }

    public void setDottedLineWidth(int dottedLineWidth) {
        this.dottedLineWidth = dottedLineWidth;
    }

    public int getAxisColor() {
        return axisColor;
    }

    public void setAxisColor(int axisColor) {
        this.axisColor = axisColor;
    }

    public boolean isNeedDrawConnectYDataLine() {
        return isNeedDrawConnectYDataLine;
    }

    public void setNeedDrawConnectYDataLine(boolean needDrawConnectYDataLine) {
        isNeedDrawConnectYDataLine = needDrawConnectYDataLine;
    }

    public boolean isNeedDrawYScale() {
        return isNeedDrawYScale;
    }

    public void setNeedDrawYScale(boolean needDrawYScale) {
        isNeedDrawYScale = needDrawYScale;
    }

    public int getDefaultTextSize() {
        return defaultTextSize;
    }

    public void setDefaultTextSize(int defaultTextSize) {
        this.defaultTextSize = defaultTextSize;
    }

    public String getyLableText() {
        return yLableText;
    }

    public void setyLableText(String yLableText) {
        this.yLableText = yLableText;
    }


    public  int getyLableTextColor() {
        return yLableTextColor;
    }

    public  void setyLableTextColor(int yLableTextColor) {
        this.yLableTextColor = yLableTextColor;
    }

    public int getxLableTextColor() {
        return xLableTextColor;
    }

    public void setxLableTextColor(int xLableTextColor) {
        this.xLableTextColor = xLableTextColor;
    }

    public boolean isNeedBg() {
        return isNeedBg;
    }

    public void setNeedBg(boolean needBg) {
        isNeedBg = needBg;
    }

    public int getDefaultStrokeWidth() {
        return defaultStrokeWidth;
    }

    public void setDefaultStrokeWidth(int defaultStrokeWidth) {
        this.defaultStrokeWidth = defaultStrokeWidth;
    }

    public BarChartView(Context context) {
        this(context,null);
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedValue = context.obtainStyledAttributes(attrs,R.styleable.BarChartView);
        axisMarginHeight =  typedValue.getDimensionPixelSize(R.styleable.BarChartView_axisMarginHeight,DensityUtils.dip2px(mContext,10));
        defaultTextSize = typedValue.getDimensionPixelSize(R.styleable.BarChartView_defaultTextSize,DensityUtils.sp2px(mContext,14));
        yLableTextColor = typedValue.getColor(R.styleable.BarChartView_yLableTextColor,0XFF99989d);
        defaultColor = typedValue.getColor(R.styleable.BarChartView_yLableTextColor,0XFF5287F7);
        xLableTextColor = typedValue.getColor(R.styleable.BarChartView_xLableTextColor,0XFF99989d);
        axisColor = typedValue.getColor(R.styleable.BarChartView_axisColor,0XFF99989d);
        isNeedBg = typedValue.getBoolean(R.styleable.BarChartView_isNeedBg,true);
        yLableText = typedValue.getString(R.styleable.BarChartView_yLableText);
        axisXItemWidth = typedValue.getDimensionPixelSize(R.styleable.BarChartView_axisXItemWidth, DensityUtils.dip2px(mContext,20));
        defaultStrokeWidth = typedValue.getDimensionPixelSize(R.styleable.LineChartView_defaultStrokeWidth,DensityUtils.dip2px(mContext,1));
        bgColor = typedValue.getColor(R.styleable.BarChartView_bgColor,0XFF5287F7);
        isNeedDrawConnectYDataLine = typedValue.getBoolean(R.styleable.BarChartView_isNeedDrawConnectYDataLine,false);
        connectLineColor = typedValue.getColor(R.styleable.BarChartView_connectLineColor,0XFF5287F7);
        dottedLineWidth = typedValue.getDimensionPixelSize(R.styleable.BarChartView_dottedLineWidth,DensityUtils.dip2px(mContext,3));
        isNeedDrawYScale = typedValue.getBoolean(R.styleable.BarChartView_isNeedDrawYScale,false);
        init();
    }

    private void init() {
        // 新建画笔
        mDataLinePaint = new Paint();       // 数据(点和连线)画笔
        mScaleLinePaint = new Paint();      // 坐标(刻度线条)值画笔

        mDataLinePaint.setAntiAlias(true);
        mDataLinePaint.setStrokeWidth(defaultStrokeWidth);
        mDataLinePaint.setColor(defaultColor);
        //坐标轴的画笔
        mScaleLinePaint.setAntiAlias(true);
        mScaleLinePaint.setStrokeWidth(defaultStrokeWidth);
        mScaleLinePaint.setColor(axisColor);

        //Y轴lable画笔
        yTextLablePaint = new Paint();
        yTextLablePaint.setAntiAlias(true);
        yTextLablePaint.setTextSize(defaultTextSize);
        yTextLablePaint.setColor(yLableTextColor);

        //X轴lable画笔
        xTextLablePaint = new Paint();
        xTextLablePaint.setAntiAlias(true);
        xTextLablePaint.setTextSize(defaultTextSize);
        xTextLablePaint.setColor(xLableTextColor);

        //Y轴上的数据画笔
        yDataPaint = new Paint();
        yDataPaint.setAntiAlias(true);
        yDataPaint.setTextSize(defaultTextSize);
        yDataPaint.setColor(defaultColor);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);

        rect = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initMeasure();
    }


    /**
     * 获取宽高
     */
    private void initMeasure() {
        //控件的宽度
        viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        viewHeight = DensityUtils.px2dip(mContext,getMeasuredHeight() - getPaddingTop()-getPaddingBottom());
        //Y轴lable文字宽度
        yLableWidth = yTextLablePaint.measureText(yLableText);
        //X轴lable文字高度
        xLableHeight = DensityUtils.getFontHeight(xTextLablePaint,mList.get(0).getDate()) + DensityUtils.dip2px(mContext,axisMarginHeight);
        //y轴的长度
        yLength = DensityUtils.dip2px(mContext,viewHeight-axisMarginHeight- xLableHeight);
        startX = getPaddingLeft();
        //起点坐标空出点画Y轴label
        startY = getPaddingTop();
        //X轴起始坐标(第一个值跟YLable文字的长度比较，取最长的,不然如果第一个值很大，会导致显示不全,还要加上文字间隔的宽度，用来显示点击之后的图片)
        xMarginWidth = yLableWidth >firstDataWidth? yLableWidth /2: firstDataWidth/2;
        //每一个X轴点之间的长度
        xLength = (viewWidth-xMarginWidth) / mList.size();
        float maxWidth = yDataPaint.measureText(String.valueOf(mList.get(0).getValue()));
        float currentWidth = 0;
        for (int i = 0; i <mList.size() ; i++) {
            currentWidth = yDataPaint.measureText(String.valueOf(mList.get(i).getValue()));
            if(maxWidth<currentWidth){
                maxWidth = currentWidth;
            }
        }
        firstDataWidth = maxWidth+ DensityUtils.dip2px(mContext,axisMarginHeight);
        startPointX = startX + firstDataWidth;
        //Y轴起始坐标
        startPointY = startY +DensityUtils.getFontHeight(yTextLablePaint,yLableText)+DensityUtils.dip2px(mContext,axisMarginHeight) +yLength;

        //点上文字的高度
        yDataHeight = DensityUtils.getFontHeight(yTextLablePaint,mList.get(0).getValue());

        //获取数据点的坐标
        getPointRoords();

    }

    /**
     * 获取数据值的坐标点
     *
     * @return 数据点的坐标
     */
    private void getPointRoords() {
        float averHeight = 0;
        float max = Float.parseFloat(mList.get(0).getValue());
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setxAxis(startPointX + i * xLength);
            //计算数组中的最大值
            if(max < Float.parseFloat(mList.get(i).getValue())){
                max = Float.parseFloat(mList.get(i).getValue());
            }
        }
        for(int i = 0;i<mList.size();i++){
            averHeight = (yLength - DensityUtils.dip2px(mContext,axisMarginHeight)-yDataHeight)/max;
            mList.get(i).setyAxis(startPointY - averHeight * Float.parseFloat(mList.get(i).getValue()));
        }
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<ChartBean> list) {
        this.mList = list;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画Y轴标签
        drawYLable(canvas);
        //画X轴标签
        drawXLable(canvas);
        //画X轴
        drawXLine(canvas);
        //画Y轴
        drawYLine(canvas);
        //画Y轴柱状图
        drawYBarChart(canvas);
        //画Y轴上的数值
        drawYData(canvas);
        //画Y轴数据连接的线
        if(isNeedDrawConnectYDataLine){
            drawConnectYDataLine(canvas);
        }
        //是否需要画Y轴的刻度
        if(isNeedDrawYScale){
            drawYScale(canvas);
        }
    }

    private void drawYScale(Canvas canvas) {
        //所有文字的高度都是一致的
        float textHeight = DensityUtils.getFontHeight(yTextLablePaint,mList.get(0).getValue());//文字高
        for (int i = 0; i < mList.size(); i++) {
            float yValueWidth = yTextLablePaint.measureText(mList.get(i).getValue());
            canvas.drawText(mList.get(i).getValue(),startPointX-yValueWidth-DensityUtils.dip2px(mContext,5),mList.get(i).getyAxis()+textHeight/2,yTextLablePaint);
        }
    }

    /**
     * 画Y轴数据连接的线
     * @param canvas
     */
    private void drawConnectYDataLine(Canvas canvas) {
        mScaleLinePaint.setColor(connectLineColor);
        for (int i = 1; i <mList.size() ; i++) {
            if(drawConnectLineType == DrawConnectLineType.DrawFullLine){
                //实线
                canvas.drawLine(startPointX,mList.get(i).getyAxis(),mList.get(i).getxAxis(),mList.get(i).getyAxis(),mScaleLinePaint);
            }else if(drawConnectLineType == DrawConnectLineType.DrawDottedLine){
                //虚线
                //虚线
                float x = mList.get(i).getxAxis()-axisXItemWidth - startPointX;
                float spaceWidth = DensityUtils.dip2px(mContext,2);
                int count = (int) (x /(spaceWidth+dottedLineWidth));
                for(int j = 0;j<count;j++){
                    canvas.drawLine(startPointX+(spaceWidth+dottedLineWidth)*j,mList.get(i).getyAxis(),startPointX+(spaceWidth+dottedLineWidth)*j+dottedLineWidth,mList.get(i).getyAxis(),mScaleLinePaint);
                }
                canvas.drawLine(startPointX+(spaceWidth+dottedLineWidth)*count,mList.get(i).getyAxis(),mList.get(i).getxAxis()-axisXItemWidth,mList.get(i).getyAxis(),mScaleLinePaint);
            }

        }
    }

    /**
     * 画柱状图
     * @param canvas
     */
    private void drawYBarChart(Canvas canvas) {
        //第一个不画，所以从1开始
        if(isNeedBg){
            bgPaint.setColor(bgColor);
        }else{
            bgPaint.setColor(axisColor);
            bgPaint.setStrokeWidth(defaultStrokeWidth);
            bgPaint.setStyle(Paint.Style.STROKE);
        }
        for(int i = 1;i<mList.size();i++){
           rect.left =  mList.get(i).getxAxis()-axisXItemWidth;
           rect.top = mList.get(i).getyAxis();
           rect.right = mList.get(i).getxAxis()+axisXItemWidth;
           rect.bottom = startPointY;
           canvas.drawRect(rect,bgPaint);
        }

    }

    /**
     * 画Y轴上的数据
     * @param canvas
     */
    private void drawYData(Canvas canvas) {
        //第一个不画
        for(int i = 1;i<mList.size();i++){
            yDataWidth = yDataPaint.measureText(String.valueOf(mList.get(i).getValue()));
            yDataPaint.setColor(defaultColor);
            canvas.drawText(String.valueOf(mList.get(i).getValue()),mList.get(i).getxAxis()- yDataWidth /2,mList.get(i).getyAxis()-DensityUtils.dip2px(mContext,axisMarginHeight),yDataPaint);

        }
    }


    /**
     * 画Y轴label
     */
    private void drawYLable(Canvas canvas){
        float yLableTextWidth = yTextLablePaint.measureText(yLableText);
        canvas.drawText(yLableText,startPointX-yLableTextWidth/2,startPointY-yLength-DensityUtils.dip2px(mContext,axisMarginHeight),yTextLablePaint);
    }


    /**
     *画X轴lable
     */
    private void drawXLable(Canvas canvas){
        for (int i = 0;i<mList.size();i++){
            float xLableWidth = xTextLablePaint.measureText(mList.get(i).getDate());
            //默认颜色
            xTextLablePaint.setColor(xLableTextColor);
            canvas.drawText(mList.get(i).getDate(),startPointX-xLableWidth/2+i*xLength,startPointY+DensityUtils.getFontHeight(xTextLablePaint,mList.get(i).getDate())+DensityUtils.dip2px(mContext,axisMarginHeight)
                    ,xTextLablePaint);
        }
    }

    /**
     *画Y轴
     * @param canvas
     */
    private void drawYLine(Canvas canvas) {
        canvas.drawLine(startPointX,startPointY,startPointX,startPointY-yLength,mScaleLinePaint);
    }

    /**
     * 画X轴
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        canvas.drawLine(startPointX, startPointY , startPointX + viewWidth-xMarginWidth, startPointY, mScaleLinePaint);
    }

}
