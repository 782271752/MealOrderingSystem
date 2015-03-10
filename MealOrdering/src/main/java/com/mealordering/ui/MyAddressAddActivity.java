package com.mealordering.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.mealordering.AppContext;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.ViewItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.CityResults;
import com.mealordering.net.model.EmptyResult;
import com.mealordering.net.model.UserDetailResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.UpdateUtil;
import com.mealordering.utils.ViewUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyAddressAddActivity extends TitleSubActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    @InjectView(R.id.my_address_find_street_btn)
    Button mFindStreetBtn;
    @InjectView(R.id.my_address_detail_et)
    EditText mDetailEt;
    @InjectView(R.id.my_address_name_et)
    EditText mNameEt;
    @InjectView(R.id.my_address_sex_man_rb)
    RadioButton mSexManRb;
    @InjectView(R.id.my_address_sex_woman_rb)
    RadioButton mSexWomanRb;
    @InjectView(R.id.my_address_phone_et)
    EditText mPhoneEt;
    @InjectView(R.id.my_address_cities_sp)
    Spinner mCitiesSp;
    @InjectView(R.id.my_address_districts_sp)
    Spinner mAreasSp;
    @InjectView(R.id.my_address_road_et)
    EditText mRoadEt;
    @InjectView(R.id.my_address_sex_rg)
    RadioGroup mSexRg;
    @InjectView(R.id.my_address_default_cb)
    CheckBox mDefaultCb;

    CityResults mCityResults;
    TemplateAdapter<CityResults.City> mCitiesAdapter;
    TemplateAdapter<CityResults.City.Area> mAreasAdapter;
    //    public static final String EXTRA_FROM = "from";
//    public static final int FROM_ORDER = 0xff1;
//    public static final int FROM_ADDRESS_MANAGER = 0xff2;
    private String mFindRoadResult;
    List<CityResults.City> mCities;

    /**
     * 判断是否是修改模式
     */
    private boolean isAlter=false;
    String cityId, mCity, mArea,road,address ,username,usex,userPhone;
    private boolean isCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address_add);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.btn_ok, this);
        ViewUtil.findViewAndClick(this, R.id.my_address_find_street_btn, this);

        UserDetailResult.UserDetail userDetail = AppContext.getInstance().getUserDetail();
//        ViewUtil.setText(mNameEt, userDetail.getRealName());
//        ViewUtil.setText(mPhoneEt, userDetail.getPhone());

        mCitiesSp.setOnItemSelectedListener(this);

        mCitiesAdapter = new TemplateAdapter<CityResults.City>(new CityItemBuilder());
        mAreasAdapter = new TemplateAdapter<CityResults.City.Area>(new AreaItemBuilder());

        mCitiesSp.setAdapter(mCitiesAdapter);
        mAreasSp.setAdapter(mAreasAdapter);

        Bundle bundle=this.getIntent().getExtras();
        if (bundle!=null){
            isAlter=true;
            cityId=bundle.getString("cityId");
            mCity =bundle.getString("city");
            mArea =bundle.getString("area");
            road=bundle.getString("road");
            address=bundle.getString("address");
            username=bundle.getString("username");
            userPhone=bundle.getString("userPhone");
            mSexRg.check(Integer.parseInt(bundle.getString("usex")) == 1 ? R.id.my_address_sex_man_rb : R.id.my_address_sex_woman_rb);

            mRoadEt.setText(road);
            mDetailEt.setText(address);
            mNameEt.setText(username);
            mPhoneEt.setText(userPhone);
            mFindRoadResult=road;

            List<CityResults.City> cities=new ArrayList<CityResults.City>();
            CityResults.City cityResult=new CityResults.City();
            cityResult.setCity(mCity);
            List<CityResults.City.Area> areas=new ArrayList<CityResults.City.Area>();
            CityResults.City.Area areaResult= new CityResults.City.Area();
            areaResult.setArea(mArea);
            areas.add(areaResult);
            cityResult.setAreas(areas);
            cities.add(cityResult);

            mCitiesAdapter.setList(cities);
            mAreasAdapter.setList(cities.get(0).getAreas());

            ViewUtil.findTextViewAndSetText(this,R.id.btn_ok,"修改地址");
            ViewUtil.findViewAndClick(this, R.id.btn_ok, this);


        }else{
            isAlter=false;
            mSexRg.check(userDetail.getSex() == 1 ? R.id.my_address_sex_man_rb : R.id.my_address_sex_woman_rb);
            ViewUtil.findViewAndClick(this, R.id.btn_ok, this);
        }


        RequestHelper.exeGetAllDistributionAreaRequest(getSpiceManager(), new CitiesRequestListener());
    }

    @Override
    public void onClick(View v) {
        CityResults.City selectCity = mCityResults.getData().get(mCitiesSp.getSelectedItemPosition());
        String city = selectCity.getCity();
        String area = selectCity.getAreas().get(mAreasSp.getSelectedItemPosition()).getArea();
        switch (v.getId()) {
            case R.id.my_address_find_street_btn: {
                if (ViewUtil.checkEmpty(mRoadEt, mRoadEt.getHint())) {
                    return;
                }
                String road = mRoadEt.getText().toString();
                Intents.launchFindRoad(this, city, area, road);
            }
            break;
            case R.id.btn_ok:
                if (ViewUtil.checkEmpty(mRoadEt, mRoadEt.getHint())) {
                    return;
                }
                if (TextUtils.isEmpty(mFindRoadResult)) {
                    ViewUtil.setError(mRoadEt, "请查找 路名/小区名 !");
                    return;
                }
                if (!mRoadEt.getText().toString().equals(mFindRoadResult)) {
                    ViewUtil.setError(mRoadEt, "你修改了 路名/小区名! 请重新查找!");
                    return;
                }

                if (isAlter){

                    if (isCheck){
                        mCity=mCities.get(0).getCity();
                        mArea=mCities.get(0).getAreas().get(0).getArea();
                    }

                    if (!mCities.get(0).getCity().equals(mCity)||!mCities.get(0).getAreas().get(0).getArea().equals(mArea)){
                        ViewUtil.setError(mRoadEt, "你修改了 区域! 请重新查找!");
                        return;
                    }
                }
                if (ViewUtil.checkEmpty(mDetailEt, "请输入详细地址")) {
                    return;
                }
                if (ViewUtil.checkEmpty(mNameEt, mNameEt.getHint())) {
                    return;
                }
                if (ViewUtil.checkEmpty(mPhoneEt, mPhoneEt.getHint())) {
                    return;
                }
                if(!checkNameChese(mNameEt.getText().toString())){
                    ToastUtil.showLongTimeMsg("收货人不能为数字");
                    return;
                }

                String road = mRoadEt.getText().toString();
                String address = mDetailEt.getText().toString();
                boolean isDefault = mDefaultCb.isChecked();
                String userName = mNameEt.getText().toString();
                boolean isMan = mSexManRb.isChecked();
                String phone = mPhoneEt.getText().toString();

                if(isAlter){
                    updapteAddressAsync(cityId,city,area,road,address , (isDefault ? 1 : 0)+"",AppContext.getInstance().getUserDetail().getUserId()
                            ,userName,(isMan ? 1:2)+"", phone);
                }else{
                    RequestHelper.exeAddAddressRequest(getSpiceManager(), new AddAddressRequestListener(),
                            city, area, road, address, isDefault, userName, isMan, phone);
                }

                break;
        }
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


                return UpdateUtil.conWeb(UpdateUtil.getUpdateAddressUrl(cityId, city, area, road, address, isDefault,
                        userId, username, usex, userPhone));
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
                        ToastUtil.showLongTimeMsg("修改成功");
                        MyAddressAddActivity.this.finish();

                    }else{
                        ToastUtil.showLongTimeMsg("修改失败");
                    }

                }catch (Exception e){

                }
            }
        }.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CityResults.City city = mCitiesAdapter.getItem(position);
        mAreasAdapter.setList(city.getAreas());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data.getExtras() != null) {
            mFindRoadResult = data.getStringExtra(Intents.FIND_ROAD);
            mRoadEt.setError(null, null);
            ViewUtil.setText(mRoadEt, mFindRoadResult);
            isCheck=true;
        }
    }

    private class CitiesRequestListener extends SimpleRequestListener<CityResults> {
        public CitiesRequestListener() {
            super(ViewUtil.findView(MyAddressAddActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(CityResults result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                mCityResults = result;

                mCities = result.getData();
                mCitiesAdapter.setList(mCities);
                if (mCities.get(0) != null)
                    mAreasAdapter.setList(mCities.get(0).getAreas());


            }
        }
    }

    private class AddAddressRequestListener extends SimpleRequestListener<EmptyResult> {
        public AddAddressRequestListener() {
            super(ViewUtil.findView(MyAddressAddActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(EmptyResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                ToastUtil.showShortTimeMsg("添加地址成功!");
                finish();
            } else {
                ToastUtil.showShortTimeMsg("添加地址失败!请重试!");
            }
        }
    }

    private class CityItemBuilder implements ViewItemBuilder<CityResults.City> {
        @Override
        public View createView(LayoutInflater inflater) {
            return inflater.inflate(android.R.layout.simple_spinner_item, null);
        }

        @Override
        public void populateView(TemplateAdapter<CityResults.City> adapter, View view, int position, CityResults.City item) {
            ViewUtil.findTextViewAndSetText(view, android.R.id.text1, item.getCity());
        }
    }

    private class AreaItemBuilder implements ViewItemBuilder<CityResults.City.Area> {
        @Override
        public View createView(LayoutInflater inflater) {
            return inflater.inflate(android.R.layout.simple_spinner_item, null);
        }

        @Override
        public void populateView(TemplateAdapter<CityResults.City.Area> adapter, View view, int position, CityResults.City.Area item) {
            ViewUtil.findTextViewAndSetText(view, android.R.id.text1, item.getArea());
        }
    }

    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    public  boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */
    public  boolean checkNameChese(String name)
    {
        boolean res=true;
        char [] cTemp = name.toCharArray();
        for(int i=0;i<name.length();i++)
        {
            if(!isChinese(cTemp[i]))
            {
                res=false;
                break;
            }
        }
        return res;
    }
}
