package com.mealordering.net;

import com.google.api.client.http.GenericUrl;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.CityResults;
import com.mealordering.net.model.EmptyResult;
import com.mealordering.net.model.EmptyResults;
import com.mealordering.net.model.FoodDetailResult;
import com.mealordering.net.model.FoodDiyDetailResult;
import com.mealordering.net.model.FoodTypesResult;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.net.model.HelpResult;
import com.mealordering.net.model.LoginResult;
import com.mealordering.net.model.MyAddressDefaultResult;
import com.mealordering.net.model.MyAddressResult;
import com.mealordering.net.model.MyMessageResults;
import com.mealordering.net.model.MyOrderDetailResult;
import com.mealordering.net.model.MyOrderResult;
import com.mealordering.net.model.MyPreferentialResult;
import com.mealordering.net.model.OrderByXYResult;
import com.mealordering.net.model.RegisterCodeResult;
import com.mealordering.net.model.RegisterResult;
import com.mealordering.net.model.ShopIdResult;
import com.mealordering.net.model.ShopLocationResults;
import com.mealordering.net.model.ShopRoadResults;
import com.mealordering.net.model.UserDetailResult;
import com.mealordering.utils.L;
import com.mealordering.utils.ToastUtil;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

/**
 * Created by Anthony on 14-2-25.
 */
public class RequestHelper {


    private RequestHelper() {
    }

    public static void exeGetCodeRequest(SpiceManager manager, RequestListener<RegisterCodeResult> requestListener, String phone) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.getCode(phone);
        L.d("get code -> %s", url.toString());
        manager.execute(new SpiceGetRequest<RegisterCodeResult>(url, RegisterCodeResult.class), requestListener);
    }

    public static void exeRegisterRequest(SpiceManager manager, RequestListener<RegisterResult> requestListener, String phone, String password) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.register(phone, password);
        L.d("register -> %s", url.toString());
        manager.execute(new SpicePostRequest<RegisterResult>(url, RegisterResult.class), requestListener);
    }

    public static void exeLoginRequest(SpiceManager manager, RequestListener<LoginResult> requestListener, String phone, String password) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.login(phone, password);
        L.d("login -> %s", url.toString());
        manager.execute(new SpicePostRequest<LoginResult>(url, LoginResult.class), requestListener);
    }

    public static void exeUserDetailRequest(SpiceManager manager, RequestListener<UserDetailResult> requestListener, String userId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.userDetail(userId);
        L.d("user detail -> %s", url.toString());
        manager.execute(new SpiceGetRequest<UserDetailResult>(url, UserDetailResult.class), requestListener);
    }

    public static void exeFoodTypeRequest(SpiceManager manager, RequestListener<FoodTypesResult> requestListener) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.foodType();
        L.d("food type -> %s", url.toString());
        manager.execute(new SpicePostRequest<FoodTypesResult>(url, FoodTypesResult.class), requestListener);
    }

    public static void exeFoodsRequest(SpiceManager manager, RequestListener<FoodsResult> requestListener, String foodTypeId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.foods(foodTypeId);
        L.d("foods -> %s", url.toString());
        SpiceGetRequest request = new SpiceGetRequest<FoodsResult>(url, FoodsResult.class);
        CachedSpiceRequest cachedRequest = CachedRequestHelper.buildCacheOneMinute(request, "foods_" + foodTypeId);
        manager.execute(cachedRequest, requestListener);
    }

    public static void exeFoodDetailRequest(SpiceManager manager, RequestListener<FoodDetailResult> requestListener,
                                            String foodId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.foodInfo(foodId);
        L.d("food detail -> %s", url.toString());
        manager.execute(new SpiceGetRequest<FoodDetailResult>(url, FoodDetailResult.class), requestListener);
    }

    public static void exeFoodDiyDetailRequest(SpiceManager manager, RequestListener<FoodDiyDetailResult> requestListener,
                                               String foodDiyId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.foodDiyInfo(foodDiyId);
        L.d("food diy detail -> %s", url.toString());
        manager.execute(new SpiceGetRequest<FoodDiyDetailResult>(url, FoodDiyDetailResult.class), requestListener);
    }

    public static void exeMyOrderRequest(SpiceManager manager, RequestListener<MyOrderResult> requestListener,
                                         String orderState, String orderId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.myOrder(2000, orderState, orderId);
        L.d("my order -> %s", url.toString());
        SpiceGetRequest request = new SpiceGetRequest<MyOrderResult>(url, MyOrderResult.class);
//        CachedSpiceRequest cachedRequest = CachedRequestHelper.buildCacheOneMinute(request, "orderState_" + orderState);
        manager.execute(request, requestListener);
    }

    public static void exeMyOrderDetailRequest(SpiceManager manager, RequestListener<MyOrderDetailResult> requestListener,
                                               String orderId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.myOrderDetail(orderId);
        L.d("My Order Detail -> %s", url.toString());
        manager.execute(new SpiceGetRequest<MyOrderDetailResult>(url, MyOrderDetailResult.class), requestListener);
    }

    /**
     * @param shopId                 店铺id
     * @param orderOptions           订单选项（0为外送，1为到店下单）
     * @param personalPreferentialId 优惠卷Id
     * @param deliveryAddress        配送地址
     * @param foods                  订购的食物
     * @return
     */
    public static void exeSubmitOrderRequest(final SpiceManager manager, final RequestListener<EmptyResults> requestListener,
                                             final boolean isSubscribe, final int shopId, final int orderOptions, final String personalPreferentialId
            , final String deliveryAddress, final List<FoodsResult.Food> foods, final String realName,final String phone,final String orderTime) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        exeIsTimeRequest(manager, new SimpleRequestListener<EmptyResults>() {
            @Override
            public void onRequestSuccess(EmptyResults emptyResults) {
                if (emptyResults.isSuccess()) {
                    GenericUrl url = UrlBuilder.submitOrder(isSubscribe, shopId, orderOptions, personalPreferentialId, deliveryAddress, foods,realName,phone,orderTime);
                    L.d("submit order-> %s", url.toString());
                    manager.execute(new SpiceGetRequest<EmptyResults>(url, EmptyResults.class), requestListener);
                } else {
                    ToastUtil.showLongTimeMsg("非营业时间，无法下单!");
                }
            }
        });

    }

    public static void exeMyAddressRequest(SpiceManager manager, RequestListener<MyAddressResult> requestListener) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.myAddress();
        L.d("my address -> %s", url.toString());
        manager.execute(new SpiceGetRequest<MyAddressResult>(url, MyAddressResult.class), requestListener);
    }

    public static void exeMyAddressDefaultRequest(SpiceManager manager, RequestListener<MyAddressDefaultResult> requestListener) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.myAddressDefault();
        L.d("my address default-> %s", url.toString());
        manager.execute(new SpiceGetRequest<MyAddressDefaultResult>(url, MyAddressDefaultResult.class), requestListener);
    }

    public static void exeHelpRequest(SpiceManager manager, RequestListener<HelpResult> requestListener,
                                      int pageId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.help(pageId);
        L.d("help -> %s", url.toString());
        manager.execute(new SpiceGetRequest<HelpResult>(url, HelpResult.class), requestListener);
    }

    public static void exeFeedbackRequest(SpiceManager manager, RequestListener<EmptyResult> requestListener,
                                          String content, String email) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.feedback(content, email);
        L.d("feedback -> %s", url.toString());
        manager.execute(new SpiceGetRequest<EmptyResult>(url, EmptyResult.class), requestListener);
    }

    public static void exeMyPreferentialRequest(SpiceManager manager, RequestListener<MyPreferentialResult> requestListener,
                                                int type, String pid) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.myPreferential(type, pid);
        L.d("my preferential -> %s", url.toString());
        manager.execute(new SpicePostRequest<MyPreferentialResult>(url, MyPreferentialResult.class), requestListener);
    }

    public static void exeGetAllDistributionAreaRequest(SpiceManager manager, RequestListener<CityResults> requestListener
    ) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.getAllDistributionArea();
        L.d("GetAllDistributionArea -> %s", url.toString());
        manager.execute(new SpicePostRequest<CityResults>(url, CityResults.class), requestListener);
    }

    public static void exeAddAddressRequest(SpiceManager manager, RequestListener<EmptyResult> requestListener
            , String city, String area, String road,
                                            String address, boolean isDefault,
                                            String userName, boolean isMan, String phone) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.addAddress(city, area, road, address, isDefault, userName, isMan, phone);
        L.d("add address -> %s", url.toString());
        manager.execute(new SpicePostRequest<EmptyResult>(url, EmptyResult.class), requestListener);
    }

    public static void exeMyMessageRequest(SpiceManager manager, RequestListener<MyMessageResults> requestListener
    ) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.myMessage();
        L.d("my message -> %s", url.toString());
        manager.execute(new SpiceGetRequest<MyMessageResults>(url, MyMessageResults.class), requestListener);
    }

    public static void exeIsTimeRequest(SpiceManager manager, RequestListener<EmptyResults> requestListener
    ) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.isTime();
        L.d("is time -> %s", url.toString());
        manager.execute(new SpiceGetRequest<EmptyResults>(url, EmptyResults.class), requestListener);
    }

    public static void exeOrderCommentRequest(SpiceManager manager, RequestListener<EmptyResults> requestListener
            , String orderId, int attitude, int taste, int speed, String evaluation) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.myOrderComment(orderId, attitude, taste, speed, evaluation);
        L.d("order comment -> %s", url.toString());
        manager.execute(new SpiceGetRequest<EmptyResults>(url, EmptyResults.class), requestListener);
    }

    public static void exeOrderByXYRequest(SpiceManager manager, RequestListener<OrderByXYResult> requestListener
            , String orderId) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.orderByXY(orderId);
        L.d("order by xy -> %s", url.toString());
        manager.execute(new SpiceGetRequest<OrderByXYResult>(url, OrderByXYResult.class), requestListener);
    }

    public static void exeShopIdRequest(SpiceManager manager, RequestListener<ShopIdResult> requestListener
            , String city, String area, String road) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.shopId(city, area, road);
        L.d("shop id -> %s", url.toString());
        manager.execute(new SpiceGetRequest<ShopIdResult>(url, ShopIdResult.class), requestListener);
    }

    public static void exeShopLocationRequest(SpiceManager manager, RequestListener<ShopLocationResults> requestListener
    ) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.shopLocation();
        L.d("shop location -> %s", url.toString());
        manager.execute(new SpiceGetRequest<ShopLocationResults>(url, ShopLocationResults.class), requestListener);
    }

    public static void exeAddDIYRequest(SpiceManager manager, RequestListener<EmptyResult> requestListener
            , String foodDiyId,
                                        List<FoodDiyDetailResult.FoodDiyDetail.FoodDiyType.FoodDiyMaterial> foodDiyMaterial
    ) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.addDIY(foodDiyId, foodDiyMaterial);
        L.d("add DIY -> %s", url.toString());
        manager.execute(new SpiceGetRequest<EmptyResult>(url, EmptyResult.class), requestListener);
    }

    public static void exeShopRoadRequest(SpiceManager manager, RequestListener<ShopRoadResults> requestListener,
                                          String city, String area, String find
    ) {
        if (!checkSpiceManagerNotNull(manager)) {
            return;
        }
        GenericUrl url = UrlBuilder.shopRoad(city, area, find);
        L.d("shop Road -> %s", url.toString());
        manager.execute(new SpiceGetRequest<ShopRoadResults>(url, ShopRoadResults.class), requestListener);
    }

    private static boolean checkSpiceManagerNotNull(SpiceManager manager) {
        if (manager == null) {
            L.e("SpiceManager is null ! can't execute request !");
            return false;
        }
        return true;
    }
}
