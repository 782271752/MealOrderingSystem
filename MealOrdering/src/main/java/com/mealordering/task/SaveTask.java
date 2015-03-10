package com.mealordering.task;

import android.os.AsyncTask;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.mealordering.db.DBHelper;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.utils.ToastUtil;

public class SaveTask extends AsyncTask<FoodsResult.Food, Void, Boolean> {
    @Override
    protected Boolean doInBackground(FoodsResult.Food... params) {
        RuntimeExceptionDao<FoodsResult.Food, Integer> dao = DBHelper.getInstance().getFoodDao();
        boolean success = false;
        if (params.length > 0) {
            dao.createOrUpdate(params[0]);
            success = true;
        }
        return success;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (success) {
            ToastUtil.showShortTimeMsg("订购成功!");
        } else {
            ToastUtil.showShortTimeMsg("系统错误,订购失败.");
        }

    }
}