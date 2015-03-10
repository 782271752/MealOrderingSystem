package com.mealordering.net;

import com.google.api.client.http.GenericUrl;
import com.mealordering.AppContext;
import com.mealordering.net.model.FoodDiyDetailResult;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.net.model.UserDetailResult;

import java.util.List;

/**
 * Created by Anthony on 14-3-4.
 */
public class UrlBuilder {

    private static final String SCHEME = "http";
//    private static final String HOST = "59.38.125.114";
    private static final String HOST = "in.kebabar.cn";

    private static final int PORT = 8067;
    /**
     * 获取验证码
     */
    private static final String GET_CODE = "/json/GetCode.aspx";
    /**
     * 注册新用户
     */
    private static final String REGISTER = "/json/UserManager/UserAdd.aspx";
    /**
     * 登录
     */
    private static final String LOGIN = "/json/UserManager/Login.aspx";
    /**
     * 美食分类
     */
    private static final String FOOD_TYPE = "/json/food/GetFoodType.aspx";
    /**
     * 美食列表
     */
    private static final String FOODS = "/json/food/GetFood.aspx";
    /**
     * 美食详情
     */
    private static final String FOOD_INFO = "/json/food/GetFoodInfo.aspx";
    private static final String FOOD_DIY_INFO = "/json/food/GetfoodDiy.aspx";
    /**
     * 提交订单
     */
    private static final String SUBMIT_ORDER = "/json/OrderAdd.aspx";
    /**
     * 预约订单
     */
    private static final String SUBMIT_SUBSCRIBE_ORDER = "/json/ConventionOrderAdd.aspx";
    /**
     * 我的订单
     */
    private static final String MY_ORDER = "/json/MyOrder.aspx";
    /**
     * 订单详情
     */
    private static final String MY_ORDER_DETAIL = "/json/OrderView.aspx";
    /**
     * 订单评价
     */
    private static final String MY_ORDER_COMMENT = "/json/OrderEvaluationAdd.aspx";
    /**
     * 我的优惠卷
     */
    private static final String MY_PREFERENTIAL = "/json/UserManager/MyPreferential.aspx";
    /**
     * 使用我的优惠卷
     */
    private static final String MY_PREFERENTIAL_USED = "/json/UserManager/MinusPreferential.aspx";
    /**
     * 我的地址管理
     */
    private static final String MY_ADDRESS = "/json/MyAddress/ViewAddress.aspx";
    /**
     * 修改我的地址管理
     */
    private static final String MY_ADDRESS_EDIT = "/json/MyAddress/UpdateAddress.aspx";
    /**
     * 我的默认地址
     */
    private static final String MY_ADDRESS_DEFAULT = "/json/MyAddress/StartAddress.aspx";
    /**
     * 商铺地址列表
     */
    private static final String SHOP_LOCATION = "/json/MoreOperation/ShopView.aspx";
    /**
     * 用户详细信息
     */
    private static final String USER_DETAIL = "/json/UserManager/GetIdUsers.aspx";
    /**
     * 修改用户详细信息
     */
    private static final String USER_EDIT = "/json/UserManager/UpdateUsers.aspx";
    /**
     * 帮助
     */
    private static final String HELP = "/json/MoreOperation/ViewAnswer.aspx";
    /**
     * 意见反馈
     */
    private static final String FEEDBACK = "/json/MoreOperation/FeedBackAdd.aspx";
    /**
     * 配送地址列表(全部返回)
     */
    private static final String GET_ALL_DISTRIBUTION_AREA = "/json/GetAllDistributionArea.aspx";
    /**
     * 新增我的地址
     */
    private static final String ADD_ADDRESS = "/json/MyAddress/UsersAddressAdd.aspx";
    /**
     * 新增我的地址
     */
    private static final String MY_MESSAGE = "/json/messages/MyMessages.aspx";
    /**
     * 判断是否能正常下单
     */
    private static final String IS_TIME = "/json/IsTime.aspx";
    /**
     * 订单获取派送员位置
     */
    private static final String ORDER_BY_XY = "/json/send/OrderByXY.aspx";
    /**
     * 分店ID
     */
    private static final String SHOP_ID = "/json/MyAddress/WebShopId.aspx";
    /**
     * 保存用户DIY信息
     */
    private static final String ADD_DIY = "/json/myDiy/AddDiy.aspx";
    /**
     * 根据市,区/镇 查找店铺
     */
    private static final String SHOP_ROAD = "/json/MyAddress/ViewShopRoad.aspx";


    private UrlBuilder() {
    }

    private static String getUserId() {
        return AppContext.getInstance().getUserDetail().getUserId();
    }

    public static GenericUrl getCode(String phone) {
        GenericUrl url = buildUrl(GET_CODE);
        url.put("Phone", phone);
        return url;
    }

    public static GenericUrl register(String phone, String password) {
        GenericUrl url = buildUrl(REGISTER);
        url.put("Phone", phone);
        url.put("Password", password);
        return url;
    }

    public static GenericUrl login(String phone, String password) {
        GenericUrl url = buildUrl(LOGIN);
        url.put("Phone", phone);
        url.put("Password", password);
        return url;
    }

    public static GenericUrl foodType() {
        GenericUrl url = buildUrl(FOOD_TYPE);
        return url;
    }

    public static GenericUrl foods(String foodTypeId) {
        GenericUrl url = buildUrl(FOODS);
        url.put("FoodTypeId", foodTypeId);
        return url;
    }

    public static GenericUrl foodInfo(String foodId) {
        GenericUrl url = buildUrl(FOOD_INFO);
        url.put("FoodId", foodId);
        return url;
    }

    public static GenericUrl foodDiyInfo(String foodDiyId) {
        GenericUrl url = buildUrl(FOOD_DIY_INFO);
        url.put("FoodDiyId", foodDiyId);
        return url;
    }

    /**
     * @param isSubscribe            是否 是预约订单
     * @param shopId                 店铺id
     * @param orderOptions           订单选项（0为外送，1为到店下单）
     * @param personalPreferentialId 优惠卷Id
     * @param deliveryAddress        配送地址
     * @param foods                  订购的食物
     * @return
     */
    public static GenericUrl submitOrder(boolean isSubscribe, int shopId, int orderOptions, String personalPreferentialId
            , String deliveryAddress, List<FoodsResult.Food> foods,String realName,String phone,String time) {
        GenericUrl url = buildUrl(isSubscribe ? SUBMIT_SUBSCRIBE_ORDER : SUBMIT_ORDER);
        UserDetailResult.UserDetail userDetail = AppContext.getInstance().getUserDetail();
        url.put("UserId", userDetail.getUserId());
        url.put("ShopId", shopId);
        url.put("PersonalPreferentialId", personalPreferentialId);
        url.put("Coordinates_x", userDetail.getCoordinatesX());
        url.put("Coordinates_y", userDetail.getCoordinatesY());
        url.put("Sex", userDetail.getSex());
        url.put("RealName", realName);
        url.put("Phone", phone);
        url.put("OrderOptions", orderOptions);
        url.put("DeliveryAddress", deliveryAddress);
        url.put("PrepareTime",time);
        // 格式例如：1a1b2,1a2b1,0a1b1 如果多个用半角逗号隔开，
        // 在a前面这个数字表示食物类型1为餐品，0为餐品DIY；
        // 在a跟b之间这个数字为餐品ID或用户餐品DIY；
        // 在b后面这个数字为每件餐品订购的数量
        StringBuilder foodsString = new StringBuilder();
        for (FoodsResult.Food food : foods) {
            foodsString.append(String.format("%sa%sb%s", food.getA(), food.getFoodId(), food.getAmount()));
            foodsString.append(",");
        }
        foodsString.deleteCharAt(foodsString.length() - 1);
        url.put("Foods", foodsString.toString());

        return url;
    }

    public static GenericUrl myOrder(int top, String orderState, String orderId) {
        GenericUrl url = buildUrl(MY_ORDER);
        url.put("Top", top);
        url.put("UserId", getUserId());
        url.put("OrderState", orderState);
        url.put("OrderId", orderId);
        return url;
    }

    public static GenericUrl myOrderDetail(String orderId) {
        GenericUrl url = buildUrl(MY_ORDER_DETAIL);
        url.put("OrderId", orderId);
        return url;
    }

    /**
     * @param orderId    订单ID
     * @param attitude   服务态度（0为待改进，1为一般，2为好评）
     * @param taste      食物口味（0为待改进，1为一般，2为好评）
     * @param speed      送达速度（0为待改进，1为一般，2为好评）
     * @param evaluation 评语
     * @return
     */
    public static GenericUrl myOrderComment(String orderId, int attitude, int taste, int speed, String evaluation) {
        GenericUrl url = buildUrl(MY_ORDER_COMMENT);
        url.put("OrderId", orderId);
        url.put("Attitude", attitude);
        url.put("Taste", taste);
        url.put("Speed", speed);
        url.put("Evaluation", evaluation);
        return url;
    }

    public static GenericUrl myPreferential(int type, String pid) {
        GenericUrl url = buildUrl(MY_PREFERENTIAL);
        url.put("UserId", getUserId());
        url.put("Type", type);
        url.put("Pid", pid);
        return url;
    }

    /**
     * @param preferentialId 优惠券的标示ID
     * @param pNumber        使用数量
     * @return
     */
    public static GenericUrl myPreferentialUsed(String preferentialId, int pNumber) {
        GenericUrl url = buildUrl(MY_PREFERENTIAL_USED);
        url.put("PersonalPreferentialId", preferentialId);
        url.put("Pnumber", pNumber);
        return url;
    }

    public static GenericUrl myAddress() {
        GenericUrl url = buildUrl(MY_ADDRESS);
        url.put("UserId", getUserId());
        return url;
    }

    public static GenericUrl myAddressEdit(String userName, String uSex,
                                           String userPhone, int cityId, String city, String area, String road,
                                           String address, boolean isDefault) {
        GenericUrl url = buildUrl(MY_ADDRESS_EDIT);
        url.put("UserId", getUserId());
        url.put("UserName", userName);
        url.put("USex", uSex);
        url.put("UserPhone", userPhone);
        url.put("City", city);
        url.put("cityId", cityId);
        url.put("Area", area);
        url.put("Road", road);
        url.put("Address", address);
        url.put("IsDefault", isDefault ? 1 : 0);
        return url;
    }

    public static GenericUrl myAddressDefault() {
        GenericUrl url = buildUrl(MY_ADDRESS_DEFAULT);
        url.put("UserId", getUserId());
        return url;
    }

    public static GenericUrl shopLocation() {
        GenericUrl url = buildUrl(SHOP_LOCATION);
        return url;
    }

    public static GenericUrl userDetail(String userId) {
        GenericUrl url = buildUrl(USER_DETAIL);
        url.put("UserId", userId);
        return url;
    }

    public static GenericUrl userEdit(String realName, String password, int sex, int coordinates_x, int coordinates_y) {
        GenericUrl url = buildUrl(USER_EDIT);
        url.put("UserId", getUserId());
        url.put("RealName", realName);
        url.put("Password", password);
        url.put("Sex", sex);
        url.put("Coordinates_x", coordinates_x);
        url.put("Coordinates_y", coordinates_y);
        return url;
    }

    public static GenericUrl help(int pageId) {
        GenericUrl url = buildUrl(HELP);
        url.put("PageId", pageId);
        return url;
    }

    public static GenericUrl feedback(String content, String email) {
        GenericUrl url = buildUrl(FEEDBACK);
        url.put("UserId", getUserId());
        url.put("Contents", content);
        url.put("Email", email);
        return url;
    }

    public static GenericUrl getAllDistributionArea() {
        GenericUrl url = buildUrl(GET_ALL_DISTRIBUTION_AREA);
        return url;
    }

    public static GenericUrl addAddress(String city, String area, String road,
                                        String address, boolean isDefault,
                                        String userName, boolean isMan, String phone) {
        GenericUrl url = buildUrl(ADD_ADDRESS);
        url.put("UserId", getUserId());
        url.put("City", city);
        url.put("Area", area);
        url.put("Road", road);
        url.put("Address", address);
        url.put("IsDefault", isDefault ? 1 : 0);
        url.put("UserName", userName);
        url.put("Usex", isMan ? 1 : 0);
        url.put("UserPhone", phone);
        return url;
    }

    public static GenericUrl myMessage() {
        GenericUrl url = buildUrl(MY_MESSAGE);
        url.put("UserId", getUserId());
        return url;
    }

    public static GenericUrl isTime() {
        GenericUrl url = buildUrl(IS_TIME);
        return url;
    }

    public static GenericUrl orderByXY(String orderId) {
        GenericUrl url = buildUrl(ORDER_BY_XY);
        url.put("OrderId", orderId);
        return url;
    }

    public static GenericUrl shopId(String city, String area, String road) {
        GenericUrl url = buildUrl(SHOP_ID);
        url.put("City", city);
        url.put("Area", area);
        url.put("Road", road);
        return url;
    }

    public static GenericUrl shopRoad(String city, String area, String find) {
        GenericUrl url = buildUrl(SHOP_ROAD);
        url.put("City", city);
        url.put("Area", area);
        url.put("Find", find);
        return url;
    }

    public static GenericUrl addDIY(String foodDiyId,
                                    List<FoodDiyDetailResult.FoodDiyDetail.FoodDiyType.FoodDiyMaterial> foodDiyMaterial) {

        GenericUrl url = buildUrl(ADD_DIY);
        url.put("UserId", getUserId());
        url.put("FoodDiyId", foodDiyId);
//        FoodDiyMaterialId：餐品DIY配料ID（多个ID用逗号隔开,例如：1,2,3）
        StringBuilder foodDiyMaterialIds = new StringBuilder();
        for (int i = 0; i < foodDiyMaterial.size(); i++) {
            foodDiyMaterialIds.append(foodDiyMaterial.get(i).getFoodDiyMaterialId());
            foodDiyMaterialIds.append(",");
        }
        if (foodDiyMaterialIds.length() > 0) {
            foodDiyMaterialIds.deleteCharAt(foodDiyMaterialIds.length() - 1);
        }
        url.put("FoodDiyMaterialId", foodDiyMaterialIds.toString());
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
