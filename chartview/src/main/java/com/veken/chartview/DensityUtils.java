package com.veken.chartview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

import java.text.DecimalFormat;

/**
 * 屏幕密度计算工具
 * 计算公式 pixels = dips * (density / 160)
 */
public class DensityUtils {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param context
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param context
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param context
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * @return 返回指定的文字高度
     */
    public static float getFontHeight(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();//文字高
    }

    /**
     *格式化字符串,保留两位小数
     */

    public static String float2Format(float num){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(num);
    }

    /**
     * 取整
     * @param num
     * @return
     */
    public static String floatFormat(float num){
        DecimalFormat df = new DecimalFormat("0");
        return df.format(num);
    }


}
