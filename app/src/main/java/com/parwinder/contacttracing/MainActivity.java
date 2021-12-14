package com.parwinder.contacttracing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Student name: Parwinder Deep Kaur
 * Student ID: A00237487
 * This is a Contacts App.
 * Users can save new contacts, edit a contact, delete a contact.
 * There are validations on saving new contact
 *
 * @author Parwinder Deep Kaur
 * @version 1.0
 * @since 16 November, 2021
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private FloatingActionButton addBT;
    private ArrayList<ContactsData> contactsList = new ArrayList();
    private RecyclerView contactsRV;
    private TextView noDataTV;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflating xml resource
        setContentView(R.layout.activity_main);
        // singleton class for Shared Preferences
        SharedPref.init(getApplicationContext());
        findViews();
        addBT.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactsList = SharedPref.read("contacts_list"); // read data from shared preference
        setViewVisibility();
    }

    /*
     * This method sets visibility of views according to data in shared preferences*/
    private void setViewVisibility() {
        if (contactsList == null || contactsList.isEmpty()) {
            noDataTV.setVisibility(View.VISIBLE);
            contactsRV.setVisibility(View.GONE);
        } else {
            noDataTV.setVisibility(View.GONE);
            contactsRV.setVisibility(View.VISIBLE);
            setupAdapter();
        }
    }

    /* Set layout manager for recyclerview
     * Initialize the adapter and set adapter to recyclerview
     * set click listener on adapter items*/
    private void setupAdapter() {
        contactsRV.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactsAdapter(this, contactsList);
        contactsRV.setAdapter(contactsAdapter);
        contactsAdapter.setClickListener(this);
    }

    /*
     * find xml views ids*/
    private void findViews() {
        addBT = findViewById(R.id.addBT);
        noDataTV = findViewById(R.id.noDataTV);
        contactsRV = findViewById(R.id.contactsRV);
    }

    /*
     * intent to go from current activity to AddContactsActivity*/
    private void gotoAddContactActivity() {
        Intent myIntent = new Intent(this, AddContactsActivity.class);
        startActivity(myIntent);
    }

    /*
     * create intent to make call
     * takes phone number as parts of uri part
     * start new activity*/
    private void makeCall(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        startActivity(intent);
    }

    /*
     * overridden method of click listener
     * contains if condition for activity's view clicks*/
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addBT) {
            gotoAddContactActivity();
        }
    }

    /*
     * overridden method of View.OnClickListener class
     * This method is of ContactsAdapter.
     * Using @clickOn param, this method first check which view is clicked
     * @position this is the position of item which is clicked
     * @view this is the actual view which is clicked
     */
    @Override
    public void onClick(View view, int position, String clickOn) {
        if (clickOn.equalsIgnoreCase("MENU")) {
            openMenuDialogBox(view, position);
        } else if (clickOn.equalsIgnoreCase("ITEM")) {
            makeCall(contactsList.get(position).getNumber());
        }
    }

    /*
     * This method create PopupMenu with 2 menu 'edit' and 'delete'
     * @view: on which the popup should open
     * @position: the adapter item position
     * set click listener on menu items
     */
    @SuppressLint("NonConstantResourceId")
    private void openMenuDialogBox(View view, int position) {
        PopupMenu popup = new PopupMenu(this, view);
        //inflating menu from xml resource
        popup.inflate(R.menu.options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    gotoAddContactActivity(contactsList.get(position));
                    return true;
                case R.id.delete:
                    deleteConfirmation(position);
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    /*
     * create intent to go to AddContactsActivity
     * pass data with 'putExtrs' method
     * pass data in Serializable way
     */
    private void gotoAddContactActivity(ContactsData contactsData) {
        Intent i = new Intent(this, AddContactsActivity.class);
        i.putExtra("contact_data", (Serializable) contactsData);
        startActivity(i);
    }

    /*
     * Create AlertDialog to show default android dialog
     * set title, message and positive-negative buttons of AlertDialog
     */
    private void deleteConfirmation(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.delete)); //set title of alert dialog
        alert.setMessage(R.string.are_you_sure_you_want_to_delete_it); // set message of alert dialog
        alert.setPositiveButton(R.string.yes, (dialog, which) -> {
            // continue with delete
            contactsList.remove(contactsList.get(position));
            contactsAdapter.notifyDataSetChanged();
            SharedPref.clear("contacts_list"); // clear old data from shared preference
            SharedPref.write(contactsList, "contacts_list"); // save latest data in shared preferences
            setViewVisibility(); // set visibility of views in case the last item is deleted from list
        });
        alert.setNegativeButton(R.string.no, (dialog, which) -> {
            // close dialog
            dialog.cancel();
        });
        alert.show();
    }
}