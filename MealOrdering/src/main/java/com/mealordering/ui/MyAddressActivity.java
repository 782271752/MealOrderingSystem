package com.mealordering.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.AppContext;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.MyAddressItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.MyAddressResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.UpdateUtil;
import com.mealordering.utils.ViewUtil;

import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyAddressActivity extends TitleSubActivity implements View.OnClickListener
        , PullToRefreshBase.OnRefreshListener{

    @InjectView(R.id.list_lv)
    PullToRefreshListView mAddressLv;
    private List<MyAddressResult.MyAddress> myAddresses;
    private TemplateAdapter<MyAddressResult.MyAddress> mAdapter;
    int mposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.inject(this);
        mAddressLv.getRefreshableView().setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mAdapter = new TemplateAdapter<MyAddressResult.MyAddress>(new MyAddressItemBuilder(true,true));
        mAddressLv.setAdapter(mAdapter);
        mAddressLv.setOnRefreshListener(this);
        mAddressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mposition=position;
                showDialog(0);

//                if (position==0){
//                    return;
//                }
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MyAddressActivity.this);
//                builder.setTitle("提示");
//                builder.setMessage("是否要删除当前选中地址？");
//
//                builder.setCancelable(false);
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (position!=0){
//                            delAsync(myAddresses.get(position).getCityId());
//                        }
//
//                    }
//                });
//                builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.show();


            }
        });



        ViewUtil.findViewAndClick(this, R.id.my_address_add_btn, this);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog=null;
        AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        switch (id){
            case 0:
                builder.setItems(R.array.address_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                String cityId=myAddresses.get(mposition).getCityId();
                                String city=myAddresses.get(mposition).getCity();
                                String area=myAddresses.get(mposition).getArea();
                                String road=myAddresses.get(mposition).getRoad();
                                String address=myAddresses.get(mposition).getDetailed();
                                String isDefault="1";
                                String userId= AppContext.getInstance().getUserDetail().getUserId();
                                String username=myAddresses.get(mposition).getUserName();
                                String usex=myAddresses.get(mposition).getUSex();
                                String userPhone=myAddresses.get(mposition).getUserPhone();
                                if (usex.equals("先生")){
                                    usex="1";
                                }else{
                                    usex="2";
                                }

                                updapteAddressAsync(cityId,city,area,road,address ,isDefault,
                                        userId,username,usex, userPhone);

                                break;
                            case 1:
                                cityId=myAddresses.get(mposition).getCityId();
                                city=myAddresses.get(mposition).getCity();
                                area=myAddresses.get(mposition).getArea();
                                road=myAddresses.get(mposition).getRoad();
                                address=myAddresses.get(mposition).getDetailed();
                                username=myAddresses.get(mposition).getUserName();
                                usex=myAddresses.get(mposition).getUSex();
                                userPhone=myAddresses.get(mposition).getUserPhone();
                                if (usex.equals("先生")){
                                    usex="1";
                                }else{
                                    usex="2";
                                }

                                Intent intent=new Intent(MyAddressActivity.this,MyAddressAddActivity.class);
                                intent.putExtra("cityId",cityId);
                                intent.putExtra("city",city);
                                intent.putExtra("area",area);
                                intent.putExtra("road",road);
                                intent.putExtra("address",address);
                                intent.putExtra("username",username);
                                intent.putExtra("usex",usex);
                                intent.putExtra("userPhone",userPhone);

                                startActivity(intent);

                                break;
                            case 2:
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyAddressActivity.this);
                                builder.setTitle("提示");
                                builder.setMessage("是否要删除当前选中地址？");

                                builder.setCancelable(false);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (mposition!=0){
                                            delAsync(myAddresses.get(mposition).getCityId());
                                        }

                                    }
                                });
                                builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();

                                break;
                        }
                    }
                });
                dialog=builder.create();
                break;
        }
        return dialog;
    }

    private void delAsync(final String cityId){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                return UpdateUtil.conWeb(UpdateUtil.getDelAddressUrl(cityId));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("")){
                    return;
                }
                try{
                    JSONObject object=new JSONObject(s);
                    String result=object.getString("Result");
                    if (result.equals("1")){
                        RequestHelper.exeMyAddressRequest(getSpiceManager(), new AddressRequestListener());
                        ToastUtil.showLongTimeMsg("删除成功");
                    }else{
                        ToastUtil.showLongTimeMsg("删除失败");
                    }

                }catch (Exception e){

                }



            }
        }.execute();
    }

    /**
     * 异步更新线程
     * @param cityId
     * @param city
     * @param area
     * @param road
     * @param address
     * @param isDefault
     * @param userId
     * @param username
     * @param usex
     * @param userPhone
     */
    private void updapteAddressAsync(final String cityId,final String city,final String area,final String road,final String address ,final String isDefault,
                              final String userId,final String username,final String usex,final String userPhone){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {


                return UpdateUtil.conWeb(UpdateUtil.getUpdateAddressUrl(cityId,city,area,road,address ,isDefault,
                        userId,username,usex, userPhone));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("")){
                    return;
                }
                try{
                    JSONObject object=new JSONObject(s);
                    String result=object.getString("Result");
                    if (result.equals("1")){
                        RequestHelper.exeMyAddressRequest(getSpiceManager(), new AddressRequestListener());
                        ToastUtil.showLongTimeMsg("设置成功");
                    }else{
                        ToastUtil.showLongTimeMsg("设置失败");
                    }

                }catch (Exception e){

                }
            }
        }.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        RequestHelper.exeMyAddressRequest(getSpiceManager(), new AddressRequestListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_address_add_btn:
                Intents.launchMyAddressAdd(this);
                break;
        }

    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        RequestHelper.exeMyAddressRequest(getSpiceManager(), new AddressRequestListener());
    }

    private class AddressRequestListener extends SimpleRequestListener<MyAddressResult> {
        public AddressRequestListener() {
            super(ViewUtil.findView(MyAddressActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyAddressResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                myAddresses = result.getData();
                mAdapter.setList(myAddresses);
            }
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mAddressLv.setRefreshing();
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mAddressLv.onRefreshComplete();
        }
    }
}
