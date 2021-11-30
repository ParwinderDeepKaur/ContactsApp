package com.parwinder.contacttracing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AddContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveBT, cancelBT;
    private EditText nameET, mobileNoET, emailET;
    private ArrayList<ContactsData> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        findViews();
        setClickListeners();
    }

    private void setClickListeners() {
        saveBT.setOnClickListener(this);
        cancelBT.setOnClickListener(this);
    }

    private void findViews() {
        saveBT = findViewById(R.id.saveBT);
        cancelBT = findViewById(R.id.cancelBT);
        nameET = findViewById(R.id.nameET);
        mobileNoET = findViewById(R.id.mobileET);
        emailET = findViewById(R.id.emailET);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveBT:
                if (validInfo()) {
                    saveContactsToSharedPreferences();
                }
                break;

            case R.id.cancelBT:
                finish();
                break;
        }
    }

    private void saveContactsToSharedPreferences() {
        ContactsData data = new ContactsData();
        data.setName(nameET.getText().toString().trim());
        data.setNumber(mobileNoET.getText().toString().trim());
        data.setEmail(emailET.getText().toString().trim());

        if (SharedPref.read("contacts_list") != null)
            contactsList = SharedPref.read("contacts_list");
        contactsList.add(data);
        SharedPref.clear("contacts_list");
        SharedPref.write(contactsList, "contacts_list");
        finish();
    }

    private boolean validInfo() {
        if (nameET.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobileNoET.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validMobileNo(mobileNoET.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.enter_valid_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailET.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_email_id), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validEmailId(emailET.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.enter_valid_email_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validEmailId(String emailId) {
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(emailId).matches();
    }

    private boolean validMobileNo(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 8 && phone.length() <= 13;
        }
        return false;
    }
}
