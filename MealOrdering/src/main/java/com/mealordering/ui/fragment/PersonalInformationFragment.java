package com.mealordering.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.api.client.util.Lists;
import com.mealordering.AppContext;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.TextItemBuilder;
import com.mealordering.bean.TextItem;
import com.mealordering.utils.ToastUtil;

import java.util.List;

import butterknife.InjectView;


public class PersonalInformationFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final int ID_MESSAGE = 0xf1;
    private static final int ID_COUPONS = 0xf2;
    private static final int ID_ORDER = 0xf3;
    private static final int ID_ADDRESS = 0xf4;
    @InjectView(R.id.list_lv)
    ListView mPersonalLv;
    private List<TextItem> mPersonalItems;

    public static PersonalInformationFragment newInstance() {
        PersonalInformationFragment fragment = new PersonalInformationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPersonalItems = Lists.newArrayList();
        mPersonalItems.add(new TextItem(ID_ORDER, getString(R.string.personal_order)));
        mPersonalItems.add(new TextItem(ID_COUPONS, getString(R.string.personal_coupons)));
        mPersonalItems.add(new TextItem(ID_MESSAGE, getString(R.string.personal_message)));
        mPersonalItems.add(new TextItem(ID_ADDRESS, getString(R.string.personal_address)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPersonalLv.setAdapter(new TemplateAdapter<TextItem>(new TextItemBuilder(), mPersonalItems));
        mPersonalLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!AppContext.getInstance().isLogin()) {
            ToastUtil.showLongTimeMsg("请先登录!");
            return;
        }
        TextItem personalItem = (TextItem) parent.getItemAtPosition(position);
        switch (personalItem.id) {
            //我的消息
            case ID_MESSAGE:
                Intents.launchMyMessage(getActivity());
                break;
            //我的优惠卷
            case ID_COUPONS:
                Intents.launchMyPreferential(getActivity());
                break;
            //我的订单
            case ID_ORDER:
                Intents.launchMyOrder(getActivity());
                break;
            //默认地址管理
            case ID_ADDRESS:
                Intents.launchMyAddress(getActivity());
                break;
        }
    }

}
