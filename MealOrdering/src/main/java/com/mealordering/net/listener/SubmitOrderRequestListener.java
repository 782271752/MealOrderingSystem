package com.mealordering.net.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.mealordering.R;
import com.mealordering.db.DBHelper;
import com.mealordering.net.model.EmptyResults;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.net.model.ShopIdResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

public class SubmitOrderRequestListener extends SimpleRequestListener<EmptyResults> {
    Activity activity;
    boolean finishActivity;
    ShopIdResult.ShopId shopId;
    boolean isSubscribe;
    int orderOptions;

    public SubmitOrderRequestListener(Activity activity, boolean finishActivity,
                                      ShopIdResult.ShopId shopId, boolean isSubscribe, int orderOptions) {
        super(ViewUtil.findView(activity, R.id.loading_pb));
        this.activity = activity;
        this.finishActivity = finishActivity;
        this.shopId = shopId;
        this.isSubscribe = isSubscribe;
        this.orderOptions = orderOptions;
    }


    @Override
    public void onRequestSuccess(EmptyResults result) {
        super.onRequestSuccess(result);
        if (result.isSuccess()) {
            RuntimeExceptionDao<FoodsResult.Food, Integer> dao = DBHelper.getInstance().getFoodDao();
            dao.delete(dao.queryForAll());

            //只有当外送才显示Dialog
            if (isSubscribe) {
                ToastUtil.showLongTimeMsg("下单成功，按预约的时间30分钟后送达");
                //预约
                if (orderOptions == 0) {
                    if (finishActivity)
                        activity.finish();
                }
            } else {
                ToastUtil.showLongTimeMsg(R.string.order_success);
                //外送
                if (orderOptions == 0) {
                    showTipDialog();
                }
                //到店
                else if (orderOptions == 1) {
                    if (finishActivity)
                        activity.finish();
                }


            }
        } else if(result.isDefrind()) {
            ToastUtil.showLongTimeMsg("黑名单无法下单");
        }else {
            ToastUtil.showLongTimeMsg(R.string.order_fail);
        }
    }

    private void showTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("提示");
        builder.setMessage(shopId.getDatetimesMsg());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (finishActivity)
                    activity.finish();
            }
        });
        builder.show();
    }
}