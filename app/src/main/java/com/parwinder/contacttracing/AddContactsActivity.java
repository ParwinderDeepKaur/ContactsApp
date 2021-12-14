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
import java.util.Collections;
import java.util.regex.Pattern;

public class AddContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveBT, cancelBT;
    private EditText nameET, mobileNoET, emailET;
    private ArrayList<ContactsData> contactsList = new ArrayList<>();
    private ContactsData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts); // inflate the xml file
        findViews();
        setClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get data from intent of previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) { // check if extras are null
            data = (ContactsData) extras.getSerializable("contact_data"); // get data in Serialized form
            setContactData();
        }

        // check if shared preferences is null and then put the data in array list
        if (SharedPref.read("contacts_list") != null)
            contactsList = SharedPref.read("contacts_list");
    }

    /*
     * set model class data in views
     */
    private void setContactData() {
        nameET.setText(data.getName());
        mobileNoET.setText(data.getNumber());
        emailET.setText(data.getEmail());
    }

    /*
     * set click listeners on views
     */
    private void setClickListeners() {
        saveBT.setOnClickListener(this);
        cancelBT.setOnClickListener(this);
    }

    /*
     * find xml view ids
     */
    private void findViews() {
        saveBT = findViewById(R.id.saveBT);
        cancelBT = findViewById(R.id.cancelBT);
        nameET = findViewById(R.id.nameET);
        mobileNoET = findViewById(R.id.mobileET);
        emailET = findViewById(R.id.emailET);
    }

    /*
     * overridden method of View.ClickListener
     * click events of views
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveBT:
                if (validInfo()) { // check if data entered is correct
                    if (data != null) { //remove old data
                        removeDataFromSharedPreferences();
                    }
                    prepareData(); // add data
                }
                break;

            case R.id.cancelBT:
                finish();
                break;
        }
    }

    /*
     * remove data from shared preference
     * check if the name exists in the list with For loop
     * use .remove() method to remove the data from the list
     */
    private void removeDataFromSharedPreferences() {
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getName().equalsIgnoreCase(data.getName())) {
                contactsList.remove(contactsList.get(i));
            }
        }
    }

    /*
     * create data and save the data in shared preference
     */
    private void prepareData() {
        // create data of ContactsData type
        ContactsData data = new ContactsData();
        data.setName(nameET.getText().toString().trim());
        data.setNumber(mobileNoET.getText().toString().trim());
        data.setEmail(emailET.getText().toString().trim());

        // check if data exists then show toast message otherwise add data in list and finish the activity
        if (alreadyExists()) {
            Toast.makeText(this, getString(R.string.contact_already_exists_with_this_name), Toast.LENGTH_SHORT).show();
        } else {
            saveContactsToSharedPreferences(data);
            finish();
        }
    }

    /*
     * check if the data exists in list with For loop
     * use equalsIgnoreCase method to match if the data match with the item in contactList
     */
    private boolean alreadyExists() {
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getName().equalsIgnoreCase(nameET.getText().toString().trim())) {
                return true;
            }
        }
        return false;
    }

    /*
     * remove old data from shared preference
     * sort the data with Collection.sort method
     * save sorted list in the shared preferences
     */
    private void saveContactsToSharedPreferences(ContactsData data) {
        contactsList.add(data);
        SharedPref.clear("contacts_list");
        Collections.sort(contactsList, (text1, text2) -> text1.getName().compareToIgnoreCase(text2.getName()));
        SharedPref.write(contactsList, "contacts_list");
    }

    /*
     * validations for the data in views
     * return true or false if fails to validate*/
    private boolean validInfo() {
        if (nameET.getText().toString().trim().isEmpty()) { // empty field validation
            Toast.makeText(this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobileNoET.getText().toString().trim().isEmpty()) { // empty field validation
            Toast.makeText(this, getString(R.string.enter_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validMobileNo(mobileNoET.getText().toString().trim())) { // invalid mobile number validation
            Toast.makeText(this, getString(R.string.enter_valid_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!emailET.getText().toString().trim().isEmpty()) { // empty field validation
            if (!validEmailId(emailET.getText().toString().trim())) { // invalid email id validation
                Toast.makeText(this, getString(R.string.enter_valid_email_id), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        // return true if all above conditions satisfied
        return true;
    }

    /*
     * email regex to validate the email id string
     * @emailId: param to check validation of
     * return true false if emailId match with pattern
     */
    private boolean validEmailId(String emailId) {
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(emailId).matches();
    }

    /*
     * boolean method to check validation of mobile number
     * @phone: the param for which validation is to be checked
     * @return true or false acc to regex matched*/
    private boolean validMobileNo(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 8 && phone.length() <= 13;
        }
        return false;
    }
}