package com.mealordering.ui;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mealordering.AppContext;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.MyAddressItemBuilder;
import com.mealordering.adapter.item.OrderConfirmItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.listener.SubmitOrderRequestListener;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.net.model.MyAddressResult;
import com.mealordering.net.model.ShopIdResult;
import com.mealordering.net.model.UserDetailResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;
import android.os.Handler;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OrderConfirmActivity extends TitleSubActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @InjectView(R.id.order_confirm_total_amount_tv)
    TextView mTotalAmountTv;
    @InjectView(R.id.order_confirm_total_cast_tv)
    TextView mTotalCastTv;
    @InjectView(R.id.list_lv)
    ListView mOrderConfirmListLv;
    @InjectView(R.id.order_confirm_name_tv)
    TextView mConfirmNameTv;
    @InjectView(R.id.order_confirm_phone_tv)
    TextView mConfirmPhoneTv;
    @InjectView(R.id.order_confirm_address_sp)
    Spinner mConfirmAddressSp;
    boolean isSubscribe;
    int mOrderOptions;
    private List<FoodsResult.Food> mOrderFoodItems;
    private UserDetailResult.UserDetail mUserDetail;
    private List<MyAddressResult.MyAddress> myAddresses;
    private TemplateAdapter<MyAddressResult.MyAddress> mAdapter;
    private ShopIdResult.ShopId mShopId;
    private String mPersonalPreferentialId;
    private String name,phone;
    private String orderTime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.inject(this);
        mUserDetail = AppContext.getInstance().getUserDetail();

//        ViewUtil.setText(mConfirmNameTv, mUserDetail.getRealName());
//        ViewUtil.setText(mConfirmPhoneTv, mUserDetail.getPhone());

        mAdapter = new TemplateAdapter<MyAddressResult.MyAddress>(new MyAddressItemBuilder(false,false));
        mConfirmAddressSp.setAdapter(mAdapter);
        mConfirmAddressSp.setOnItemSelectedListener(this);

        mPersonalPreferentialId = getIntent().getStringExtra(Intents.ID);
        mOrderFoodItems = (List<FoodsResult.Food>) getIntent().getSerializableExtra(Intents.ITEMS);
        isSubscribe = getIntent().getBooleanExtra(Intents.IS_SUBSCRIBE, false);
        mOrderOptions = getIntent().getIntExtra(Intents.ORDER_OPTIONS, 0);
        orderTime=getIntent().getStringExtra(Intents.ORDER_SUBSCRIBE_TIME);
        Log.v("---time--",orderTime);

        ViewUtil.findViewAndClick(this, R.id.order_confirm_add_address_btn, this);
        ViewUtil.findViewAndClick(this, R.id.btn_ok, this);
        BaseAdapter adapter = new TemplateAdapter<FoodsResult.Food>(new OrderConfirmItemBuilder(), mOrderFoodItems);
        mOrderConfirmListLv.setAdapter(adapter);
        updateTotalAmountPrice();

    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestHelper.exeMyAddressRequest(getSpiceManager(), new AddressRequestListener());
    }

    private void updateTotalAmountPrice() {
        float totalCost = 0;
        int totalAmount = 0;
        for (FoodsResult.Food item : mOrderFoodItems) {
            totalAmount += item.getAmount();
            totalCost += (item.getAmount() * item.getPrice());
        }

        ViewUtil.setText(mTotalAmountTv, getString(R.string.buy_amount, totalAmount));
        ViewUtil.setText(mTotalCastTv, getString(R.string.money, totalCost));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_confirm_add_address_btn:
                Intents.launchMyAddressAdd(this);
                break;
            case R.id.btn_ok:
                if (mShopId == null) {
                    ToastUtil.showLongTimeMsg("无法获取店铺ID,请重新选择地址!");
                    break;
                }
                // 订单选项（0为外送，1为到店下单）
                int orderOptions = mOrderOptions;
                String personalPreferentialId = mPersonalPreferentialId;
                MyAddressResult.MyAddress myAddress = (MyAddressResult.MyAddress) mConfirmAddressSp.getSelectedItem();
                String deliveryAddress = myAddress.getFullAddress();

                RequestHelper.exeSubmitOrderRequest(getSpiceManager(),
                        new SubmitOrderRequestListener(this, true, mShopId, isSubscribe, orderOptions),
                        isSubscribe, mShopId.getShopId(), orderOptions, personalPreferentialId, deliveryAddress, mOrderFoodItems,mConfirmNameTv.getText().toString(),
                        mConfirmPhoneTv.getText().toString(),orderTime);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MyAddressResult.MyAddress myAddress = (MyAddressResult.MyAddress) parent.getItemAtPosition(position);
        RequestHelper.exeShopIdRequest(getSpiceManager(), new ShopIdRequestListener(), myAddress.getCity(), myAddress.getArea(), myAddress.getRoad());
        name=myAddress.getUserName();
        phone=myAddress.getUserPhone();
        Message message=new Message();
        message.what=0;
        mHandler.sendMessage(message);
    }

    private Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:

                    if (name!=null&&phone!=null){
                        ViewUtil.setText(mConfirmNameTv, name);
                        ViewUtil.setText(mConfirmPhoneTv, phone);
                    }

                    break;
            }
        }
    };

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class AddressRequestListener extends SimpleRequestListener<MyAddressResult> {
        public AddressRequestListener() {
            super(ViewUtil.findView(OrderConfirmActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyAddressResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                myAddresses = result.getData();
                mAdapter.setList(myAddresses);
                mConfirmAddressSp.setAdapter(mAdapter);
                mConfirmAddressSp.setSelection(0);

            }
        }
    }

    private class ShopIdRequestListener extends SimpleRequestListener<ShopIdResult> {
        public ShopIdRequestListener() {
            super(ViewUtil.findView(OrderConfirmActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mShopId = null;
        }

        @Override
        public void onRequestSuccess(ShopIdResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                if (result.getData().getDatetimes() == 0) {
                    return;
                }
                mShopId = result.getData();
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }
    }


}
