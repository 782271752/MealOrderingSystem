package com.mealordering.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.ViewItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.MyPreferentialResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;
import com.octo.android.robospice.SpiceManager;

import java.util.List;

/**
 * Created by Anthonit on 2014/6/4 0004.
 */
public class PreferentialDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    List<MyPreferentialResult.MyPreferential> myPreferentials;
    SpiceManager spiceManager;
    TemplateAdapter<MyPreferentialResult.MyPreferential> adapter;
    private View checkedView;
    private int checkedPosition = -1;
    private View contentView;

    private OnPreferentialCheckedListener onPreferentialCheckedListener;

    protected PreferentialDialogFragment(SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
    }

    public static PreferentialDialogFragment instance(SpiceManager spiceManager) {
        PreferentialDialogFragment dialogFragment = new PreferentialDialogFragment(spiceManager);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.NoFrameDialog);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        View view = contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_choose_preferential, null);
        GridView gridView = ViewUtil.findView(view, R.id.preferential_dialog_grid_view);
        gridView.setOnItemClickListener(this);
        adapter = new TemplateAdapter<MyPreferentialResult.MyPreferential>(
                new PreferentialDialogItemBuilder());
        gridView.setAdapter(adapter);
        dialog.setContentView(view);

        ViewUtil.findViewAndClick(view, R.id.btn_ok, this);
        ViewUtil.findViewAndClick(view, R.id.btn_cancel, this);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RequestHelper.exeMyPreferentialRequest(spiceManager, new MyPreferentialRequestListener(), 0, String.valueOf(0));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (onPreferentialCheckedListener != null) {
                    onPreferentialCheckedListener.onPreferentialChecked(
                            checkedPosition == -1 ? null : myPreferentials.get(checkedPosition));
                }
                break;
            case R.id.btn_cancel:
                break;
        }
        dismiss();
    }

    public void setOnPreferentialCheckedListener(OnPreferentialCheckedListener onPreferentialCheckedListener) {
        this.onPreferentialCheckedListener = onPreferentialCheckedListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (checkedView != null) {
            ImageView checkedIcon = ViewUtil.findView(checkedView, R.id.preferential_dialog_checked_iv);
            ViewUtil.setInvisible(checkedIcon, true);
        }

        boolean isChecked = view.getTag() == null ? false : (Boolean) view.getTag();
        isChecked = !isChecked;
        ImageView checkedIcon = ViewUtil.findView(view, R.id.preferential_dialog_checked_iv);

        if (checkedPosition!=position){
            ViewUtil.setInvisible(checkedIcon, false);
            view.setTag(isChecked);

            checkedView = view;
            checkedPosition = position;
        }else{
            view.setTag(isChecked);

            checkedView = view;
            checkedPosition = -1;
        }



    }

    public interface OnPreferentialCheckedListener {
        void onPreferentialChecked(MyPreferentialResult.MyPreferential preferential);
    }

    private class PreferentialDialogItemBuilder implements ViewItemBuilder<MyPreferentialResult.MyPreferential> {
        @Override
        public View createView(LayoutInflater inflater) {
            return inflater.inflate(R.layout.list_item_perferential_dialog, null);
        }

        @Override
        public void populateView(TemplateAdapter<MyPreferentialResult.MyPreferential> adapter, View view, int position, MyPreferentialResult.MyPreferential item) {
            ImageView icon = ViewUtil.findView(view, R.id.preferential_dialog_icon_iv);
            ViewUtil.loadImage(icon, item.getImg());
        }
    }

    private class MyPreferentialRequestListener extends SimpleRequestListener<MyPreferentialResult> {
        public MyPreferentialRequestListener() {
            super(ViewUtil.findView(contentView, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyPreferentialResult result) {
            super.onRequestSuccess(result);
            if (result.getData() == null || result.getData().isEmpty()) {
                ToastUtil.showLongTimeMsg("对不起,没有可用的优惠卷.");
            }
            if (result.isSuccess()) {
                myPreferentials = result.getData();
                adapter.setList(result.getData());
            }
        }
    }
}
