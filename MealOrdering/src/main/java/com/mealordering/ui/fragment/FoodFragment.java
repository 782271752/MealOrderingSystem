package com.mealordering.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.FoodClassifyItemBuilder;
import com.mealordering.adapter.item.FoodItemBuilder;
import com.mealordering.adapter.item.ViewItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.FoodTypesResult;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.UpdateUtil;
import com.mealordering.utils.ViewUtil;

import java.util.List;

import butterknife.InjectView;

public class FoodFragment extends BaseFragment implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener {
    @InjectView(R.id.food_types_lv)
    ListView mFoodTypesLv;
    @InjectView(R.id.food_foods_lv)
    PullToRefreshListView mFoodsLv;
    private List<FoodTypesResult.FoodType> mFoodTypes;
    private List<FoodsResult.Food> mFoods;
    //    private List<FoodItem> mFoodsDIY;
    private TemplateAdapter<FoodTypesResult.FoodType> mFoodTypeAdapter;
    private FoodsAdapter mFoodsAdapter;

    public static FoodFragment newInstance() {
        FoodFragment fragment = new FoodFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_food, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFoodsAdapter = new FoodsAdapter(new FoodItemBuilder(getActivity()));
        mFoodsLv.setAdapter(mFoodsAdapter);
        mFoodsLv.setOnRefreshListener(this);
        mFoodTypeAdapter = new TemplateAdapter<FoodTypesResult.FoodType>(new FoodClassifyItemBuilder());
        mFoodTypesLv.setAdapter(mFoodTypeAdapter);
        mFoodsLv.setOnItemClickListener(this);
        mFoodTypesLv.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFoodTypes == null || mFoodTypes.size() == 0) {
            RequestHelper.exeFoodTypeRequest(getSpiceManager(), new FoodTypeRequestListener());
        } else {
            mFoodTypeAdapter.setList(mFoodTypes);
            String foodTypeId = mFoodTypes.get(mFoodTypesLv.getCheckedItemPosition()).getFoodTypeId();
            if (mFoods == null || mFoods.isEmpty()) {
                RequestHelper.exeFoodsRequest(getSpiceManager(), new FoodsRequestListener(), foodTypeId);
            } else {
                mFoodsAdapter.setList(mFoods);
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.food_types_lv:
                FoodTypesResult.FoodType foodType = (FoodTypesResult.FoodType) mFoodTypesLv.getItemAtPosition(position);
//                ToastUtil.showShortTimeMsg("分类 -> " + classify);
//                if (foodType.getFoodTypeName().equals("DIY")) {
//                    mFoodsAdapter.setList(mFoodsDIY);
//                } else {
                RequestHelper.exeFoodsRequest(getSpiceManager(), new FoodsRequestListener(), foodType.getFoodTypeId());
//                }
                mFoodTypesLv.setItemChecked(position, true);
                break;
            case android.R.id.list:
                FoodsResult.Food food = (FoodsResult.Food) mFoodsLv.getRefreshableView().getItemAtPosition(position);
                if (food.getA() == 1) {
                    Intents.launchFoodDIYDetail(getActivity(), food);
                } else {
                    Intents.launchFoodDetail(getActivity(), food);
                }
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        FoodTypesResult.FoodType foodType = (FoodTypesResult.FoodType) mFoodTypesLv.getItemAtPosition(mFoodTypesLv.getCheckedItemPosition());
        if (foodType != null)
            RequestHelper.exeFoodsRequest(getSpiceManager(), new FoodsRequestListener(), foodType.getFoodTypeId());
    }

    private class FoodsRequestListener extends SimpleRequestListener<FoodsResult> {
        FoodsRequestListener() {
            super(ViewUtil.findView(getView(), R.id.loading_pb));
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mFoodsLv.setRefreshing();
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mFoodsLv.onRefreshComplete();
        }

        @Override
        public void onRequestSuccess(FoodsResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                mFoods = result.getData();
                mFoodsAdapter.setList(mFoods);
                String foodTypeId = mFoodTypes.get(mFoodTypesLv.getCheckedItemPosition()).getFoodTypeId();
                mFoodsAdapter.setRecommend(foodTypeId.equals("00"));
            }
        }


    }

    private class FoodTypeRequestListener extends SimpleRequestListener<FoodTypesResult> {
        FoodTypeRequestListener() {
            super(ViewUtil.findView(getView(), R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(FoodTypesResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                if (mFoodTypes != null) {
                    mFoodTypes.clear();
                }
                mFoodTypes = result.getData();
                mFoodTypes.add(0, new FoodTypesResult.FoodType("00", "推荐"));
                mFoodTypes.add(new FoodTypesResult.FoodType("01", "DIY"));
                mFoodTypeAdapter.setList(mFoodTypes);
                if (mFoodTypeAdapter.getCount() > 0) {
                    mFoodTypesLv.setItemChecked(0, true);
                    RequestHelper.exeFoodsRequest(getSpiceManager(), new FoodsRequestListener(), mFoodTypes.get(0).getFoodTypeId());
                }
            }
        }
    }

    public class FoodsAdapter extends TemplateAdapter<FoodsResult.Food> {
        private boolean isRecommend;

        public FoodsAdapter(ViewItemBuilder<FoodsResult.Food> builder) {
            super(builder);
        }

        public boolean isRecommend() {
            return isRecommend;
        }

        public void setRecommend(boolean isRecommend) {
            this.isRecommend = isRecommend;
        }
    }
}
