package com.airsystem.greendao.sample.services;

import android.content.Context;

import com.airsystem.greendao.sample.MainApplication;
import com.airsystem.greendao.sample.dao.UserDetails;
import com.airsystem.greendao.sample.dao.UserDetailsDao;
import com.airsystem.greendao.sample.dao.Users;
import com.airsystem.greendao.sample.dao.UsersDao;

import java.util.List;

/**
 * Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public class DatabaseServices implements IDatabaseServices {
    Context mContext;

    private static DatabaseServices mInstance;

    public DatabaseServices(final Context context) {
        mContext = context;
    }

    public static DatabaseServices getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseServices(context);
        }
        return mInstance;
    }

    @Override
    public List<Users> findAll(Context context) {
        return getUsersDao(context).loadAll();
    }

    @Override
    public Users findById(Context context, long id) {
        return getUsersDao(context).load(id);
    }

    @Override
    public UserDetails findByUserDetailsId(Context context, long id) {
        return getUserDetailsDao(context).load(id);
    }

    @Override
    public void insertOrUpdate(Context context, Users users, UserDetails userDetails) {
        getUsersDao(context).insertOrReplace(users);
        getUserDetailsDao(context).insertOrReplace(userDetails);
    }

    @Override
    public void deleteAll(Context context) {
        getUsersDao(context).deleteAll();
        getUserDetailsDao(context).deleteAll();
    }

    @Override
    public void deleteById(Context context, long id, long detailid) {
        getUsersDao(context).delete(findById(context, id));
        getUserDetailsDao(context).delete(findByUserDetailsId(context, detailid));
    }

    private static UsersDao getUsersDao(Context context) {
        return ((MainApplication) context.getApplicationContext()).getDaoSession().getUsersDao();
    }

    private static UserDetailsDao getUserDetailsDao(Context context) {
        return ((MainApplication) context.getApplicationContext()).getDaoSession().getUserDetailsDao();
    }
}