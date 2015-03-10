package com.mealordering.employee.utils;

import android.widget.Toast;

import com.mealordering.employee.AppContext;


public class ToastUtil {
    private static AppContext context = AppContext.getInstance();

    private ToastUtil() {
    }

    /**
     * 显示duration为 Toast.LENGTH_LONG 的Toast
     *
     * @param text 显示的内容
     */
    public static void showLongTimeMsg(CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示duration为 Toast.LENGTH_LONG 的Toast
     *
     * @param resId 显示内容的资源ID
     */
    public static void showLongTimeMsg(int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示duration为 Toast.LENGTH_SHORT 的Toast
     *
     * @param text 显示的内容
     */
    public static void showShortTimeMsg(CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示duration为 Toast.LENGTH_SHORT 的Toast
     *
     * @param resId 显示内容的资源ID
     */
    public static void showShortTimeMsg(int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
