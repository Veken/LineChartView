# ChartView

ChartView是一个Android开源图表库。目前仅支持折线图，曲线图，柱状图，饼状图，以及折线图和曲线图点击之后的状态变化，可以选择是画一个图片还是一个背景框。

### Screens
折线图和曲线图</br>

![折线图和曲线图](https://github.com/Veken/LineChartView/raw/master/image/chartview.gif)</br>

饼状图有内圆</br>

![饼状图有内圆](https://github.com/Veken/LineChartView/raw/master/image/piechart_inside.gif)</br>

饼状图没有内圆</br>

![饼状图没有内圆](https://github.com/Veken/LineChartView/raw/master/image/piechart.gif)</br>

## Usage

### Gradle
* Step 1. Add LineChartView
```
    dependencies {
	       compile 'com.veken:chart_view:1.0.0'
	}
```
### 折线图
### XML
```
  <com.veken.linecharviewmodule.LineChartView
            android:id="@+id/chart_view"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            app:showPicResource="@mipmap/ic_launcher"
            app:defaultStrokeWidth="0.5dp"
            app:pointClickRadius="3dp"
            app:defaultTextSize="16sp"
            app:pointDefaultRadius="2dp"
            app:isNeedBg="true"
            app:clickable="true"
            app:startColor="@color/startColor"
            app:endColor="@color/endColor" />

```
> 折线图自定义属性

attrs | description
---|---
showPicResource | 点击之后显示的图片资源
defaultTextSize | 默认文字大小
yLableTextColor | Y轴lable文字颜色
xLableTextColor | X轴Lable文字颜色
defaultColor    | 默认颜色
axisColor       | 坐标轴颜色
defaultStrokeWidth | 默认画笔的StrokeWidth 
axisMarginHeight    | X轴下的文字和X轴之间的间隔高度
yLableText      | Y轴的文字描述
isNeedBg        | 是否需要背景（默认为不需要=false）
isNeedDrawConnectYDataLine | 是否需要点和Y轴之间的连接线（默认为不需要=false）
dottedLineWidth | 连接线为虚线时的宽度
connectLineColor | 连接线的颜色 
pointMarginHeight | 点和文字之间的间隔高度
pointDefaultRadius | 点默认半径
pointClickRadius | 点击之后圆的半径
textAndClickBgMargin | 点击之后文字和点击背景上下左右的间隔
clickBgColor | 点击之后的背景颜色
startColor | 背景颜色的起始色
endColor | 背景颜色的结束色
clickable | 是否可以点击

### Java
```
LineChartView lineChartView = view.findViewById(R.id.line_chart_view);
lineChartView.setyLableText("折线图");
//设置点击背景（可以为图片，也可以为一个颜色背景，大小根据textAndClickBgMargin设置）
lineChartView.setDrawBgType(DrawBgType.DrawBitmap);
//设置图片资源
lineChartView.setShowPicResource(R.mipmap.click_icon);
//连接线为虚线（也可以为实现）
lineChartView.setDrawConnectLineType(DrawConnectLineType.DrawDottedLine);
lineChartView.setClickable(true);
//是否需要画连接线
lineChartView.setNeedDrawConnectYDataLine(true);
//连接线的颜色
lineChartView.setConnectLineColor(getResources().getColor(R.color.default_color));
//是否需要背景
lineChartView.setNeedBg(true);
//画曲线图（也可以为折线图）
lineChartView.setDrawLineType(DrawLineType.Draw_Curve);
```
### Data
```
private ArrayList<ChartBean> lineChartBeanList;
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
}
```

### 柱状图
### XML
```
  <com.veken.chartview.view.BarChartView
        android:id="@+id/bar_chart_view"
        android:layout_width="match_parent"
        android:layout_height="300dp"/>

```
> 柱状图自定义属性

attrs | description
---|---
axisXItemWidth | 每一个Item的宽度
axisXItemMargin | Item之间的间隔
isNeedDrawYScale | 是否需要画Y轴的刻度
bgColor | 柱状图背景颜色
其他同折线图

### Java
```
private BarChartView barChartView;
barChartView = view.findViewById(R.id.bar_chart_view);
//是否需要柱状图的背景
barChartView.setNeedBg(true);
//Y轴文字
barChartView.setyLableText("柱状图");
//是否需要连接线
barChartView.setNeedDrawConnectYDataLine(true);
//是否需要画Y轴刻度
barChartView.setNeedDrawYScale(true);
//连接线的样式为虚线（也可以为直线）
barChartView.setDrawConnectLineType(DrawConnectLineType.DrawDottedLine);
```
### Data
```
private ArrayList<ChartBean> barChartBeanList;
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
barChartView.setData(barChartBeanList);
```

### 饼状图
### XML
```
  <com.veken.chartview.view.PieChartView
        android:id="@+id/piechart_view"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:insideText="Hello"
        app:insideTextColor="@color/default_color"
        app:insideRadiusPercent="0.5"/>
```
> 饼状图自定义属性

attrs | description
---|---
textColor | 百分比文字颜色
textOutCircleMargin | 文字和圆之间的间距
isNeedInside | 是否需要内圆
insideText | 内圆显示的文字
insideBgColor | 内圆背景颜色
insideTextSize | 内圆文字大小
insideTextColor | 内圆文字颜色
insideRadiusPercent | 内圆半径占外圆半径的大小,默认(0.5)

### Java
```
private PieChartView pieChartView;
pieChartView = view.findViewById(R.id.piechart_view);
//内圆中间文字
pieChartView.setInsideText("Hello");
//是否需要加载动画
pieChartView.setIsNeedAnimation(true,5000);
//是否需要背景
pieChartView.setNeedInside(false);
```
### Data
```
private List<PieChartBean> mList;
if(mList==null){
    mList = new ArrayList<>();
}
//各个扇形的背景颜色
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
```

## License
```
Copyright (C) 2018 Veken

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
******

## About Me

简书地址：https://www.jianshu.com/p/4a5949d92821





