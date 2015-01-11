package com.airsystem.greendao.sample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.airsystem.greendao.sample.dao.DaoMaster;
import com.airsystem.greendao.sample.dao.DaoSession;

/**
 * Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public class MainApplication extends Application {
    public DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "greendao-sample", null);
        SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}