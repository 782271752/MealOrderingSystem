package com.mealordering.ui.fragment;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.mealordering.AppContext;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.OrderFoodItemBuilder;
import com.mealordering.db.DBHelper;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.listener.SubmitOrderRequestListener;
import com.mealordering.net.model.EmptyResults;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.net.model.MyAddressDefaultResult;
import com.mealordering.net.model.MyPreferentialResult;
import com.mealordering.net.model.ShopIdResult;
import com.mealordering.net.model.UserDetailResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;
import com.umeng.socom.Log;

import java.util.Date;
import java.util.List;

import butterknife.InjectView;


public class ShopCartFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.shop_cart_list_lv)
    ListView mShopCartLv;
    @InjectView(R.id.shop_cart_settlement_btn)
    Button mSettlementBtn;
    @InjectView(R.id.shop_cart_total_amount_tv)
    TextView mTotalAmountTv;
    @InjectView(R.id.shop_cart_total_cast_tv)
    TextView mTotalCastTv;
    private List<FoodsResult.Food> mOrderFoods;
    private TemplateAdapter<FoodsResult.Food> mAdapter;
    /**
     * 订单的类型
     */
    private int mOrderOptions;
    private String mPersonalPreferentialId = "0";
    private boolean isSubscribe = false;
    private Button selTime;

    private OrderTypeDialog orderTypeDialog;
    SimpleDateFormat dateformat;

    /**
     * 预约时间
     */
    private String time="";
    private String hours="",minutes="";
    private int houri=0,minutei=0;
    public static ShopCartFragment newInstance() {
        ShopCartFragment fragment = new ShopCartFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_cart, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new TemplateAdapter<FoodsResult.Food>(new OrderFoodItemBuilder());
        mShopCartLv.setAdapter(mAdapter);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                updateTotalAmountPrice();
            }
        });
        mSettlementBtn.setOnClickListener(this);
        dateformat=new SimpleDateFormat("hh:mm");

    }

    @Override
    public void onResume() {
        super.onResume();
        RuntimeExceptionDao<FoodsResult.Food, Integer> dao = DBHelper.getInstance().getFoodDao();
        mOrderFoods = dao.queryForAll();
        mAdapter.setList(mOrderFoods);
        updateTotalAmountPrice();
        orderTypeDialog=new OrderTypeDialog(getActivity(),0);
    }

    private void updateTotalAmountPrice() {
        float totalCost = 0;
        int totalAmount = 0;
        for (FoodsResult.Food food : mOrderFoods) {
            totalAmount += food.getAmount();
            totalCost += (food.getAmount() * food.getPrice());
        }

        ViewUtil.setText(mTotalAmountTv, getString(R.string.buy_amount, totalAmount));
        ViewUtil.setText(mTotalCastTv, getString(R.string.money, totalCost));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_cart_settlement_btn:
                if (!AppContext.getInstance().isLogin()) {
                    ToastUtil.showShortTimeMsg("请先登录.");
                    break;
                }
                if (mOrderFoods == null || mOrderFoods.isEmpty()) {
                    ToastUtil.showShortTimeMsg("请选购食物后再下订单!.");
                    break;
                }
//                new OrderTypeDialog(getActivity()).show();
                orderTypeDialog.show();

                break;
        }
    }

    class OrderTypeDialog extends Dialog implements View.OnClickListener {
        RadioGroup orderTypeRg;

//        public OrderTypeDialog(Context context) {
//            super(context, R.style.NoFrameDialog);
//            setCancelable(false);
//            setCanceledOnTouchOutside(false);
//            final View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_order_type, null);
//            setContentView(view);
//            orderTypeRg = ViewUtil.findView(view, R.id.order_type_rg);
//            ViewUtil.findViewAndClick(view, R.id.btn_ok, this);
//            ViewUtil.findViewAndClick(view, R.id.btn_cancel, this);
//            selTime=ViewUtil.findView(view,R.id.order_type_select_time_btn);
//            selTime.setOnClickListener(this);
//
//            orderTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    switch (checkedId){
//                        case R.id.order_type_subscribe_rb:
//                             ViewUtil.setInvisible(selTime,false);
//                            break;
//                        default:
//                             selTime.setVisibility(View.GONE);
//                            break;
//                    }
//                }
//            });
//        }

        public OrderTypeDialog(Context context,int id){


            super(context, R.style.NoFrameDialog);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            final View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_order_type, null);
            setContentView(view);
            orderTypeRg = ViewUtil.findView(view, R.id.order_type_rg);
            ViewUtil.findViewAndClick(view, R.id.btn_ok, this);
            ViewUtil.findViewAndClick(view, R.id.btn_cancel, this);
            RadioButton subSrcibe=ViewUtil.findView(view,R.id.order_type_subscribe_rb);
            selTime=ViewUtil.findView(view,R.id.order_type_select_time_btn);
            selTime.setOnClickListener(this);

            orderTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.order_type_subscribe_rb:
                            ViewUtil.setInvisible(selTime,false);
                            break;
                        default:
                            selTime.setVisibility(View.GONE);
                            break;
                    }
                }
            });
            if (id!=0){
                subSrcibe.setChecked(true);
                selTime.setVisibility(View.VISIBLE);
                selTime.setText(hours+":"+minutes);
            }


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_ok:
                    switch (orderTypeRg.getCheckedRadioButtonId()) {
                        case R.id.order_type_outside_rb:
                            mOrderOptions = 0;
                            isSubscribe = false;
                            break;
                        case R.id.order_type_inside_rb:
                            mOrderOptions = 1;
                            isSubscribe = false;
                            break;
                        case R.id.order_type_subscribe_rb:
                            Log.e("onclik_time",time);
                            if (time.equals("")){
                                ToastUtil.showLongTimeMsg("请选择预约时间");
                                return;
                            }



                            try {
                                Date date=new Date();
                                Log.e("time",houri+":"+minutei+"");
                                date.setHours(houri);
                                date.setMinutes(minutei);
//                                date=dateformat.parse(houri+":"+minutei+"");
                                if(date.compareTo(new Date())==-1){
                                    ToastUtil.showShortTimeMsg("预约时间不能小于当前时间,请重新选择");
                                    return;
                                }

                            }catch (Exception e){
                                Log.e("change_time",e.toString());
                            }

//                            if(houri<new Date().getHours()||minutei<)

                            mOrderOptions = 0;
                            isSubscribe = true;
                            break;
                    }
                    PreferentialDialogFragment dialogFragment = PreferentialDialogFragment.instance(getSpiceManager());
                    dialogFragment.setOnPreferentialCheckedListener(new PreferentialDialogFragment.OnPreferentialCheckedListener() {
                        @Override
                        public void onPreferentialChecked(MyPreferentialResult.MyPreferential preferential) {
                            if (preferential != null)
                                mPersonalPreferentialId = preferential.getPersonalPreferentialId();
                            //判断是否是预约下单 , 执行不同逻辑.
//                            if (isSubscribe) {
//                                if (mOrderOptions == 0) {
//                                    RequestHelper.exeMyAddressDefaultRequest(getSpiceManager(), new MyAddressDefaultRequestListener());
//                                }
//                            } else {
                            if (mOrderOptions == 0) {
                                Log.e("time++++++++++++++++++++++++++++++++++++++++++++++++++",time);
                                Intents.launchOrderConfirm(getContext(), mOrderFoods, mPersonalPreferentialId, isSubscribe, mOrderOptions,time);
                            } else if (mOrderOptions == 1) {
                                RequestHelper.exeMyAddressDefaultRequest(getSpiceManager(), new MyAddressDefaultRequestListener());
                            }
                        }

//                        }
                    });
                    dialogFragment.show(getFragmentManager(), "");
//                    time="";
                    break;
                case R.id.btn_cancel:
//                    time="";
                    break;

                case R.id.order_type_select_time_btn:
                    new TimePickerDialog(getActivity(),mTimeSetListener,new Date().getHours(),new Date().getMinutes(),true).show();
                   break;
            }
            dismiss();


        }
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {



        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time=hourOfDay+"-"+minute+"";
            hours=hourOfDay+"";

            houri=hourOfDay;
            minutei=minute;




//            Date date=new Date();
//            date.setHours(hourOfDay);
//            date.setMinutes(minute);
//
//            if (date.before(new Date())){
//                ToastUtil.showShortTimeMsg("预约时间不能小于当前时间,请重新选择");
//            }

            if(minute>=0&&minute<=9){
                minutes="0"+minute+"";
            }else{
                minutes=minute+"";
            }
//            selTime.setText(time);

            if (orderTypeDialog.isShowing()){
                orderTypeDialog.show();

            }else{
                orderTypeDialog=new OrderTypeDialog(getActivity(),1);
                orderTypeDialog.show();

            }

        }
    };

    private class MyAddressDefaultRequestListener extends SimpleRequestListener<MyAddressDefaultResult> {
        private MyAddressDefaultRequestListener() {
            super(ViewUtil.findView(getActivity(), R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyAddressDefaultResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                MyAddressDefaultResult.MyAddressDefault myAddress = result.getData();
                RequestHelper.exeShopIdRequest(getSpiceManager(), new ShopIdRequestListener(myAddress), myAddress.getCity(), myAddress.getArea(), myAddress.getRoad());
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }
    }

    private class ShopIdRequestListener extends SimpleRequestListener<ShopIdResult> {
        MyAddressDefaultResult.MyAddressDefault myAddress;

        public ShopIdRequestListener(MyAddressDefaultResult.MyAddressDefault myAddress) {
            super(ViewUtil.findView(getActivity(), R.id.loading_pb));
            this.myAddress = myAddress;
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
        }

        @Override
        public void onRequestSuccess(ShopIdResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                if (result.getData().getDatetimes() == 0) {
                    ToastUtil.showLongTimeMsg(result.getData().getDatetimesMsg());
                    return;
                }
                int shopId = result.getData().getShopId();
                // 订单选项（0为外送，1为到店下单; 0为预约下单-url不一样）
                int orderOptions = mOrderOptions;
                String personalPreferentialId = mPersonalPreferentialId;
                String deliveryAddress = myAddress.getFullAddress();

                RequestHelper.exeSubmitOrderRequest(getSpiceManager(), new SubmitOrderRequestListener(getActivity(), false, result.getData(), isSubscribe, orderOptions) {
                            @Override
                            public void onRequestSuccess(EmptyResults result) {
                                super.onRequestSuccess(result);
                                onResume();
                            }
                        },
                        isSubscribe, shopId, orderOptions, personalPreferentialId, deliveryAddress, mOrderFoods,AppContext.getInstance().getUserDetail().getRealName(),
                        AppContext.getInstance().getUserDetail().getPhone(),time
                );
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }
    }
}
