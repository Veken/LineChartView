# LineChartView

LineChartView是一个关于Android折线图的开源图表库。目前仅支持折线图，点击之后的状态变化，可以选择是画一个图片还是一个背景框。

### Screens
![](https://github.com/Veken/LineChartView/raw/master/image/linechartview.gif)</br>

## Usage

### Gradle
* Step 1. Add LineChartView

```
    dependencies {
	        compile 'com.veken:linechartview:1.0.1'
	}
```
### XML
```XML
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
### Java
```Java
 LineChartView chartView = findViewById(R.id.chart_view);;
 chartView.setDefaultTextSize(24);
 chartView.setyLableText("分钟");
 chartView.setClickable(true);
 chartView.setNeedBg(true);
 //画的种类，目前仅支持图片和一个背景框
 chartView.setDrawType(DrawType.DrawBackground);
```
### Data
```Java
private List<LineChartBean> mList;
if(mList==null){
	mList = new ArrayList<>();
}
```
```Java
Random random = new Random();
for(int i=0;i<7;i++){
	LineChartBean lineChartBean = new LineChartBean();
	lineChartBean.setValue(String.valueOf(random.nextInt(10000)));
	lineChartBean.setDate(String.valueOf(i));
	mList.add(lineChartBean);
}
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





