package com.mealordering.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.content.pm.PackageInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mealordering.net.model.VersionEntity;
import com.mealordering.ui.FindBackActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by li on 2014/6/18.
 * 版本更新，用户密码更新
 */
public class UpdateUtil {
    public static int localVersion = 0;// 本地安装版本
    public static final String UPDATE_VERSION_URL="http://in.kebabar.cn/json/VersionSet.aspx?VersionType=2";
    public static final String UPDATE_USER_URL="http://in.kebabar.cn/json/UpdatePwd.aspx";
    public static final String DEL_ADDRESS="http://in.kebabar.cn/json/MyAddress/deleteAddress.aspx?cityId=";
    public static final String PACKAGE_NAME = "com.mealordering";
    public static final String UPDATE_ADDRESS="http://in.kebabar.cn/json/MyAddress/UpdateAddress.aspx?";

    public static String getUpdateAddressUrl(String cityId,String city,String area,String road,String address ,String isDefault,
                 String userId,String username,String usex,String userPhone ){
        String url=UPDATE_ADDRESS+"cityId="+cityId+"&City="+city+"&Area="+area+"&Road="+road+"&Address="+address+"&IsDefault="+
                isDefault+"&UserId="+userId+"&Username="+username+"&USex="+usex+"&UserPhone="+userPhone;
        Log.e("UpdateAddressUrl",url);
        return url;

    }


    public static final String APP_DIR = SetAppDir();
    public static File updateFile;

    public static Dialog dialog=null;

    public static String getUpdateUserUrl(String phone,String psd){
        return UPDATE_USER_URL+"?Phone="+phone+"&Pwd="+psd;
    }

    public static String getDelAddressUrl(String cityID){
        return DEL_ADDRESS+cityID;
    }
    public static String conWeb(String url) {
        String str = "";
        try {
            HttpGet request = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                str = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
//            e.printStackTrace();
            str="";
        }
        return str;
    }


    /**
     * 设置缓存文件的保存位置
     */
    public static String SetAppDir() {
        String app_dir = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            app_dir = Environment.getExternalStorageDirectory() + "/li/";
        } else {
            app_dir = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/li/";
        }
        return app_dir;
    }

    /***
     * 创建文件
     */
    public static void createFile(String name) {
        File updateDir = new File(APP_DIR+"APK/");
        updateFile = new File(updateDir + name + ".apk");

        if (!updateDir.exists()) {
            updateDir.mkdirs();
        }

        if (updateFile.exists()) {
            updateFile.delete();
        }

        try {
            updateFile.createNewFile();
        } catch (IOException e) {
            Log.e("update",e.toString());
        }

    }

    /**
     * 获取本地apk版本号
     * @param context
     * @return
     */
    public static int getLocalVersionCode(Context context){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            localVersion = packageInfo.versionCode;
            Log.e("本地apk版本", localVersion+"");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return localVersion;
    }

    public static VersionEntity getVersion(String json){
        VersionEntity version=new VersionEntity();
        String result="";
        try{
            JSONObject object=new JSONObject(json);
            result=object.getString("Result");
            Log.e("result",result);
            if (result.equals("1")){
                JSONObject vobject=object.getJSONObject("Data");
                version.setId(vobject.getString("VersionUpdateId"));
                version.setVersionNo(vobject.getString("VersionNo"));
                version.setVersionUrl(vobject.getString("VersionUrl"));
            }else{
                version=null;
            }


        }catch (Exception e){
//            e.printStackTrace();;
            version=null;

        }

        return version;
    }

    /**
     * 异步更新版本
     * @param context
     * @param auto 是否自动更新
     */
    public static void updateVersionAsync(final Context context, final boolean auto){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {

                return UpdateUtil.conWeb(UpdateUtil.UPDATE_VERSION_URL);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                VersionEntity version=UpdateUtil.getVersion(s);
                if(version==null){
                    return;
                }


                if (UpdateUtil.getLocalVersionCode(context)<Integer.parseInt(version.getVersionNo())){

                    Log.e("UpdateUtil.getLocalVersionCode(context)",UpdateUtil.getLocalVersionCode(context)+"");
                    Log.e("Integer.parseInt(version.getVersionNo())",Integer.parseInt(version.getVersionNo())+"");

                    showVersionDialog(version.getVersionUrl(), context);

                    if (!auto){
                        cancelWaitDialog();
//                    Toast.makeText(context,"当前已经是最新版本",Toast.LENGTH_LONG).show();
                    }

                }else{
                    if (!auto){
                        cancelWaitDialog();
                        Toast.makeText(context,"当前已经是最新版本",Toast.LENGTH_LONG).show();
                    }
                }

            }

        }.execute();
    }

    public static void showVersionDialog(final String url,final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("检测到新版本更新,是否立刻更新？");

        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(url);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(it);
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }

    public static void showWaitDialog(Context context){
        dialog=new AlertDialog.Builder(context)
                .setView(new ProgressBar(context))
                .create();
        dialog.show();
    }

    public static void cancelWaitDialog(){
        dialog.cancel();
    }

    /**
     * 检查是否连接网络
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context){
        boolean flag=false;
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan == null) {
            flag= false;
        } else {
            NetworkInfo[] info = conMan.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        flag= true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    public static void updateUserAsync(final String url, final Context context){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                return conWeb(url);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String result="";
                if (s.equals("")){
                    ToastUtil.showLongTimeMsg("网络错误");
                    return;
                }

                try{
                    JSONObject object=new JSONObject(s);
                    result=object.getString("Result");
                    Log.e("result",result);

                    if(result.equals("1")){
                        cancelWaitDialog();
                        ((FindBackActivity)context).finish();
                        ToastUtil.showLongTimeMsg("修改成功");
                    }else{
                        cancelWaitDialog();
                        ToastUtil.showLongTimeMsg("修改失败");
                    }


                }catch (Exception e){

                }


            }
        }.execute();
    }




}
