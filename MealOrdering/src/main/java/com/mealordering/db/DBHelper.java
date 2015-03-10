package com.mealordering.db;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mealordering.AppContext;
import com.mealordering.net.model.FoodsResult;

import java.sql.SQLException;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "appdb";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper instance = null;

    private DBHelper() {
        super(AppContext.getInstance(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public RuntimeExceptionDao<FoodsResult.Food, Integer> getFoodDao() {
        return getRuntimeExceptionDao(FoodsResult.Food.class);
    }

    @Override

    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, FoodsResult.Food.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, FoodsResult.Food.class, true);
            TableUtils.createTable(connectionSource, FoodsResult.Food.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
