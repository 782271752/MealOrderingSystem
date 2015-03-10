package com.mealordering.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.util.Lists;
import com.mealordering.AppContext;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.TextItemBuilder;
import com.mealordering.bean.TextItem;
import com.mealordering.net.model.VersionEntity;
import com.mealordering.ui.AboutUsActivity;
import com.mealordering.ui.HomeActivity;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.UpdateUtil;
import com.mealordering.utils.ViewUtil;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMServiceFactory;
import com.mealordering.utils.SocializeConfigDemo;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.CircleShareContent;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;

import java.util.List;

import butterknife.InjectView;

public class MoreFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final int ID_SHARE_APP = 0xff1;
    private static final int ID_HELP = 0xff2;
    private static final int ID_ABOUT = 0xff3;
    private static final int ID_FEEDBACK = 0xff4;
    private static final int ID_UPDATE= 0xff5;
    private static final int ID_ABOUT_US=0xff6;
    private HomeActivity activity;

    @InjectView(R.id.list_lv)
    ListView mMoreLv;
    private List<TextItem> mMoreItems;


    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=(HomeActivity)getActivity();
        mMoreItems = Lists.newArrayList();
        mMoreItems.add(new TextItem(ID_SHARE_APP, getString(R.string.more_share_app)));
        mMoreItems.add(new TextItem(ID_HELP, getString(R.string.more_help)));
        mMoreItems.add(new TextItem(ID_ABOUT, getString(R.string.more_about)));
        mMoreItems.add(new TextItem(ID_FEEDBACK, getString(R.string.more_feedback)));
        mMoreItems.add(new TextItem(ID_UPDATE,getString(R.string.more_update)));
        mMoreItems.add(new TextItem(ID_ABOUT_US,"关于"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_more, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMoreLv.setAdapter(new TemplateAdapter<TextItem>(new TextItemBuilder(), mMoreItems));
        mMoreLv.setOnItemClickListener(this);
        ViewUtil.findViewAndClick(getView(), R.id.more_shop_tv, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextItem moreItem = (TextItem) parent.getItemAtPosition(position);
        switch (moreItem.id) {
            case ID_SHARE_APP:
                activity.addWXPlatform();
                activity.addQQPlatform();
                activity.mController.getConfig().setSsoHandler(new SinaSsoHandler());
                activity.mController.setShareContent("肯爸宅急送");
                activity.mController.openShare(getActivity(), false);
                break;
            case ID_HELP:
                Intents.launchHelp(getActivity());
                break;
            case ID_ABOUT:
                Intents.launchAbout(getActivity());
                break;
            case ID_FEEDBACK:
                if (AppContext.getInstance().isLogin()) {
                    Intents.launchFeedback(getActivity());
                } else {
                    ToastUtil.showShortTimeMsg("请先登录.");
                }
                break;

            case ID_UPDATE:
                if (UpdateUtil.checkNetwork(getActivity())){
                    UpdateUtil.showWaitDialog(getActivity());
                    UpdateUtil.updateVersionAsync(getActivity(),false);
                }else{
                    ToastUtil.showLongTimeMsg("网络错误");
                }
                break;
            case ID_ABOUT_US:
                Intent intent=new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_shop_tv:
                Intents.launchShopLocation(getActivity());
                break;
        }
    }






}
