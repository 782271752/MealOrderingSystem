package com.mealordering.employee;

import android.app.Activity;
import android.content.Intent;

import com.mealordering.employee.net.model.OrderResults;
import com.mealordering.employee.ui.GetOrderActivity;
import com.mealordering.employee.ui.LoginActivity;
import com.mealordering.employee.ui.MapActivity;
import com.mealordering.employee.ui.MyOrderActivity;
import com.mealordering.employee.ui.OrderDetailActivity;

/**
 * Created by Antholy on 14-2-11.
 */
public class Intents {

    private Intents() {
    }

    public static final String ID = "id";
    public static final String ITEMS = "items";
    public static final String ITEM = "item";

    public static void launchLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void launchGetOrder(Activity activity) {
        Intent intent = new Intent(activity, GetOrderActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    public static void launchMyOrder(Activity activity) {
        Intent intent = new Intent(activity, MyOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void launchOrderDetail(Activity activity, OrderResults.Order item) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra(ITEM, item);
        activity.startActivityForResult(intent, 0);
    }

    public static void launchMap(Activity activity, OrderResults.Order item) {
        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra(ITEM, item);
        activity.startActivity(intent);
    }
}
