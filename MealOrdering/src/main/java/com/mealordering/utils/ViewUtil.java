package com.mealordering.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.client.util.escape.CharEscapers;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ViewUtil {
    private ViewUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T findView(Activity a, Integer id) {
        if (a == null) {
            L.d("Activity is null !");
            return null;
        }
        return (T) a.findViewById(id);
    }

    /**
     * 查找指定的View并设置点击事件
     *
     * @param a        Activity
     * @param id       View的Id
     * @param listener 点击事件
     */
    public static void findViewAndClick(Activity a, Integer id,
                                        OnClickListener listener) {
        setOnClick(findView(a, id), listener);
    }

    /**
     * 在View中查找指定的View .
     *
     * @param view 包含指定Id的View
     * @param id   View的Id
     * @return 查找到的View
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findView(View view, Integer id) {
        if (view == null) {
            L.d("view is null !");
            return null;
        }
        return (T) view.findViewById(id);
    }

    /**
     * 查找指定的View并设置点击事件
     *
     * @param view     包含指定Id的View
     * @param id       View的Id
     * @param listener 点击事件
     */
    public static void findViewAndClick(View view, Integer id,
                                        OnClickListener listener) {
        setOnClick(findView(view, id), listener);
    }

    /**
     * 查找指定的View并设置点击事件
     *
     * @param view 包含指定Id的View
     * @param id   View的Id
     */
    public static void findTextViewAndSetText(View view, Integer id,
                                              CharSequence text) {
        View target = findView(view, id);
        if (!(target instanceof TextView)) {
            L.d("target view is not TextView !");
            return;
        }
        ((TextView) target).setText(text);
    }

    public static void findTextViewAndSetText(Activity activity, Integer id,
                                              CharSequence text) {
        View target = findView(activity, id);
        if (!(target instanceof TextView)) {
            L.d("target view is not TextView !");
            return;
        }
        ((TextView) target).setText(text);
    }

    /**
     * 查找Fragment
     *
     * @param fm FragmentManager 对象
     * @param id Fragment的Id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T findFragment(FragmentManager fm,
                                                      Integer id) {
        if (fm == null) {
            L.d("view is null !");
            return null;
        }
        return (T) fm.findFragmentById(id);
    }

    /**
     * 查找Fragment
     *
     * @param fm  FragmentManager 对象
     * @param tag Fragment的Tag
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T findFragment(FragmentManager fm,
                                                      String tag) {
        if (fm == null) {
            L.d("view is null !");
            return null;
        }
        return (T) fm.findFragmentByTag(tag);
    }

    /**
     * 设置View的Visibility为GONE
     *
     * @param view 需要设置为View.GONE的View
     * @param gone 是否这是为View.GONE
     */
    public static void setGone(final View view, final boolean gone) {
        if (view == null) {
            L.d("view is null !");
            return;
        }
        final int current = view.getVisibility();
        if (gone && current != GONE)
            view.setVisibility(GONE);
        else if (!gone && current != VISIBLE)
            view.setVisibility(VISIBLE);
    }

    /**
     * 设置View的Visibility为INVISIBLE
     *
     * @param view      需要设置为View.INVISIBLE的View
     * @param invisible 是否这是为View.INVISIBLE
     */
    public static void setInvisible(final View view, final boolean invisible) {
        if (view == null) {
            L.d("view is null !");
            return;
        }
        final int current = view.getVisibility();
        if (invisible && current != INVISIBLE)
            view.setVisibility(INVISIBLE);
        else if (!invisible && current != VISIBLE)
            view.setVisibility(VISIBLE);
    }

    /**
     * 设置View的点击事件
     *
     * @param view     需要设置的View
     * @param listener 点击事件
     */
    public static void setOnClick(View view, OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 设置View是否可用
     *
     * @param view   需要设置的View
     * @param enable 是否可用
     */
    public static void setEnable(View view, boolean enable) {
        if (view != null) {
            view.setEnabled(enable);
        }
    }

    /**
     * 设置TextView的Text
     *
     * @param view TextView对象
     * @param text 字符串
     */
    public static void setText(TextView view, String text) {
        if (view != null) {
            view.setText(text);
            if (view instanceof EditText && !TextUtils.isEmpty(text)) {
                ((EditText) view).setSelection(text.length());
            }
        }
    }

    /**
     * 设置TextView的Text
     *
     * @param view  TextView对象
     * @param resId 字符串的资源ID
     */
    public static void setText(TextView view, Integer resId) {
        if (view != null && resId != null) {
            view.setText(resId);
        }
    }

    /**
     * 设置TextView的Text
     *
     * @param view TextView对象
     * @param text 可能包含html的字符串
     */
    public static void setTextMaybeHtml(TextView view, String text) {
        if (TextUtils.isEmpty(text)) {
            view.setText("");
            return;
        }
        if (text.contains("<") && text.contains(">")) {
            view.setText(Html.fromHtml(text));
            view.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            view.setText(text);
        }
    }

    /**
     * 异步加载图片到ImageView
     *
     * @param imageView ImageView对象
     * @param imageUrl  图片的url地址
     */
    public static void loadImage(ImageView imageView, String imageUrl) {
        loadImage(imageView, imageUrl, null);
    }

    /**
     * 异步加载图片到ImageView
     *
     * @param imageView ImageView对象
     * @param imageUrl  图片的url地址
     * @param listener  加载过程的监听事件
     */
    public static void loadImage(ImageView imageView, String imageUrl,
                                 ImageLoadingListener listener) {
        if (imageView == null) {
            L.d("ImageView is null !");
            return;
        }

        if (TextUtils.isEmpty(imageUrl)) {
            L.d("image url is empty !");
            return;
        }
        // CharEscapers.escapeUriQuery() 解决url中有中文,无法正常请求的问题
        ImageLoader.getInstance().displayImage(
                CharEscapers.escapeUriQuery(imageUrl), imageView, listener);
    }


    /**
     * 检查EditText是否为空, 为空时,显示设定的文字. 调用的是{@link android.widget.EditText#setError(CharSequence) EditText.setError()}
     *
     * @param et         需要检查的 EditText
     * @param emptyResId 为空时显示的字符串
     * @return 是否空.
     */
    public static boolean checkEmpty(EditText et, Integer emptyResId) {
        if (et == null || emptyResId == null)
            return false;

        return checkEmpty(et, et.getResources().getString(emptyResId));
    }

    /**
     * 检查EditText是否为空, 为空时,显示设定的文字. 调用的是{@link android.widget.EditText#setError(CharSequence) EditText.setError()}
     *
     * @param et          需要检查的 EditText
     * @param emptyString 为空时显示的字符串
     * @return 是否空.
     */
    public static boolean checkEmpty(EditText et, CharSequence emptyString) {
        if (et == null || TextUtils.isEmpty(emptyString))
            return false;
        String text = et.getText().toString();
        if (TextUtils.isEmpty(text)) {
            setError(et, emptyString);
            return true;
        }
        return false;
    }

    public static void setError(EditText et, CharSequence errorMsg) {
        et.requestFocus();
        et.setError(Html.fromHtml(String.format("<font color='#000'>%s</font>", errorMsg)));

    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale);
    }
}
