package com.airsystem.greendao.sample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.airsystem.greendao.sample.R;
import com.airsystem.greendao.sample.dao.UserDetails;
import com.airsystem.greendao.sample.dao.Users;
import com.airsystem.greendao.sample.services.DatabaseServices;

import java.util.List;

/**
 * Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public class UsersAdapter extends ArrayAdapter<Users> implements IUsersAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public UsersAdapter(Context context) {
        super(context, R.layout.row_users);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void updateData(List<Users> usersList) {
        this.clear();
        for (Users users : usersList) {
            add(users);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_users, null);
            viewHolder = new ViewHolder();
            viewHolder.mTextViewId = (TextView) convertView.findViewById(R.id.tv_userid);
            viewHolder.mTextViewUsername = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.mTextViewUserrole = (TextView) convertView.findViewById(R.id.tv_userrole);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        fillViewWithData(position, viewHolder);
        return convertView;
    }

    private void fillViewWithData(int position, ViewHolder viewHolder) {
        viewHolder.mTextViewId.setText(new StringBuilder(mContext.getString(R.string.row_id)).append(" ").append(getItem(position).getId().toString()));
        viewHolder.mTextViewUsername.setText(new StringBuilder(mContext.getString(R.string.row_username)).append(" ").append(getItem(position).getUsername()));
        viewHolder.mTextViewUserrole.setText(new StringBuilder(mContext.getString(R.string.row_role)).append(" ").append(findUserRoles(mContext, getItem(position).getId())));
    }

    private String findUserRoles(Context context, long id) {
        UserDetails userDetails = DatabaseServices.getInstance(mContext).findByUserDetailsId(context, id);
        return userDetails.getRole();
    }

    static class ViewHolder {
        public TextView mTextViewId;
        public TextView mTextViewUsername;
        public TextView mTextViewUserrole;
    }
}