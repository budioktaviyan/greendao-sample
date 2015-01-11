package com.airsystem.greendao.sample.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.airsystem.greendao.sample.R;
import com.airsystem.greendao.sample.dao.UserDetails;
import com.airsystem.greendao.sample.dao.Users;
import com.airsystem.greendao.sample.services.DatabaseServices;

/**
 * Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public class DetailsActivity extends ActionBarActivity implements DialogInterface.OnClickListener {
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;

    private Spinner mSpinnerRole;
    private Spinner mSpinnerGender;

    private Users mUsers;
    private UserDetails mUserDetails;

    private long mUserId;
    private long mUserDetailsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit: {
                createoreditUser();
                return true;
            }
            case R.id.action_delete: {
                deleteUserDialog();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
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
                deleteUser(DetailsActivity.this, dialog);
            }
            case AlertDialog.BUTTON_NEGATIVE: {
                dialog.cancel();
            }
        }
    }

    private void initComponents() {
        initEditText();
        initRoleSpinner();
        initGenderSpinner();
        initData();
    }

    private void initEditText() {
        mEditTextUsername = (EditText) findViewById(R.id.et_username);
        mEditTextPassword = (EditText) findViewById(R.id.et_password);
        mEditTextFirstName = (EditText) findViewById(R.id.et_firstname);
        mEditTextLastName = (EditText) findViewById(R.id.et_lastname);
    }

    private void initRoleSpinner() {
        mSpinnerRole = (Spinner) findViewById(R.id.sp_role);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRole.setAdapter(adapter);
    }

    private void initGenderSpinner() {
        mSpinnerGender = (Spinner) findViewById(R.id.sp_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGender.setAdapter(adapter);
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mUserId = getIntent().getExtras().getLong("usersId");
            mUserDetailsId = getIntent().getExtras().getLong("userDetailsId");
            mUsers = DatabaseServices.getInstance(DetailsActivity.this).findById(DetailsActivity.this, mUserId);
            mUserDetails = DatabaseServices.getInstance(DetailsActivity.this).findByUserDetailsId(DetailsActivity.this, mUserDetailsId);
        }
        fillViewWithData();
    }

    private void createoreditUser() {
        if (validateFields()) {
            if (mUsers == null) {
                mUsers = new Users();
                mUserDetails = new UserDetails();
            } else {
                mUsers.setId(mUserId);
                mUserDetails.setId(mUserDetailsId);
            }

            mUsers.setUsername(mEditTextUsername.getText().toString());
            mUsers.setPassword(mEditTextPassword.getText().toString());
            mUserDetails.setFirstname(mEditTextFirstName.getText().toString());
            mUserDetails.setLastname(mEditTextLastName.getText().toString());
            mUserDetails.setRole(mSpinnerRole.getSelectedItem().toString());
            mUserDetails.setGender(mSpinnerGender.getSelectedItem().toString());

            DatabaseServices.getInstance(DetailsActivity.this).insertOrUpdate(DetailsActivity.this, mUsers, mUserDetails);
            finish();
        } else {
            Toast.makeText(DetailsActivity.this, getString(R.string.error_validation), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields() {
        if (mEditTextUsername.getText().length() == 0) {
            mEditTextUsername.setError(getString(R.string.error_cannot_be_empty));
            return false;
        }
        if (mEditTextPassword.getText().length() == 0) {
            mEditTextPassword.setError(getString(R.string.error_cannot_be_empty));
            return false;
        }

        mEditTextUsername.setError(null);
        mEditTextPassword.setError(null);
        return true;
    }

    private void fillViewWithData() {
        if (mUsers != null) {
            mEditTextUsername.setText(mUsers.getUsername());
            mEditTextPassword.setText(mUsers.getPassword());
        }
        if (mUserDetails != null) {
            mEditTextFirstName.setText(mUserDetails.getFirstname());
            mEditTextLastName.setText(mUserDetails.getLastname());

            if (mUserDetails.getRole().equalsIgnoreCase("ADMIN")) {
                mSpinnerRole.setSelection(0);
            } else {
                mSpinnerRole.setSelection(1);
            }
            if (mUserDetails.getGender().equalsIgnoreCase("MALE")) {
                mSpinnerGender.setSelection(0);
            } else {
                mSpinnerGender.setSelection(1);
            }
        }
    }

    private void deleteUserDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailsActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_item));
        alertDialogBuilder.setMessage(getString(R.string.dialog_content_delete_item));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.dialog_confirm, this);
        alertDialogBuilder.setNegativeButton(R.string.dialog_cancel, this);
        alertDialogBuilder.create().show();
    }

    private void deleteUser(Context context, DialogInterface dialog) {
        if (mUserId != 0 && mUserDetailsId != 0) {
            DatabaseServices.getInstance(context).deleteById(context, mUserId, mUserDetailsId);
        }
        dialog.cancel();
        finish();
    }
}