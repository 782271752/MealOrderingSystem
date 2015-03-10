package com.mealordering.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.mealordering.AppContext;

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
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示duration为 Toast.LENGTH_LONG 的Toast
     *
     * @param resId 显示内容的资源ID
     */
    public static void showLongTimeMsg(int resId) {
        showLongTimeMsg(context.getResources().getString(resId));
    }

    /**
     * 显示duration为 Toast.LENGTH_SHORT 的Toast
     *
     * @param text 显示的内容
     */
    public static void showShortTimeMsg(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示duration为 Toast.LENGTH_SHORT 的Toast
     *
     * @param resId 显示内容的资源ID
     */
    public static void showShortTimeMsg(int resId) {
        showShortTimeMsg(context.getResources().getString(resId));
    }
}
