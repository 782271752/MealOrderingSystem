package com.mealordering.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.joboevan.push.tool.Consts;
import com.joboevan.push.tool.LBS;
import com.mealordering.Intents;

/**
 * 推送消息接收器
 * <p/>
 * *
 */
public class PushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String action = intent.getAction();
        if (Consts.getACTION_RECEIVER_KEY(context).equals(action)) {
            Bundle bundle = intent.getExtras();
            String key = bundle.getString("key");

            if (Consts.MESSAGE_KEY_CONNECT.equals(key)) {
                // 连接结果
                int value = bundle.getInt("value");

                switch (value) {
                    case Consts.RESULT_Y:
                        Log.d("Log", "连接成功");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_N:
                        Log.d("Log", "连接失败");
                        sendToValue(context, key, value);
                        break;
                }
            } else if (Consts.MESSAGE_KEY_LOGIN.equals(key)) {
                // 登陆结果
                int value = bundle.getInt("value");

                switch (value) {
                    case Consts.RESULT_Y:
                        Log.d("Log", "登陆成功");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_N:
                        Log.d("Log", "登陆失败");
                        sendToValue(context, key, value);
                        break;
                }
            } else if (Consts.MESSAGE_KEY_SETALIAS.equals(key)) {
                int value = bundle.getInt("value");

                switch (value) {
                    case Consts.RESULT_Y:
                        Log.d("Log", "别名设置成功");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_N:
                        Log.d("Log", "别名设置失败");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_I:
                        Log.d("Log", "正在设置别名");
                        sendToValue(context, key, value);
                        break;
                    default:
                        break;
                }
            } else if (Consts.MESSAGE_KEY_CLEANALIAS.equals(key)) {
                int value = bundle.getInt("value");

                switch (value) {
                    case Consts.RESULT_Y:
                        Log.d("Log", "清除别名成功");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_N:
                        Log.d("Log", "清除别名失败");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_I:
                        Log.d("Log", "正在清除别名");
                        sendToValue(context, key, value);
                        break;
                    default:
                        break;
                }
            } else if (Consts.MESSAGE_KEY_SETTAGS.equals(key)) {
                int value = bundle.getInt("value");

                switch (value) {
                    case Consts.RESULT_Y:
                        Log.d("Log", "标签设置成功");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_N:
                        Log.d("Log", "标签设置失败");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_I:
                        Log.d("Log", "正在设置标签");
                        sendToValue(context, key, value);
                        break;
                    default:
                        break;
                }
            } else if (Consts.MESSAGE_KEY_CLEANTAGS.equals(key)) {
                int value = bundle.getInt("value");

                switch (value) {
                    case Consts.RESULT_Y:
                        Log.d("Log", "标签清除成功");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_N:
                        Log.d("Log", "标签清除失败");
                        sendToValue(context, key, value);
                        break;
                    case Consts.RESULT_I:
                        Log.d("Log", "正在清除标签");
                        sendToValue(context, key, value);
                        break;
                    default:
                        break;
                }
            } else if (Consts.MESSAGE_KEY_CUSTOM.equals(key)) {
                // 接收自定义推送信息
                String value = bundle.getString("value");
                String flag = bundle.getString(Consts.MESSAGE_BACK_FLAG);
                Log.w("Log", "推送自定义消息: " + value);
                Log.w("Log", "推送自定义消息id: " + flag);
                sendMessage(context, Consts.MESSAGE_BACK_FLAG, flag); // 发送自定义消息的ID值
                sendMessage(context, key, value);

                //这个方法是推送内部封装生成通知的方法
//				CustomNotification notification = new CustomNotification();
//				notification.createNotification(context);
            } else if (Consts.MESSAGE_KEY_NOTIFICATION.equals(key)) { // 如需在工程中展示通知标题和内容，请设置是否展示通知内容
                String title = bundle.getString(Consts.NOTIFICATION_TITLE);
                String content = bundle.getString(Consts.NOTIFICATION_CONTENT);
                String ext = bundle.getString(Consts.NOTIFICATION_EXT);
                String flag = bundle.getString(Consts.MESSAGE_BACK_FLAG);
                Log.d("Log", "通知标题：" + title);
                Log.d("Log", "通知内容：" + content);
                Log.d("Log", "通知附加字段：" + ext);
                Log.d("Log", "通知消息id：" + flag);
                sendMessage(context, Consts.MESSAGE_BACK_FLAG, flag); // 发送通知的ID值
                sendNotification(context, key, title, content);
            } else if (Consts.MESSAGE_KEY_PUSHSTATECHANGED.equals(key)) { // 客户端与服务端的状态
                int value = bundle.getInt("value");

                switch (value) {
                    case Consts.PUSH_CONNECTSTATE_CONNECTING: // 通道连接
                        Log.d("Log", "推送服务已经连接");
                        //连接状态存储到数据库
                        sendToValue(context, key, value);
                        break;

                    case Consts.PUSH_CONNECTSTATE_DISCONNECT: // 通道断开
                        Log.e("Log", "推送服务已经断开");
                        //连接状态存储到数据库
                        sendToValue(context, key, value);
                        break;
                }
            } else if (Consts.ACTION_RECEIVER_VERSION.equals(key)) {
                int value = bundle.getInt("value");
                sendToValue(context, key, value);

                switch (value) {
                    case Consts.VERSION_LATEST:
                        Log.d("Log", "推送版本是最新版本：可用");
                        break;
                    case Consts.VERSION_OLD_PERMIT:
                        Log.d("Log", "推送版本是老版本：可用");
                        break;
                    case Consts.VERSION_OLD_REFUSE:
                        Log.e("Log", "推送版本是老版本：不可用");
                        break;
                    default:
                        break;
                }
            } else if (Consts.CLIENT_DEVICE_ID.equals(key)) {
                String device_id = bundle.getString("value");
                Log.d("Log", "设备唯一标识：" + device_id);
            }
        } else if (Consts.getActionNotificationOpened(context).equals(action)) {
            Log.d("Log", "用户点击了通知");
            Bundle bundle = intent.getExtras();
            //通知标题
            String title = bundle.getString(Consts.NOTIFICATION_TITLE);
            //通知内容
            String content = bundle.getString(Consts.NOTIFICATION_CONTENT);
            //通知附加字段
            String ext = bundle.getString(Consts.NOTIFICATION_EXT);
            //通知消息flag
            String flag = bundle.getString(Consts.MESSAGE_BACK_FLAG);

            Intents.launchMyMessage(context);
//			Intent i = new Intent(context, TestActivity.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(i);
        } else if (Consts.getActionLbsPush(context).equals(action)) { // 接收到广播，需要上传经纬度
            Log.d("Log", "需要上传经纬度信息");
            // 请调用上传经纬度的接口,此处是写的固定的百度经纬度
            // 参数分别为context对象，经度，纬度，类型（需要通过枚举类获取，分为GPS，百度，google三种经纬度类型）
            String method = LBS.BAIDU.method(); // 获取经纬度类型
//			PushMethodImpl.getInstance().UploadGpsMessage(context,"0", "0", method);
        }
    }

    /**
     * 将接收到的数据，传给UI界面中的广播，更新UI *
     */
    private void sendToValue(Context context, String key, int value) {
        Bundle bund = new Bundle();
        bund.putString("key", key);
        bund.putInt("value", value);
//		SendBroastBean bean = new SendBroastBean(context, bund,
//				PushTestActivity.RECEIVER_MESSAGE_ACTION);
//		SendBroastBean bean1 = new SendBroastBean(context, bund,
//				PushSettingActivity.RECEIVER_MESSAGE_ACTION_1);
//		Tool.sendBroast(bean);
//		Tool.sendBroast(bean1);
    }

    /**
     * 将接收到的自定义消息，传给UI界面中的广播 *
     */
    private void sendMessage(Context context, String key, String value) {
        Bundle bund = new Bundle();
        bund.putString("key", key);
        bund.putString("value", value);
//		SendBroastBean bean = new SendBroastBean(context, bund,
//				PushTestActivity.RECEIVER_MESSAGE_ACTION);
//		SendBroastBean bean1 = new SendBroastBean(context, bund,
//				PushSettingActivity.RECEIVER_MESSAGE_ACTION_1);
//		Tool.sendBroast(bean1);
//		Tool.sendBroast(bean);
    }

    /**
     * 将接收到的通知信息，传给UI界面中的广播 *
     */
    private void sendNotification(Context context, String key, String title,
                                  String content) {
        Bundle bund = new Bundle();
        bund.putString("key", key);
        bund.putString(Consts.NOTIFICATION_TITLE, title);
        bund.putString(Consts.NOTIFICATION_CONTENT, content);
//		SendBroastBean bean = new SendBroastBean(context, bund,
//				PushTestActivity.RECEIVER_MESSAGE_ACTION);
//		SendBroastBean bean1 = new SendBroastBean(context, bund,
//				PushSettingActivity.RECEIVER_MESSAGE_ACTION_1);
//		Tool.sendBroast(bean1);
//		Tool.sendBroast(bean);
    }

}
