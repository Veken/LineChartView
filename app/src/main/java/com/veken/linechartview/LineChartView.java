package com.veken.linechartview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
    private float startX = 0.0f;
    private float startY = 0.0f;
    //Popwindow的x，y坐标
    private int yoff;
    private int xoff;
    //X轴的lable
    private String[] xLabel;
    //数据点
    private String[] data;

    //点击后展示的图片
    private int showPicResource = R.mipmap.click_icon;


    //y轴lable文字
    private String yLableText;

    //每个点的x和y坐标
    private float[][] mPointCoords;

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

    private Canvas bitmapCanvas;

    //留一点间隔，来显示pop的高度
    private int showPopHeight = 30;

    //文字和X轴Y轴高度间隔
    private int marginHeight = 10;
    //点上的文字和点之间的间隔
    private int pointMarginHeight = 20;

    //Y轴数据的文字宽度
    private float yDataWidth;

    //默认文字大小
    private int defaultTextSize = 14;
    //默认圆心大小
    private int pointRadius = 4;
    //点击之后里面圆的圆心
    private int pointCircleRadius = 2;
    private int defaultStrokeWidth = 2;
    //Y轴lable颜色
    private  int yLableTextColor = R.color.ylable_textColor;
    //默认数据颜色
    private int defaultColor = R.color.default_color;
    //X轴Lable颜色
    private int xLableTextColor = R.color.ylable_textColor;
    //坐标轴的颜色
    private int axisColor = R.color.axisColor;

    private boolean isClick;                // 是否点击了数据点
    private int clickIndex = -1;            // 被点击的数据点的索引值

    private Context mContext;

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

    public int getPointCircleRadius() {
        return pointCircleRadius;
    }

    public void setPointCircleRadius(int pointCircleRadius) {
        this.pointCircleRadius = pointCircleRadius;
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
        init();
        initData();
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

        pointCircleRadius = DensityUtils.dip2px(mContext, pointCircleRadius);
        pointRadius = DensityUtils.dip2px(mContext,pointRadius);

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

    private void initData() {
        if(xLabel==null){
            xLabel = new String[]{};
        }

        if(data == null){
            data = new String[]{};
        }

        if(mPointCoords == null){
            mPointCoords = new float[7][2];
        }

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
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = DensityUtils.px2dip(mContext,getMeasuredHeight() - getPaddingTop()-getPaddingBottom());

        //Y轴lable文字宽度
        float yLableWidth = yTextLablePaint.measureText(yLableText);
        //X轴lable文字高度
        float xLableHeight = getFontHeight(xTextLablePaint) + DensityUtils.dip2px(mContext,marginHeight);

        //目前是显示7个数据
        xLength = width / data.length;
        yLength = DensityUtils.dip2px(mContext,height-marginHeight-xLableHeight);
        startX = getPaddingLeft();
        //起点坐标空出点画Y轴label
        startY = getPaddingTop();

        //X轴起始坐标(第一个值跟YLable文字的长度比较，取最长的,不然如果第一个值很大，会导致显示不全)
        float firstDataWidth = yDataPaint.measureText(String.valueOf(data[0]));
        startPointX = startX +yLableWidth/2>firstDataWidth/2?yLableWidth/2:firstDataWidth/2+DensityUtils.dip2px(mContext,marginHeight);
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
        float max = Float.parseFloat(data[0]);
        for (int i = 0; i < data.length; i++) {
            mPointCoords[i][0] = startPointX + i * xLength;
            //计算数组中的最大值
            if(max < Float.parseFloat(data[i])){
                max = Float.parseFloat(data[i]);
            }
        }
        for(int i = 0;i<data.length;i++){
            //将所有的值根据高度均分(如果需要显示点击之后的popwindow,可以加上popwindow的高度，如果不需要，可以去掉，或者设置为0)
            averHeight = (yLength - DensityUtils.dip2px(mContext,showPopHeight+pointMarginHeight))/max;
            mPointCoords[i][1] = startPointY - averHeight * Float.parseFloat(data[i]);
        }
    }

    /**
     * 设置x轴刻度值
     *
     * @param xLabel x刻度值
     */
    public void setxLabel(String[] xLabel) {
        this.xLabel = xLabel;
    }

    /**
     * 设置数据
     *
     * @param data 数据值
     */
    public void setData(String[] data) {
        this.data = data;
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
        //画Y轴上的数值
        drawYData(canvas);
        //画数据圆点
        drawDataPoints(canvas);
//        drawBgColor();              // 绘制背景色块

        bitmapCanvas = canvas;
    }

    /**
     * 画Y轴上的数据
     * @param canvas
     */
    private void drawYData(Canvas canvas) {
        for(int i = 0;i<data.length;i++){
            yDataWidth = yDataPaint.measureText(String.valueOf(data[i]));
            if(isClick&&clickIndex==i){
                yDataPaint.setColor(Color.WHITE);
            }else{
                yDataPaint.setColor(mContext.getResources().getColor(defaultColor));
            }
            canvas.drawText(String.valueOf(data[i]),mPointCoords[i][0]- yDataWidth /2,mPointCoords[i][1]-DensityUtils.dip2px(mContext,30),yDataPaint);

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
        for (int i = 0;i<xLabel.length;i++){
            float xLableWidth = xTextLablePaint.measureText(xLabel[i]);
            if(isClick&&clickIndex==i){
                //点击之后改变xlable文字颜色
                xTextLablePaint.setColor(mContext.getResources().getColor(defaultColor));
            }else{
                //默认颜色
                xTextLablePaint.setColor(mContext.getResources().getColor(xLableTextColor));
            }
            canvas.drawText(xLabel[i],startPointX-xLableWidth/2+i*xLength,startPointY+DensityUtils.dip2px(mContext,pointMarginHeight),xTextLablePaint);
        }
    }

    /**
     *画Y轴
     * @param canvas
     */
    private void drawYLine(Canvas canvas) {
        for(int i = 0;i<data.length;i++){
            canvas.drawLine(startPointX+i*xLength,startPointY,startPointX+i*xLength,startPointY-yLength,mScaleLinePaint);
        }
    }

    /**
     * 画X轴
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        canvas.drawLine(startPointX, startPointY , startPointX + xLength*data.length, startPointY, mScaleLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        for (int i = 0; i < data.length; i++) {
            float dataX = mPointCoords[i][0];
            // 控制触摸/点击的范围，在有效范围内才触发
            if (Math.abs(touchX - dataX) < xLength / 2) {
                isClick = true;
                clickIndex = i;
                //显示点击之后的图片
                showClick(i);
                break;
            }
        }
        invalidate();
        return true;
    }

    /**
     * 绘制数据线条
     *
     * @param canvas
     */
    private void drawDataLines(Canvas canvas) {
        getPointRoords();
        for (int i = 0; i < data.length; i++) {
            mDataLinePaint.setColor(mContext.getResources().getColor(defaultColor));
            //最后一个点就不用再画连线了
            if(i==data.length-1)return;
            canvas.drawLine(mPointCoords[i][0], mPointCoords[i][1], mPointCoords[i + 1][0], mPointCoords[i + 1][1], mDataLinePaint);
        }
    }

    /**
     * 绘制数据点
     *
     * @param canvas
     */
    private void drawDataPoints(Canvas canvas) {
        for(int i = 0;i<mPointCoords.length;i++){
            // 点击后，绘制数据点
            if (isClick&&clickIndex == i) {
                //绘制白色背景
                canvas.drawCircle(mPointCoords[clickIndex][0],mPointCoords[clickIndex][1], pointRadius+DensityUtils.dip2px(mContext,2), transparentPaint);
                pointSelectedPaint.setColor(mContext.getResources().getColor(defaultColor));
                //绘制外层圆环
                canvas.drawCircle(mPointCoords[clickIndex][0], mPointCoords[clickIndex][1],pointRadius+DensityUtils.dip2px(mContext,2), pointSelectedPaint);
                pointPaint.setColor(mContext.getResources().getColor(defaultColor));
                pointPaint.setStyle(Paint.Style.FILL);
                pointPaint.setStrokeWidth(defaultStrokeWidth);
                //绘制点击之后圆点的小圆
                canvas.drawCircle(mPointCoords[clickIndex][0], mPointCoords[clickIndex][1],pointCircleRadius, pointPaint);

            }else{
                //绘制白色背景
                canvas.drawCircle(mPointCoords[i][0],mPointCoords[i][1], pointRadius, transparentPaint);
                pointPaint.setColor(mContext.getResources().getColor(defaultColor));
                pointPaint.setStrokeWidth(defaultStrokeWidth);
                pointPaint.setStyle(Paint.Style.STROKE);
                //绘制圆环
                canvas.drawCircle(mPointCoords[i][0], mPointCoords[i][1], pointRadius, pointPaint);
            }
        }
    }

    /**
     * 点击数据点后，展示详细的数据值
     */
    private void showClick(int index) {
        float tvWidth = yDataPaint.measureText(String.valueOf(data[index]));
        float width = tvWidth+DensityUtils.dip2px(mContext,pointMarginHeight);
        float height = getFontHeight(yDataPaint)+DensityUtils.dip2px(mContext,pointMarginHeight);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),showPicResource);
        Bitmap resizeBitmap = resizeBitmap(bitmap, width, height);
        RectF rect1 = new RectF(mPointCoords[index][0]-width/2, mPointCoords[index][1]-height-DensityUtils.dip2px(mContext,marginHeight),mPointCoords[index][0]+width/2, mPointCoords[index][1]-DensityUtils.dip2px(mContext,15));
        bitmapCanvas.drawBitmap(resizeBitmap,null,rect1,bitmapPaint);
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
}
