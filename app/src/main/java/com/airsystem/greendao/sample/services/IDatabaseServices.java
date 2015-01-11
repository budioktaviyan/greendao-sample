package com.airsystem.greendao.sample.services;

import android.content.Context;

import com.airsystem.greendao.sample.dao.UserDetails;
import com.airsystem.greendao.sample.dao.Users;

import java.util.List;

/**
 * Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public interface IDatabaseServices {
    List<Users> findAll(Context context);

    Users findById(Context context, long id);

    UserDetails findByUserDetailsId(Context context, long id);

    void insertOrUpdate(Context context, Users users, UserDetails userDetails);

    void deleteAll(Context context);

    void deleteById(Context context, long id, long detailid);
}