package com.veken.linechartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veken on 2018/8/14 18:13
 *
 * @desc
 */

public class LineChartView extends View {

    private float xLength;
    private float yLength;
    private float startPointX = 0.0f;
    private float startPointY = 0.0f;
    private float xMarginWidth;
    private float startX = 0.0f;
    private float startY = 0.0f;
    //数据
    private List<LineChartBean> mList = new ArrayList<>();

    //点击后展示的图片
    private int showPicResource = R.mipmap.click_icon;

    //y轴lable文字
    private String yLableText;

    //画笔
    //坐标轴的画笔
    private Paint mScaleLinePaint;
    //数据连线的画笔
    private Paint mDataLinePaint;
    //透明画笔
    private Paint transparentPaint = new Paint();
    //数据圆点被选中的画笔
    private Paint pointSelectedPaint = new Paint();
    //数据圆点默认画笔
    private Paint pointPaint = new Paint();
    //Y轴lable画笔
    private Paint yTextLablePaint;
    //X轴lable画笔
    private Paint xTextLablePaint;
    //Y轴上的数据画笔
    private Paint yDataPaint;
    //图片画笔
    private Paint bitmapPaint;

    //文字和X轴Y轴高度间隔
    private int marginHeight;
    //点上的文字和点之间的间隔
    private int pointMarginHeight;

    //Y轴数据的文字宽度
    private float yDataWidth;
    //默认文字大小
    private int defaultTextSize = 14;
    //默认圆心大小
    private int pointDefaultRadius = 4;
    //点击之后里面圆的圆心
    private int pointClickRadius = 2;
    private int defaultStrokeWidth = 2;
    //Y轴lable颜色
    private int yLableTextColor = R.color.ylable_textColor;
    //默认数据颜色
    private int defaultColor = R.color.default_color;
    //X轴Lable颜色
    private int xLableTextColor = R.color.ylable_textColor;
    //坐标轴的颜色
    private int axisColor = R.color.axisColor;

    private boolean isClick;                // 是否点击了数据点
    private int clickIndex = -1;            // 被点击的数据点的索引值

    private Context mContext;
    private Bitmap bitmap;
    private Bitmap resizeBitmap;
    private float firstDataWidth;
    private float yLableWidth;
    private float viewWidth;
    private float viewHeight;

    public int getPointClickRadius() {
        return pointClickRadius;
    }

    public void setPointClickRadius(int pointClickRadius) {
        this.pointClickRadius = pointClickRadius;
    }

    public int getPointDefaultRadius() {
        return pointDefaultRadius;
    }

    public void setPointDefaultRadius(int pointDefaultRadius) {
        this.pointDefaultRadius = pointDefaultRadius;
    }
    public int getAxisColor() {
        return axisColor;
    }

    public void setAxisColor(int axisColor) {
        this.axisColor = axisColor;
    }


    public int getShowPicResource() {
        return showPicResource;
    }

    public void setShowPicResource(int showPicResource) {
        this.showPicResource = showPicResource;
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

    public int getPointMarginHeight() {
        return pointMarginHeight;
    }

    public void setPointMarginHeight(int pointMarginHeight) {
        this.pointMarginHeight = pointMarginHeight;
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

    public int getMarginHeight() {
        return marginHeight;
    }

    public void setMarginHeight(int marginHeight) {
        this.marginHeight = marginHeight;
    }


    public LineChartView(Context context) {
        this(context,null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        TypedArray typedValue = context.obtainStyledAttributes(attrs,R.styleable.LineChartView);
//        showPicResource = (int) typedValue.getDimension(R.styleable.LineChartView_showPicResource,R.mipmap.click_icon);
//        marginHeight = (int) typedValue.getDimension(R.styleable.LineChartView_marginHeight,10);
//        pointMarginHeight = typedValue.getInteger(R.styleable.LineChartView_pointMarginHeight,20);
//        defaultTextSize = typedValue.getInteger(R.styleable.LineChartView_defaultTextSize,14);
//        pointDefaultRadius = typedValue.getInteger(R.styleable.LineChartView_pointDefaultRadius,4);
//        pointClickRadius = typedValue.getInteger(R.styleable.LineChartView_pointClickRadius,2);
//        defaultStrokeWidth = typedValue.getInteger(R.styleable.LineChartView_defaultStrokeWidth,2);
//        yLableTextColor = typedValue.getInteger(R.styleable.LineChartView_yLableTextColor,R.color.ylable_textColor);
//        defaultColor = typedValue.getInteger(R.styleable.LineChartView_defaultColor,R.color.default_color);
//        xLableTextColor = typedValue.getInteger(R.styleable.LineChartView_xLableTextColor,R.color.ylable_textColor);
//        axisColor = typedValue.getInteger(R.styleable.LineChartView_axisColor,axisColor);
        init();
    }

    private void init() {
        // 新建画笔
        mDataLinePaint = new Paint();       // 数据(点和连线)画笔
        mScaleLinePaint = new Paint();      // 坐标(刻度线条)值画笔

        defaultStrokeWidth = DensityUtils.dip2px(mContext,defaultStrokeWidth);

        mDataLinePaint.setAntiAlias(true);
        mDataLinePaint.setStrokeWidth(defaultStrokeWidth);
        mDataLinePaint.setColor(mContext.getResources().getColor(defaultColor));
        //坐标轴的画笔
        mScaleLinePaint.setAntiAlias(true);
        mScaleLinePaint.setStrokeWidth(defaultStrokeWidth);
        mScaleLinePaint.setColor(mContext.getResources().getColor(axisColor));

        //白色透明圆
        transparentPaint.setColor(Color.WHITE);
        transparentPaint.setStyle(Paint.Style.FILL);
        transparentPaint.setStrokeWidth(defaultStrokeWidth);

        //点击后被选中的圆
        pointSelectedPaint.setAntiAlias(true);
        pointSelectedPaint.setStrokeWidth(1);
        pointSelectedPaint.setStyle(Paint.Style.STROKE);

        //默认圆画笔
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setColor(mContext.getResources().getColor(defaultColor));
        pointPaint.setStrokeWidth(defaultStrokeWidth);

        pointClickRadius = DensityUtils.dip2px(mContext, pointClickRadius);
        pointDefaultRadius = DensityUtils.dip2px(mContext,pointDefaultRadius);

        //Y轴lable画笔
        yTextLablePaint = new Paint();
        yTextLablePaint.setAntiAlias(true);
        yTextLablePaint.setTextSize(DensityUtils.sp2px(mContext,defaultTextSize));
        yTextLablePaint.setColor(mContext.getResources().getColor(yLableTextColor));

        //X轴lable画笔
        xTextLablePaint = new Paint();
        xTextLablePaint.setAntiAlias(true);
        xTextLablePaint.setTextSize(DensityUtils.sp2px(mContext,defaultTextSize));
        xTextLablePaint.setColor(mContext.getResources().getColor(xLableTextColor));

        //Y轴上的数据画笔
        yDataPaint = new Paint();
        yDataPaint.setAntiAlias(true);
        yDataPaint.setTextSize(DensityUtils.sp2px(mContext,defaultTextSize));
        yDataPaint.setColor(mContext.getResources().getColor(defaultColor));

        //图片画笔
        bitmapPaint = new Paint();

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
        float xLableHeight = getFontHeight(xTextLablePaint) + DensityUtils.dip2px(mContext,marginHeight);
        //y轴的长度
        yLength = DensityUtils.dip2px(mContext,viewHeight-marginHeight-xLableHeight);
        startX = getPaddingLeft();
        //起点坐标空出点画Y轴label
        startY = getPaddingTop();
        //X轴起始坐标(第一个值跟YLable文字的长度比较，取最长的,不然如果第一个值很大，会导致显示不全,还要加上文字间隔的宽度，用来显示点击之后的图片)
        xMarginWidth = yLableWidth >firstDataWidth? yLableWidth /2: firstDataWidth/2;
        //每一个X轴点之间的长度
        xLength = (viewWidth-xMarginWidth) / mList.size();
        firstDataWidth = yDataPaint.measureText(String.valueOf(mList.get(0).getValue()))+ DensityUtils.dip2px(mContext,pointMarginHeight);
        startPointX = startX + xMarginWidth;
        //Y轴起始坐标
        startPointY = startY + getFontHeight(yTextLablePaint)+DensityUtils.dip2px(mContext,marginHeight) +yLength;
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
            //将所有的值根据高度均分(如果需要显示点击之后的popwindow,可以加上popwindow的高度，如果不需要，可以去掉，或者设置为0)
            averHeight = (yLength - DensityUtils.dip2px(mContext,pointMarginHeight))/max;
            mList.get(i).setyAxis(startPointY - averHeight * Float.parseFloat(mList.get(i).getValue()));
        }
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<LineChartBean> list) {
        this.mList = list;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画Y轴标签
        drawYLable(canvas);
        //画数据圆点之间的连线
        drawDataLines(canvas);
        //画X轴
        drawXLine(canvas);
        //画Y轴
        drawYLine(canvas);
        //画X轴标签
        drawXLable(canvas);
        if(isClick){
            showClick(clickIndex,canvas);
        }
        //画Y轴上的数值
        drawYData(canvas);
        //画数据圆点
        drawDataPoints(canvas);
//        drawBgColor();              // 绘制背景色块

    }

    /**
     * 画Y轴上的数据
     * @param canvas
     */
    private void drawYData(Canvas canvas) {
        for(int i = 0;i<mList.size();i++){
            yDataWidth = yDataPaint.measureText(String.valueOf(mList.get(i).getValue()));
            if(isClick&&clickIndex==i){
                yDataPaint.setColor(Color.WHITE);
            }else{
                yDataPaint.setColor(mContext.getResources().getColor(defaultColor));
            }
            canvas.drawText(String.valueOf(mList.get(i).getValue()),mList.get(i).getxAxis()- yDataWidth /2,mList.get(i).getyAxis()-DensityUtils.dip2px(mContext,30),yDataPaint);

        }
    }


    /**
     * 画Y轴label
     */
    private void drawYLable(Canvas canvas){
        float yLableTextWidth = yTextLablePaint.measureText(yLableText);
        canvas.drawText(yLableText,startPointX-yLableTextWidth/2,startY+DensityUtils.dip2px(mContext,pointMarginHeight),yTextLablePaint);
    }

    /**
     * @return 返回指定的文字高度
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }

    /**
     *画X轴lable
     */
    private void drawXLable(Canvas canvas){
        for (int i = 0;i<mList.size();i++){
            float xLableWidth = xTextLablePaint.measureText(mList.get(i).getDate());
            if(isClick&&clickIndex==i){
                //点击之后改变xlable文字颜色
                xTextLablePaint.setColor(mContext.getResources().getColor(defaultColor));
            }else{
                //默认颜色
                xTextLablePaint.setColor(mContext.getResources().getColor(xLableTextColor));
            }
            canvas.drawText(mList.get(i).getDate(),startPointX-xLableWidth/2+i*xLength,startPointY+DensityUtils.dip2px(mContext,pointMarginHeight),xTextLablePaint);
        }
    }

    /**
     *画Y轴
     * @param canvas
     */
    private void drawYLine(Canvas canvas) {
        for(int i = 0;i<mList.size();i++){
            canvas.drawLine(startPointX+i*xLength,startPointY,startPointX+i*xLength,startPointY-yLength,mScaleLinePaint);
        }
    }

    /**
     * 画X轴
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        canvas.drawLine(startPointX, startPointY , startPointX + viewWidth-xMarginWidth, startPointY, mScaleLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        for (int i = 0; i < mList.size(); i++) {
            float dataX = mList.get(i).getxAxis();
            // 控制触摸/点击的范围，在有效范围内才触发
            if (Math.abs(touchX - dataX) < xLength / 2) {
                isClick = true;
                clickIndex = i;
                invalidate();
                //显示点击之后的图片
            }
        }
        return true;
    }

    /**
     * 绘制数据线条
     *
     * @param canvas
     */
    private void drawDataLines(Canvas canvas) {
        getPointRoords();
        for (int i = 0; i < mList.size(); i++) {
            mDataLinePaint.setColor(mContext.getResources().getColor(defaultColor));
            //最后一个点就不用再画连线了
            if(i == mList.size()-1)return;
            canvas.drawLine(mList.get(i).getxAxis(),mList.get(i).getyAxis(),mList.get(i+1).getxAxis(), mList.get(i+1).getyAxis(), mDataLinePaint);
        }
    }

    /**
     * 绘制数据点
     *
     * @param canvas
     */
    private void drawDataPoints(Canvas canvas) {
        for(int i = 0;i<mList.size();i++){
            // 点击后，绘制数据点
            if (isClick&&clickIndex == i) {
                //绘制白色背景
                canvas.drawCircle(mList.get(clickIndex).getxAxis(),mList.get(clickIndex).getyAxis(), pointDefaultRadius+DensityUtils.dip2px(mContext,2), transparentPaint);
                pointSelectedPaint.setColor(mContext.getResources().getColor(defaultColor));
                //绘制外层圆环
                canvas.drawCircle(mList.get(clickIndex).getxAxis(),mList.get(clickIndex).getyAxis(),pointDefaultRadius+DensityUtils.dip2px(mContext,2), pointSelectedPaint);
                pointPaint.setColor(mContext.getResources().getColor(defaultColor));
                pointPaint.setStyle(Paint.Style.FILL);
                pointPaint.setStrokeWidth(defaultStrokeWidth);
                //绘制点击之后圆点的小圆
                canvas.drawCircle(mList.get(clickIndex).getxAxis(),mList.get(clickIndex).getyAxis(),pointClickRadius, pointPaint);

            }else{
                //绘制白色背景
                canvas.drawCircle(mList.get(i).getxAxis(),mList.get(i).getyAxis(), pointDefaultRadius, transparentPaint);
                pointPaint.setColor(mContext.getResources().getColor(defaultColor));
                pointPaint.setStrokeWidth(defaultStrokeWidth);
                pointPaint.setStyle(Paint.Style.STROKE);
                //绘制圆环
                canvas.drawCircle(mList.get(i).getxAxis(),mList.get(i).getyAxis(), pointDefaultRadius, pointPaint);
            }
        }
    }

    /**
     * 点击数据点后，展示详细的数据值
     */
    private void showClick(int index,Canvas canvas) {
        float height = getFontHeight(yDataPaint)+DensityUtils.dip2px(mContext,pointMarginHeight);
        float width = yDataPaint.measureText(mList.get(index).getValue())+DensityUtils.dip2px(mContext,pointMarginHeight);
        bitmap = BitmapFactory.decodeResource(getResources(),showPicResource);
        resizeBitmap = resizeBitmap(bitmap, firstDataWidth, height);
        Log.d("第一个点的坐标：",mList.get(index).getxAxis()+"");
        //因为图片下边有个尖角，所以间隔会有一点点变化
        RectF rect1 = new RectF(mList.get(index).getxAxis()-width/2,
                mList.get(index).getyAxis()-height-DensityUtils.dip2px(mContext,marginHeight),
                mList.get(index).getxAxis()+width/2,
                mList.get(index).getyAxis()-DensityUtils.dip2px(mContext,18));
        canvas.drawBitmap(resizeBitmap,null,rect1,bitmapPaint);
    }

    /**
     * 使用Matrix将Bitmap压缩到指定大小
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, float w, float h)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = w / width;
        float scaleHeight =  h / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 释放bitmap资源
     */
    public void recycleBitmap(){
        if(bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
