package com.airsystem.greendao.sample.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.airsystem.greendao.sample.R;
import com.airsystem.greendao.sample.dao.UserDetails;
import com.airsystem.greendao.sample.dao.Users;
import com.airsystem.greendao.sample.services.DatabaseServices;
import com.airsystem.greendao.sample.ui.adapter.UsersAdapter;

/**
 * @author Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    ListView mUsers;

    private UsersAdapter mUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUsersAdapter.updateData(DatabaseServices.getInstance(MainActivity.this).findAll(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add: {
                createUser();
                return true;
            }
            case R.id.action_deleteall: {
                deleteAllUsersDialog();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent editUserActivity = new Intent(MainActivity.this, DetailsActivity.class);
        Users usersSelected = mUsersAdapter.getItem(position);
        UserDetails userDetailsSelected = DatabaseServices.getInstance(MainActivity.this).findByUserDetailsId(MainActivity.this, usersSelected.getId());
        editUserActivity.putExtra("usersId", usersSelected.getId());
        editUserActivity.putExtra("userDetailsId", userDetailsSelected.getId());
        startActivity(editUserActivity);
    }

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which  The button that was clicked (e.g.
     *               {@link android.content.DialogInterface#BUTTON1}) or the position
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE: {
                deleteAllUsers(MainActivity.this, dialog);
            }
            case AlertDialog.BUTTON_NEGATIVE: {
                dialog.cancel();
            }
        }
    }

    private void initComponents() {
        mUsers = (ListView) this.findViewById(R.id.lv_users);
        mUsersAdapter = new UsersAdapter(MainActivity.this);
        mUsers.setAdapter(mUsersAdapter);
        mUsers.setOnItemClickListener(this);
    }

    private void createUser() {
        Intent createUserActivity = new Intent(MainActivity.this, DetailsActivity.class);
        startActivity(createUserActivity);
    }

    private void deleteAllUsersDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_items));
        alertDialogBuilder.setMessage(getString(R.string.dialog_content_delete_items));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.dialog_confirm, this);
        alertDialogBuilder.setNegativeButton(R.string.dialog_cancel, this);
        alertDialogBuilder.create().show();
    }

    private void deleteAllUsers(Context context, DialogInterface dialog) {
        DatabaseServices.getInstance(context).deleteAll(context);
        mUsersAdapter.updateData(DatabaseServices.getInstance(MainActivity.this).findAll(MainActivity.this));
        dialog.cancel();
    }
}