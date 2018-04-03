package com.shouxiu.rxjavaretrofit.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author yeping
 * @date 2018/4/3 11:29
 * Toast
 */

public class ToastUtils {
    public ToastUtils() {
    }

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), 0);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, 0);
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), 0);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), 0);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
