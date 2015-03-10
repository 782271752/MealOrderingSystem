package com.mealordering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mealordering.net.model.FoodsResult;
import com.mealordering.net.model.MyOrderResult;
import com.mealordering.ui.AboutActivity;
import com.mealordering.ui.FeedbackActivity;
import com.mealordering.ui.FindRoadActivity;
import com.mealordering.ui.FoodDetailActivity;
import com.mealordering.ui.FoodDiyDetailActivity;
import com.mealordering.ui.HelpActivity;
import com.mealordering.ui.LoginActivity;
import com.mealordering.ui.MapActivity;
import com.mealordering.ui.MyAddressActivity;
import com.mealordering.ui.MyAddressAddActivity;
import com.mealordering.ui.MyMessageActivity;
import com.mealordering.ui.MyOrderActivity;
import com.mealordering.ui.MyPreferentialActivity;
import com.mealordering.ui.OrderCommentActivity;
import com.mealordering.ui.OrderConfirmActivity;
import com.mealordering.ui.OrderDetailActivity;
import com.mealordering.ui.RegisterActivity;
import com.mealordering.ui.ShopLocationActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Antholy on 14-2-11.
 */
public class Intents {
    public static final String ID = "id";
    public static final String ITEM = "item";
    public static final String ITEMS = "items";
    public static final String IS_SUBSCRIBE = "isSubscribe";
    public static final String ORDER_OPTIONS = "orderOptions";
    public static final String ORDER_SUBSCRIBE_TIME = "PrepareTime";
    public static final String CITY = "city";
    public static final String AREA = "area";
    public static final String FIND_ROAD = "find";

    private Intents() {
    }

    public static void launchLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void launchRegister(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public static void launchFoodDetail(Context context, FoodsResult.Food food) {
        Intent intent = new Intent(context, FoodDetailActivity.class);
        intent.putExtra(ITEM, food);
        context.startActivity(intent);
    }

    public static void launchFoodDIYDetail(Context context, FoodsResult.Food food) {
        Intent intent = new Intent(context, FoodDiyDetailActivity.class);
        intent.putExtra(ITEM, food);
        context.startActivity(intent);
    }

    public static void launchMyMessage(Context context) {
        Intent intent = new Intent(context, MyMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void launchMyOrder(Context context) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        context.startActivity(intent);
    }

    public static void launchMyPreferential(Context context) {
        Intent intent = new Intent(context, MyPreferentialActivity.class);
        context.startActivity(intent);
    }

    public static void launchMyAddress(Context context) {
        Intent intent = new Intent(context, MyAddressActivity.class);
        context.startActivity(intent);
    }

    public static void launchMyAddressAdd(Context context) {
        Intent intent = new Intent(context, MyAddressAddActivity.class);
//        intent.putExtra(MyAddressAddActivity.EXTRA_FROM, fromCode);
        context.startActivity(intent);
    }

    public static void launchOrderDetail(Context context, MyOrderResult.MyOrder item) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(ITEM, item);
        context.startActivity(intent);
    }

    public static void launchOrderConfirm(Context context, List<FoodsResult.Food> items, String personalPreferentialId
            , boolean isSubscribe, int orderOptions,String time) {
        Intent intent = new Intent(context, OrderConfirmActivity.class);
        intent.putExtra(ITEMS, (Serializable) items);
        intent.putExtra(ID, personalPreferentialId);
        intent.putExtra(IS_SUBSCRIBE, isSubscribe);
        intent.putExtra(ORDER_OPTIONS, orderOptions);
        intent.putExtra(ORDER_SUBSCRIBE_TIME,time);
        Log.e("intent_time----------------------------------------------------------------------------------------",time);
        context.startActivity(intent);
    }

    public static void launchOrderComment(Context context, MyOrderResult.MyOrder item) {
        Intent intent = new Intent(context, OrderCommentActivity.class);
        intent.putExtra(ITEM, item);
        context.startActivity(intent);
    }

    public static void launchAbout(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public static void launchFeedback(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    public static void launchHelp(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        context.startActivity(intent);
    }

    public static void launchFindRoad(Activity context, String city, String area, String find) {
        Intent intent = new Intent(context, FindRoadActivity.class);
        intent.putExtra(CITY, city);
        intent.putExtra(AREA, area);
        intent.putExtra(FIND_ROAD, find);
        context.startActivityForResult(intent, 0);
    }

    public static void launchMap(Context context, String orderId) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(ID, orderId);
        context.startActivity(intent);
    }

    public static void launchShopLocation(Context context) {
        Intent intent = new Intent(context, ShopLocationActivity.class);
        context.startActivity(intent);
    }


}
