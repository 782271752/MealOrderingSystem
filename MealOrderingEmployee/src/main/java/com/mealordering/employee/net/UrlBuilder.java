package com.mealordering.employee.net;

import com.google.api.client.http.GenericUrl;
import com.mealordering.employee.AppContext;

/**
 * Created by Anthony on 14-3-4.
 */
public class UrlBuilder {

    private static final String SCHEME = "http";
//    private static final String HOST = "59.38.125.114";
    private static final String HOST = "in.kebabar.cn";
    private static final int PORT = 8067;
    /**
     * 员工登录
     */
    private static final String LOGIN = "/json/send/Login.aspx";
    /**
     * 派送领单
     */
    private static final String GET_DISPATCH_ORDER = "/json/send/GetSingle.aspx";
    /**
     * 外送订单列表
     */
    private static final String MY_ORDER = "/json/send/MyOrder.aspx";
    /**
     * 订单详情
     */
    private static final String ORDER_DETAIL = "/json/send/OrderView.aspx";
    /**
     * 确认送达提交
     */
    private static final String CONFIRM_DISPATCH_ORDER = "/json/send/SendSubmit.aspx";
    /**
     * 更新员工坐标
     */
    private static final String UPDATE_XY = "/json/send/UpdateXY.aspx";

    private UrlBuilder() {
    }

    private static String getEmployeeId() {
        return AppContext.getInstance().getEmployeeId();
    }

    public static GenericUrl login(String employeeName, String password) {
        GenericUrl url = buildUrl(LOGIN);
        url.put("EmployeeName", employeeName);
        url.put("Pwd", password);
        return url;
    }

    public static GenericUrl getDispatchOrder(String orderNo) {
        GenericUrl url = buildUrl(GET_DISPATCH_ORDER);
        url.put("OrderNo", orderNo);
        url.put("EmployeeId", getEmployeeId());
        return url;
    }

    public static GenericUrl orders(int top, String orderState, String orderId) {
        GenericUrl url = buildUrl(MY_ORDER);
        url.put("Top", top);
        url.put("EmployeeId", getEmployeeId());
        url.put("OrderState", orderState);
        url.put("OrderId", orderId);
        return url;
    }

    public static GenericUrl orderDetail(String orderId) {
        GenericUrl url = buildUrl(ORDER_DETAIL);
        url.put("OrderId", orderId);
        return url;
    }

    public static GenericUrl confirmDispatchOrder(String orderId) {
        GenericUrl url = buildUrl(CONFIRM_DISPATCH_ORDER);
        url.put("OrderId", orderId);
        return url;
    }

    public static GenericUrl updateXY(double CoordinatesX, double CoordinatesY) {
        GenericUrl url = buildUrl(UPDATE_XY);
        url.put("EmployeeId", getEmployeeId());
        url.put("Coordinates_x", CoordinatesX);
        url.put("Coordinates_y", CoordinatesY);
        return url;
    }

    private static GenericUrl buildUrl(String path) {
        GenericUrl url = new GenericUrl();
        url.setScheme(SCHEME);
        url.setHost(HOST);
//        url.setPort(PORT);
        url.setRawPath(path);
        return url;
    }
}
