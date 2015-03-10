package com.mealordering.employee.net;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.api.client.http.GenericUrl;
import com.mealordering.employee.net.listener.SimpleRequestListener;
import com.mealordering.employee.net.model.ConfirmDispathOrderResult;
import com.mealordering.employee.net.model.EmptyResults;
import com.mealordering.employee.net.model.GetOrderResult;
import com.mealordering.employee.net.model.LoginResult;
import com.mealordering.employee.net.model.OrderDetailResult;
import com.mealordering.employee.net.model.OrderResults;
import com.mealordering.employee.utils.DialogHelper;
import com.mealordering.employee.utils.L;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Created by Anthony on 14-2-25.
 */
public class RequestHelper {


    private RequestHelper() {
    }

    public static void exeLoginRequest(Context context, SpiceManager manager, SimpleRequestListener<LoginResult> requestListener, String employeeName, String password) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.login(employeeName, password);
        L.d("login -> %s", url.toString());
        SpiceRequest request = new SpiceGetRequest<LoginResult>(url, LoginResult.class);
        Dialog dialog = createCancelAbleLoadingDialog(context, "正在登录,请稍等.", manager, request);
        requestListener.setDialog(dialog);
        manager.execute(request, requestListener);
    }

    public static void exeGetDispatchOrderRequest(Context context, SpiceManager manager, SimpleRequestListener<GetOrderResult> requestListener,
                                                  String orderNo) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.getDispatchOrder(orderNo);
        L.d("getDispatchOrder -> %s", url.toString());
        SpiceRequest request = new SpicePostRequest<GetOrderResult>(url, GetOrderResult.class);
        Dialog dialog = createCancelAbleLoadingDialog(context, "正在领单,请稍等.", manager, request);
        requestListener.setDialog(dialog);
        manager.execute(request, requestListener);
    }

    public static void exeOrdersRequest(Context context, SpiceManager manager, SimpleRequestListener<OrderResults> requestListener,
                                        String orderState, String orderId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.orders(200, orderState, orderId);
        L.d("orders -> %s", url.toString());
        SpiceRequest request = new SpicePostRequest<OrderResults>(url, OrderResults.class);
        manager.execute(request, requestListener);
    }

    public static void exeOrderDetailRequest(Context context, SpiceManager manager, SimpleRequestListener<OrderDetailResult> requestListener,
                                             String orderId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.orderDetail(orderId);
        L.d("orderDetail-> %s", url.toString());
        SpiceRequest request = new SpicePostRequest<OrderDetailResult>(url, OrderDetailResult.class);
        Dialog dialog = createCancelAbleLoadingDialog(context, "正在获取订单详情,请稍等.", manager, request);
        requestListener.setDialog(dialog);
        manager.execute(request, requestListener);
    }

    public static void exeConfirmDispatchOrderRequest(Context context, SpiceManager manager, SimpleRequestListener<ConfirmDispathOrderResult> requestListener,
                                                      String orderId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.confirmDispatchOrder(orderId);
        L.d("confirmDispatchOrder -> %s", url.toString());
        SpiceRequest request = new SpicePostRequest<ConfirmDispathOrderResult>(url, ConfirmDispathOrderResult.class);
        Dialog dialog = createCancelAbleLoadingDialog(context, "正在确认订单,请稍等.", manager, request);
        requestListener.setDialog(dialog);
        manager.execute(request, requestListener);
    }

    public static void exeUpdateXYRequest(SpiceManager manager, SimpleRequestListener<EmptyResults> requestListener,
                                          double CoordinatesX, double CoordinatesY) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.updateXY(CoordinatesX, CoordinatesY);
        L.d("updateXY -> %s", url.toString());
        SpiceRequest request = new SpicePostRequest<EmptyResults>(url, EmptyResults.class);
        manager.execute(request, requestListener);
    }

    private static Dialog createCancelAbleLoadingDialog(Context context, String message,
                                                        final SpiceManager manager,
                                                        final SpiceRequest request) {
        Dialog dialog = DialogHelper.buildLoadingDialog(context, message);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                manager.cancel(request);
            }
        });
        return dialog;
    }

    private static boolean checkSpiceManagerNotNull(SpiceManager manager) {
        if (manager == null) {
            L.e("SpiceManager is null ! can't execute request !");
            return false;
        }
        return true;
    }
}
