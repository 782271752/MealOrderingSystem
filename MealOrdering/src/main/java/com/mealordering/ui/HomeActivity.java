package com.mealordering.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.mealordering.AppContext;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.ui.fragment.FoodFragment;
import com.mealordering.ui.fragment.MoreFragment;
import com.mealordering.ui.fragment.PersonalInformationFragment;
import com.mealordering.ui.fragment.ShopCartFragment;
import com.mealordering.utils.DClickExit;
import com.mealordering.utils.SocializeConfigDemo;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.UpdateUtil;
import com.mealordering.utils.ViewUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.CircleShareContent;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 内容区域的ID
     */
    private final int CONTENT_ID = R.id.home_content_layout;
    private SparseArray<Fragment> mCacheFragments;
    private Fragment mCurrentFragment;
    private DClickExit mDClickExit;
    private CheckedTextView mLastCheckedCtv;
    // 整个平台的Controller, 负责管理整个SDK的配置、操作等处理
    public UMSocialService mController = UMServiceFactory.getUMSocialService(
            SocializeConfigDemo.DESCRIPTOR, RequestType.SOCIAL);

    private static final String SHARE_CONTENT="给你推荐肯爸宅急送手机订餐客户端：便捷订餐、DIY餐品随意配！\n";
    private static final String SHARE_TITLE="肯爸宅急送";
    private static final String SHARE_URL="http://app.qq.com/#id=detail&appid=1101482475";
    private static final String SHARE_CONTENT_IMAGE_URL="http://url.cn/Nlw1eY";

    /**
     * @功能描述 : 自定义平台排序,.分享平台会按照参数传递的顺序来排列, 如果没有指定顺序，则默认排序
     */
    private void setPlatformOrder() {
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,SHARE_MEDIA.TENCENT,SHARE_MEDIA.QZONE,SHARE_MEDIA.SMS);
        mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);

//		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,SHARE_MEDIA.TENCENT,SHARE_MEDIA.QZONE,SHARE_MEDIA.SMS);
    }

    /**
     * @Title: configSso
     * @Description: 配置sso授权Handler
     * @return void
     * @throws
     */
    private void configSso() {
        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        mController.getConfig().supportQQPlatform(this, "100424468","c7394704798a158208a74ab60104f0ba",
                SHARE_URL);
        mController.getConfig().setSsoHandler(
                new QZoneSsoHandler(this,"100424468","c7394704798a158208a74ab60104f0ba"));
        mController.setShareContent(SHARE_CONTENT);
        setPlatformOrder();


        UMImage localImage = new UMImage(this, R.drawable.icon);
        UMImage urlImage = new UMImage(this,
                R.drawable.icon);
        UMImage resImage = new UMImage(this, R.drawable.icon);



        WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(SHARE_CONTENT);
        weixinContent.setTitle(SHARE_TITLE);
        weixinContent.setTargetUrl(SHARE_URL);
        weixinContent.setShareImage(new UMImage(this, R.drawable.icon));
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(SHARE_CONTENT);
        circleMedia.setTitle(SHARE_TITLE);
        circleMedia.setShareImage(localImage);
        // circleMedia.setShareMusic(uMusic);
//		circleMedia.setShareVideo(video);
        mController.setShareMedia(circleMedia);


        // 设置renren分享内容
        RenrenShareContent renrenShareContent = new RenrenShareContent();
        renrenShareContent.setShareContent(SHARE_CONTENT);
        UMImage image = new UMImage(this,
                BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        image.setTitle(SHARE_TITLE);
        image.setThumb(SHARE_CONTENT_IMAGE_URL);
        renrenShareContent.setShareImage(image);
        renrenShareContent.setAppWebSite(SHARE_URL);
        mController.setShareMedia(renrenShareContent);

        UMImage qzoneImage = new UMImage(this,
                R.drawable.icon);
        qzoneImage
                .setTargetUrl(SHARE_CONTENT_IMAGE_URL);


        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(SHARE_CONTENT);
        qzone.setTargetUrl(SHARE_URL);
        qzone.setTitle(SHARE_TITLE);
        qzone.setShareImage(urlImage);
        mController.setShareMedia(qzone);


        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(SHARE_CONTENT);
        qqShareContent.setTitle(SHARE_TITLE);
        qqShareContent
                .setShareImage(new UMImage(this, R.drawable.icon));
        qqShareContent.setTargetUrl(SHARE_URL);
        mController.setShareMedia(qqShareContent);



        TencentWbShareContent tencent = new TencentWbShareContent();
        tencent.setShareContent(SHARE_CONTENT );
        // 设置tencent分享内容
        mController.setShareMedia(tencent);

        // 设置邮件分享内容， 如果需要分享图片则只支持本地图片
        MailShareContent mail = new MailShareContent(localImage);
        mail.setTitle(SHARE_TITLE);
        mail.setShareContent(SHARE_CONTENT);
        // 设置tencent分享内容
        mController.setShareMedia(mail);


        SinaShareContent sinaContent = new SinaShareContent(urlImage);
        sinaContent.setShareContent(SHARE_CONTENT);
//		mController.setShareMedia(sinaContent);

        mController.setShareMedia(new UMImage(this,
                R.drawable.icon));

        mController.getConfig().closeSinaSSo();
        // addInstagram();

        // addWXPlatform();
        // addYXPlatform();

    }
    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    public void addWXPlatform() {

        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wxe931eec166542500";
        // 微信图文分享,音乐必须设置一个url
        String contentUrl = SHARE_URL;
        // 添加微信平台
        UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(
                HomeActivity.this, appId, contentUrl);
        wxHandler.setWXTitle(SHARE_TITLE);

        UMImage mUMImgBitmap = new UMImage(this,
                R.drawable.icon);

        // 设置分享文字内容
        mController
                .setShareContent(SHARE_CONTENT);
        // mController.setShareContent(null);
        // 设置分享图片
        mController.setShareMedia(mUMImgBitmap);
        // 支持微信朋友圈
        UMWXHandler circleHandler = mController.getConfig()
                .supportWXCirclePlatform(this, appId, contentUrl);
        circleHandler.setCircleTitle(SHARE_TITLE);


        //
        mController.getConfig().registerListener(new SocializeListeners.SnsPostListener() {

            @Override
            public void onStart() {
//                Toast.makeText(HomeActivity.this, "weixin -- xxxx onStart", Toast.LENGTH_SHORT)
//                        .show();
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode,
                                   SocializeEntity entity) {
//                Toast.makeText(HomeActivity.this, platform + " code = " + eCode, Toast.LENGTH_SHORT)
//                        .show();
            }
        });

    }

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    public void addQQPlatform() {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        mController.getConfig().supportQQPlatform(this, "100424468","c7394704798a158208a74ab60104f0ba",
                SHARE_URL);
        // // 图片分享
        // UMImage mUMImgBitmap = new UMImage(getActivity(),
        // "http://www.umeng.com/images/pic/banner_module_social.png");
        // UMImage mUMImgBitmap = new UMImage(getActivity(),
        // BitmapFactory.decodeResource(
        // getResources(), R.drawable.socialize_banner_module));
        // UMImage mUMImgBitmap = new UMImage(getActivity(),
        // BitmapFactory.decodeFile("/mnt/sdcard/test.jpg"));
        // UMImage mUMImgBitmap = new UMImage(getActivity(), new
        // File("/mnt/sdcard/test1.png"));
        // UMImage mUMImgBitmap = new UMImage(getActivity(), new
        // File("/mnt/sdcard/download.bmp"));
        // UMImage mUMImgBitmap = new UMImage(getActivity(), new
        // File("/mnt/sdcard/test4.jpg"));

        // UMImage mImage = new UMImage(getActivity(), R.drawable.icon);
        UMImage mImage = new UMImage(this, R.drawable.icon);
        mImage.setTitle(SHARE_TITLE);

        // 要分享的文字内容
        mController
                .setShareContent(SHARE_CONTENT);
        // 设置多媒体内容
        mController.setShareMedia(mImage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDClickExit = new DClickExit(this);
        mCacheFragments = new SparseArray<Fragment>(5);
        configSso();
        ViewUtil.findViewAndClick(this, R.id.login_btn, this);
        ViewUtil.findViewAndClick(this, R.id.logout_btn, this);
        onClick(ViewUtil.findView(this, R.id.home_food_btn));

//        ViewUtil.findViewAndClick(this, R.id.home_preferential_btn, this);
        ViewUtil.findViewAndClick(this, R.id.home_food_btn, this);
        ViewUtil.findViewAndClick(this, R.id.home_shop_cart_btn, this);
        ViewUtil.findViewAndClick(this, R.id.home_personal_information_btn, this);
        ViewUtil.findViewAndClick(this, R.id.home_more_btn, this);

        if (UpdateUtil.checkNetwork(this)){
            UpdateUtil.updateVersionAsync(this,true);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mDClickExit.doubleClickExit(keyCode);
    }

    private void updateLoginState() {
        boolean isLogin = AppContext.getInstance().isLogin();
        ViewUtil.setInvisible(ViewUtil.findView(this, R.id.login_btn), isLogin);
        ViewUtil.setInvisible(ViewUtil.findView(this, R.id.logout_btn), !isLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                Intents.launchLogin(this);
                return;
            case R.id.logout_btn:
                AppContext.getInstance().setUserDetail(null);
                updateLoginState();
                ToastUtil.showShortTimeMsg("您已退出登录!");
                return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            fragmentTransaction.detach(mCurrentFragment);
        }
        int checkedId = v.getId();
        Fragment fragment = mCacheFragments.get(checkedId);
        if (fragment == null) {
            switch (checkedId) {
                //餐品
                case R.id.home_food_btn:
                    fragment = FoodFragment.newInstance();
                    break;
                //购物车
                case R.id.home_shop_cart_btn:
                    fragment = ShopCartFragment.newInstance();
                    break;
                //个人信息
                case R.id.home_personal_information_btn:
                    fragment = PersonalInformationFragment.newInstance();
                    break;
                //更多
                case R.id.home_more_btn:
                    fragment = MoreFragment.newInstance();
                    break;
            }
            mCacheFragments.put(checkedId, fragment);
            fragmentTransaction.add(CONTENT_ID, fragment).commit();
        } else {
            fragmentTransaction.attach(fragment).commit();
        }
        mCurrentFragment = fragment;
        if (mLastCheckedCtv != null)
            mLastCheckedCtv.setChecked(false);
        CheckedTextView checked = (CheckedTextView) v;
        checked.setChecked(true);
        mLastCheckedCtv = checked;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
