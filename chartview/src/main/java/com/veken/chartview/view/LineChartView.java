package com.veken.chartview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.veken.chartview.bean.ChartBean;
import com.veken.chartview.drawtype.DrawBgType;
import com.veken.chartview.drawtype.DrawLineType;
import com.veken.chartview.DensityUtils;
import com.veken.linecharviewmodule.R;
import com.veken.chartview.drawtype.DrawConnectLineType;

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
    private List<ChartBean> mList = new ArrayList<>();

    //点击后展示的图片
    private int showPicResource = R.mipmap.click_icon;

    //y轴lable文字
    private String yLableText = "折线图";

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
    private Paint clickPaint;
    private Paint bgPaint;

    //文字和X轴Y轴高度间隔
    private int axisMarginHeight;
    //点上的文字和点之间的间隔
    private int pointMarginHeight;

    //文字和图片之间的间隔
    private int textAndClickBgMargin;

    //Y轴数据的文字宽度
    private float yDataWidth;
    //默认文字大小
    private int defaultTextSize = 14;
    //默认圆心大小
    private int pointDefaultRadius = 4;
    //点击之后里面圆的圆心
    private int pointClickRadius = 2;
    private int defaultStrokeWidth = 1;
    //Y轴lable颜色
    private int yLableTextColor;
    //默认数据颜色
    private int defaultColor;
    //X轴Lable颜色
    private int xLableTextColor;
    //坐标轴的颜色
    private int axisColor;
    //点击之后的背景颜色
    private int clickBgColor;

    //连接线的颜色
    private int connectLineColor;

    private  int startColor;
    private  int endColor;
    private int[] mColors;

    private boolean isClick;                // 是否点击了数据点
    private int clickIndex = -1;            // 被点击的数据点的索引值


    //是否需要点击事件
    private boolean clickable;
    //是否需要背景
    private boolean isNeedBg;

    private Context mContext;
    private Bitmap bitmap;
    private Bitmap resizeBitmap;
    private float firstDataWidth;
    private float yLableWidth;
    private float viewWidth;
    private float viewHeight;
    private float clickBgHeight;
    private float clickBgWidth;
    private float yDataHeight;
    private float xLableHeight;

    //虚线的每一小格宽度
    private int dottedLineWidth;

    private Path bgPath;
    //是否需要画数据跟Y轴的联系
    private boolean isNeedDrawConnectYDataLine;

    //是否需要画Y轴的刻度
//    private boolean isNeedDrawYScale = false;
    //画什么类型的背景
    private DrawBgType drawBgType;
    //画什么类型的Y轴连接线
    private DrawConnectLineType drawConnectLineType;
    //画虚线还是曲线
    private DrawLineType drawLineType = DrawLineType.Draw_Line;

    private Path curvePath;
    private RectF rectF;

    public DrawLineType getDrawLineType() {
        return drawLineType;
    }

    public void setDrawLineType(DrawLineType drawLineType) {
        this.drawLineType = drawLineType;
    }

//    public boolean isNeedDrawYScale() {
//        return isNeedDrawYScale;
//    }
//
//    public void setNeedDrawYScale(boolean needDrawYScale) {
//        isNeedDrawYScale = needDrawYScale;
//    }

    public int getTextAndClickBgMargin() {
        return textAndClickBgMargin;
    }

    public void setTextAndClickBgMargin(int textAndClickBgMargin) {
        this.textAndClickBgMargin = textAndClickBgMargin;
    }

    public int getConnectLineColor() {
        return connectLineColor;
    }

    public void setConnectLineColor(int connectLineColor) {
        this.connectLineColor = connectLineColor;
    }

    public int getDottedLineWidth() {
        return dottedLineWidth;
    }

    public void setDottedLineWidth(int dottedLineWidth) {
        this.dottedLineWidth = dottedLineWidth;
    }
    public DrawBgType getDrawBgType() {
        return drawBgType;
    }

    public void setDrawBgType(DrawBgType drawBgType) {
        this.drawBgType = drawBgType;
    }

    public DrawConnectLineType getDrawConnectLineType() {
        return drawConnectLineType;
    }

    public void setDrawConnectLineType(DrawConnectLineType drawConnectLineType) {
        this.drawConnectLineType = drawConnectLineType;
    }


    public int getClickBgColor() {
        return clickBgColor;
    }

    public void setClickBgColor(int clickBgColor) {
        this.clickBgColor = clickBgColor;
    }

    public int getPointClickRadius() {
        return pointClickRadius;
    }

    public void setPointClickRadius(int pointClickRadius) {
        this.pointClickRadius = pointClickRadius;
    }

    public boolean isNeedDrawConnectYDataLine() {
        return isNeedDrawConnectYDataLine;
    }

    public void setNeedDrawConnectYDataLine(boolean needDrawConnectYDataLine) {
        isNeedDrawConnectYDataLine = needDrawConnectYDataLine;
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


    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isNeedBg() {
        return isNeedBg;
    }

    public void setNeedBg(boolean needBg) {
        isNeedBg = needBg;
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
        TypedArray typedValue = context.obtainStyledAttributes(attrs,R.styleable.LineChartView);
        showPicResource =  typedValue.getResourceId(R.styleable.LineChartView_showPicResource,R.mipmap.click_icon);
        axisMarginHeight =  typedValue.getDimensionPixelSize(R.styleable.LineChartView_axisMarginHeight,DensityUtils.dip2px(mContext,10));
        pointMarginHeight = typedValue.getDimensionPixelSize(R.styleable.LineChartView_pointMarginHeight,DensityUtils.dip2px(mContext,20));
        defaultTextSize = typedValue.getDimensionPixelSize(R.styleable.LineChartView_defaultTextSize,DensityUtils.sp2px(mContext,14));
        pointDefaultRadius = typedValue.getDimensionPixelSize(R.styleable.LineChartView_pointDefaultRadius,DensityUtils.dip2px(mContext,3));
        pointClickRadius = typedValue.getDimensionPixelSize(R.styleable.LineChartView_pointClickRadius,DensityUtils.dip2px(mContext,2));
        defaultStrokeWidth = typedValue.getDimensionPixelSize(R.styleable.LineChartView_defaultStrokeWidth,DensityUtils.dip2px(mContext,1));
        yLableTextColor = typedValue.getColor(R.styleable.LineChartView_yLableTextColor,0XFF99989d);
        defaultColor = typedValue.getColor(R.styleable.LineChartView_defaultColor,0XFF5287F7);
        xLableTextColor = typedValue.getColor(R.styleable.LineChartView_xLableTextColor,0XFF99989d);
        axisColor = typedValue.getColor(R.styleable.LineChartView_axisColor,0XFF99989d);
        textAndClickBgMargin = typedValue.getDimensionPixelSize(R.styleable.LineChartView_textAndClickBgMargin,DensityUtils.dip2px(mContext,10));
        clickBgColor = typedValue.getColor(R.styleable.LineChartView_clickBgColor,0XFF5287F7);
        startColor = typedValue.getColor(R.styleable.LineChartView_startColor,0X20BFEFFF);
        endColor = typedValue.getColor(R.styleable.LineChartView_endColor,0XFF5287F7);
        clickable = typedValue.getBoolean(R.styleable.LineChartView_clickable,true);
        isNeedBg = typedValue.getBoolean(R.styleable.LineChartView_isNeedBg,true);
        yLableText = typedValue.getString(R.styleable.LineChartView_yLableText);
        isNeedDrawConnectYDataLine = typedValue.getBoolean(R.styleable.LineChartView_isNeedDrawConnectYDataLine, false);
        dottedLineWidth = typedValue.getDimensionPixelSize(R.styleable.LineChartView_dottedLineWidth,DensityUtils.dip2px(mContext,3));
//        isNeedDrawYScale = typedValue.getBoolean(R.styleable.LineChartView_isNeedDrawYScale,false);
        connectLineColor = typedValue.getColor(R.styleable.LineChartView_connectLineColor,0XFF5287F7);
        mColors = new int[2];
        mColors[0] = startColor;
        mColors[1] = endColor;
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

        //白色透明圆
        transparentPaint.setColor(Color.WHITE);
        transparentPaint.setStyle(Paint.Style.FILL);
        transparentPaint.setStrokeWidth(defaultStrokeWidth);

        //点击后被选中的圆
        pointSelectedPaint.setAntiAlias(true);
        pointSelectedPaint.setStrokeWidth(defaultStrokeWidth);
        pointSelectedPaint.setStyle(Paint.Style.STROKE);

        //默认圆画笔
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setColor(defaultColor);
        pointPaint.setStrokeWidth(defaultStrokeWidth);

        pointClickRadius = DensityUtils.dip2px(mContext, pointClickRadius);
        pointDefaultRadius = DensityUtils.dip2px(mContext,pointDefaultRadius);

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

        //点击之后的画笔
        clickPaint = new Paint();

        bgPath = new Path();

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);

        //曲线路径
        curvePath = new Path();

        rectF = new RectF();
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
        if(!TextUtils.isEmpty(yLableText)){
            yLableWidth = yTextLablePaint.measureText(yLableText);
        }else{
            yLableWidth = yTextLablePaint.measureText("");
        }
        //Y轴lable文字宽度
        //X轴lable文字高度
        xLableHeight = DensityUtils.getFontHeight(xTextLablePaint,mList.get(0).getDate())+ DensityUtils.dip2px(mContext,axisMarginHeight);
        //y轴的长度
        yLength = DensityUtils.dip2px(mContext,viewHeight-axisMarginHeight- xLableHeight);
        startX = getPaddingLeft();
        //起点坐标空出点画Y轴label
        startY = getPaddingTop();
        //点击之后背景的宽度和高度
        clickBgHeight = DensityUtils.getFontHeight(yDataPaint,mList.get(0).getValue())+ DensityUtils.dip2px(mContext,textAndClickBgMargin);
        clickBgWidth = yDataPaint.measureText(mList.get(0).getValue())+ DensityUtils.dip2px(mContext,textAndClickBgMargin);
        //点上文字的高度
        yDataHeight = DensityUtils.getFontHeight(yDataPaint,mList.get(0).getValue());
        float maxWidth = yDataPaint.measureText(String.valueOf(mList.get(0).getValue()));
        float currentWidth = 0;
        for (int i = 0; i <mList.size() ; i++) {
            currentWidth = yDataPaint.measureText(String.valueOf(mList.get(i).getValue()));
            if(maxWidth<currentWidth){
                maxWidth = currentWidth;
            }
        }
        firstDataWidth = maxWidth+ DensityUtils.dip2px(mContext,axisMarginHeight);
        //X轴起始坐标(第一个值跟YLable文字的长度比较，取最长的,不然如果第一个值很大，会导致显示不全,还要加上文字间隔的宽度，用来显示点击之后的图片)
        xMarginWidth = yLableWidth >firstDataWidth? yLableWidth /2: firstDataWidth/2;
        //每一个X轴点之间的长度
        xLength = (viewWidth-xMarginWidth) / mList.size();
        startPointX = startX + xMarginWidth;
        //Y轴起始坐标
        startPointY = startY + DensityUtils.getFontHeight(yTextLablePaint,yLableText)+DensityUtils.dip2px(mContext,axisMarginHeight) +yLength;

    }

    /**
     * 获取数据值的坐标点
     *
     * @return 数据点的坐标
     */
    private void getPoints() {
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
            //将所有的值根据高度均分(如果需要显示点击之后的背景,可以加上背景的高度，如果不需要，可以去掉，或者设置为0)
            averHeight = (yLength - DensityUtils.dip2px(mContext,pointMarginHeight)-clickBgHeight/2+yDataHeight/2)/max;
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
        //画数据圆点之间的连线
        drawDataLines(canvas);
        //画X轴标签
        drawXLable(canvas);
        //是否需要画Y轴的刻度
//        if(isNeedDrawYScale){
//            drawYScale(canvas);
//        }
        // 绘制背景色块
        if(isNeedBg){
            drawBgColor(canvas);
        }
        //画X轴
        drawXLine(canvas);
        //画Y轴
        drawYLine(canvas);
        //点击之后状态
        if(isClick&&clickable){
            showClick(clickIndex,canvas);
        }
        //画Y轴上的数值
        drawYData(canvas);
        //画Y轴数据连接的线
        if(isNeedDrawConnectYDataLine){
            drawConnectYDataLine(canvas);
        }
        //画数据圆点
        drawDataPoints(canvas);
    }

//    /**
//     * 是否需要画Y轴的刻度
//     * @param canvas
//     */
//    private void drawYScale(Canvas canvas) {
//        //所有文字的高度都是一致的
//        float textHeight = DensityUtils.getFontHeight(yTextLablePaint,mList.get(0).getValue());//文字高
//        for (int i = 0; i < mList.size(); i++) {
//            float yValueWidth = yTextLablePaint.measureText(mList.get(i).getValue());
//            canvas.drawText(mList.get(i).getValue(),startPointX-yValueWidth-DensityUtils.dip2px(mContext,5)-clickBgWidth/2,mList.get(i).getyAxis()+textHeight/2,yTextLablePaint);
//        }
//    }

    /**
     * 画Y轴数据连接的线
     * @param canvas
     */
    private void drawConnectYDataLine(Canvas canvas) {
        mScaleLinePaint.setColor(connectLineColor);
        for (int i = 0; i <mList.size() ; i++) {
            if(drawConnectLineType == DrawConnectLineType.DrawFullLine){
                //实线
                canvas.drawLine(startPointX,mList.get(i).getyAxis(),mList.get(i).getxAxis(),mList.get(i).getyAxis(),mScaleLinePaint);
            }else if(drawConnectLineType == DrawConnectLineType.DrawDottedLine){
                //虚线
                //虚线
                float x = mList.get(i).getxAxis() - startPointX;
                float spaceWidth = DensityUtils.dip2px(mContext,2);
                int count = (int) (x /(spaceWidth+dottedLineWidth));
                for(int j = 0;j<count;j++){
                    canvas.drawLine(startPointX+(spaceWidth+dottedLineWidth)*j,mList.get(i).getyAxis(),startPointX+(spaceWidth+dottedLineWidth)*j+dottedLineWidth,mList.get(i).getyAxis(),mScaleLinePaint);
                }
                canvas.drawLine(startPointX+(spaceWidth+dottedLineWidth)*count,mList.get(i).getyAxis(),mList.get(i).getxAxis(),mList.get(i).getyAxis(),mScaleLinePaint);
            }
        }
    }

    /**
     * 画背景颜色
     * @param canvas
     */
    private void drawBgColor(Canvas canvas) {
        LinearGradient hrLg = new LinearGradient(startPointX, startPointY, mList.get(0).getxAxis(),mList.get(0).getyAxis(), startColor,endColor, Shader.TileMode.CLAMP);
        bgPaint.setShader(hrLg);
        if(drawLineType==DrawLineType.Draw_Line){
            //设置垂直透明度渐变,起点坐标(x是图表中心,y是最高点的纵坐标,其值最小),终点坐标(x是图表中心,y是最低点的纵坐标,其值最大)
//        float x = yLength / 2;
//        LinearGradient vtLg = new LinearGradient(x, 0, x, getHeight(),startColor, endColor, Shader.TileMode.CLAMP);
//        Shader composeShader = new ComposeShader(hrLg, vtLg, PorterDuff.Mode.MULTIPLY);
            bgPath.moveTo(startPointX,startPointY);
            for(int i = 0;i<mList.size();i++){
                bgPath.lineTo(mList.get(i).getxAxis(),mList.get(i).getyAxis());
            }
            bgPath.lineTo(mList.get(mList.size()-1).getxAxis(),startPointY);
            canvas.drawPath(bgPath,bgPaint);
        }else if(drawLineType==DrawLineType.Draw_Curve){
            bgPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(drawCurveLine(),bgPaint);
        }
    }

    /**
     * 画Y轴上的数据
     * @param canvas
     */
    private void drawYData(Canvas canvas) {
        for(int i = 0;i<mList.size();i++){
            yDataWidth = yDataPaint.measureText(String.valueOf(mList.get(i).getValue()));
            if(isClick&&clickIndex==i&&clickable){
                yDataPaint.setColor(Color.WHITE);
            }else{
                yDataPaint.setColor(defaultColor);
            }
            canvas.drawText(String.valueOf(mList.get(i).getValue()),mList.get(i).getxAxis()- yDataWidth /2,mList.get(i).getyAxis()-DensityUtils.dip2px(mContext,pointMarginHeight),yDataPaint);

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
            if(isClick&&clickIndex==i&&clickable){
                //点击之后改变xlable文字颜色
                xTextLablePaint.setColor(defaultColor);
            }else{
                //默认颜色
                xTextLablePaint.setColor(xLableTextColor);
            }
            canvas.drawText(mList.get(i).getDate(),startPointX-xLableWidth/2+i*xLength,startPointY+DensityUtils.getFontHeight(xTextLablePaint,mList.get(i).getDate())+DensityUtils.dip2px(mContext,axisMarginHeight)
                    ,xTextLablePaint);
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
        mScaleLinePaint.setColor(axisColor);
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
        getPoints();
        if(isNeedBg){
            mDataLinePaint.setColor(endColor);
        }else{
            mDataLinePaint.setColor(defaultColor);
        }
        mDataLinePaint.setStyle(Paint.Style.STROKE);
        if(drawLineType==DrawLineType.Draw_Curve){
            canvas.drawPath(drawCurveLine(),mDataLinePaint);
        }else if(drawLineType == DrawLineType.Draw_Line){
            for (int i = 0; i < mList.size(); i++) {
                //最后一个点就不用再画连线了
                if(i == mList.size()-1)return;
                canvas.drawLine(mList.get(i).getxAxis(),mList.get(i).getyAxis(),mList.get(i+1).getxAxis(), mList.get(i+1).getyAxis(), mDataLinePaint);
            }
        }
    }

    /**
     * 返回曲线图路径
     * @return
     */
    private Path drawCurveLine() {
        curvePath.reset();
        for (int i = 0; i <mList.size(); i++) {
            if (i != mList.size() - 1) {
                if (i == 0) {
                    curvePath.moveTo(mList.get(0).getxAxis(), mList.get(0).getyAxis());
                }
                curvePath.cubicTo((mList.get(i).getxAxis() + mList.get(i + 1).getxAxis()) / 2,
                        mList.get(i).getyAxis(),
                        (mList.get(i).getxAxis() + mList.get(i + 1).getxAxis()) / 2,
                        mList.get(i + 1).getyAxis(),
                        mList.get(i + 1).getxAxis(),
                        mList.get(i + 1).getyAxis());
            } else {
                    curvePath.lineTo(mList.get(mList.size() - 1).getxAxis(), startPointY);
                    curvePath.lineTo(mList.get(0).getxAxis(), startPointY);
                    curvePath.lineTo(mList.get(0).getxAxis(), mList.get(0).getyAxis());
            }
        }
        return curvePath;
    }

    /**
     * 绘制数据点
     *
     * @param canvas
     */
    private void drawDataPoints(Canvas canvas) {
        for(int i = 0;i<mList.size();i++){
            // 点击后，绘制数据点
            if (isClick&&clickIndex == i&&clickable) {
//                //绘制白色背景
                canvas.drawCircle(mList.get(clickIndex).getxAxis(),mList.get(clickIndex).getyAxis(), pointDefaultRadius+DensityUtils.dip2px(mContext,2), transparentPaint);
                pointSelectedPaint.setColor(defaultColor);
                //绘制外层圆环
                canvas.drawCircle(mList.get(clickIndex).getxAxis(),mList.get(clickIndex).getyAxis(),pointDefaultRadius+DensityUtils.dip2px(mContext,2), pointSelectedPaint);
                pointPaint.setColor(defaultColor);
                pointPaint.setStyle(Paint.Style.FILL);
                pointPaint.setStrokeWidth(defaultStrokeWidth);
                //绘制点击之后圆点的小圆
                canvas.drawCircle(mList.get(clickIndex).getxAxis(),mList.get(clickIndex).getyAxis(),pointClickRadius, pointPaint);

            }else{
                //绘制白色背景
                canvas.drawCircle(mList.get(i).getxAxis(),mList.get(i).getyAxis(), pointDefaultRadius, transparentPaint);
                pointPaint.setColor(defaultColor);
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
        //计算区域
        rectF.left = mList.get(index).getxAxis()-clickBgWidth/2;
        rectF.top = mList.get(index).getyAxis()-clickBgHeight/2- DensityUtils.dip2px(mContext,pointMarginHeight);
        rectF.right = mList.get(index).getxAxis()+clickBgWidth/2;
        rectF.bottom = mList.get(index).getyAxis()-DensityUtils.dip2px(mContext,pointMarginHeight)+DensityUtils.dip2px(mContext,textAndClickBgMargin/2)-yDataHeight/2;
        switch (drawBgType){
            case DrawBackground:
                clickPaint.setColor(clickBgColor);
                canvas.drawRect(rectF,clickPaint);
                break;
            case DrawBitmap:
                bitmap = BitmapFactory.decodeResource(getResources(),showPicResource);
                resizeBitmap = resizeBitmap(bitmap, clickBgWidth, clickBgHeight);
                canvas.drawBitmap(resizeBitmap,null, rectF,clickPaint);
                break;
        }
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
