package com.mealordering.adapter.item;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.MyAddressResult;
import com.mealordering.utils.ViewUtil;

/**
 * Created by Anthony on 14-2-21.
 */
public class MyAddressItemBuilder implements ViewItemBuilder<MyAddressResult.MyAddress> {
    private boolean hasDefaultIcon;
    private boolean isMyAddress;

    public MyAddressItemBuilder(boolean hasDefaultIcon,boolean isMyAddress) {

        this.hasDefaultIcon = hasDefaultIcon;
        this.isMyAddress=isMyAddress;
    }

    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_my_address, null);
    }

    @Override
    public void populateView(TemplateAdapter<MyAddressResult.MyAddress> adapter, View view, int position, MyAddressResult.MyAddress item) {
        TextView titleTv = ViewUtil.findView(view, R.id.my_address_title_tv);
        String address = item.getFullAddress();
        address = position == 0 ? address + " [默认]" : address;
        ViewUtil.setText(titleTv, address);

        Drawable leftDrawable = hasDefaultIcon && position == 0 ?
                view.getContext().getResources().getDrawable(R.drawable.icon_my_address_default) : null;
        titleTv.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);

//        if (isMyAddress){
//            Drawable leftDrawable = hasDefaultIcon && position == 0 ?
//                    view.getContext().getResources().getDrawable(R.drawable.icon_my_address_default) : view.getContext().getResources().getDrawable(R.drawable.icon_delete);
//            titleTv.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
//        }else{
//            Drawable leftDrawable = hasDefaultIcon && position == 0 ?
//                    view.getContext().getResources().getDrawable(R.drawable.icon_my_address_default) : null;
//            titleTv.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
//        }



    }
}
