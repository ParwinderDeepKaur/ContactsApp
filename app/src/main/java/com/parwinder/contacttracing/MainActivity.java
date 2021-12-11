package com.parwinder.contacttracing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private FloatingActionButton addBT;
    private ArrayList<ContactsData> contactsList = new ArrayList();
    private RecyclerView contactsRV;
    private TextView noDataTV;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPref.init(getApplicationContext()); // singleton class for Shared Preferences
        findViews();
        addBT.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactsList = SharedPref.read("contacts_list");
        setViewVisibility();
    }

    private void setViewVisibility() {
        if (contactsList == null || contactsList.isEmpty()) {
            noDataTV.setVisibility(View.VISIBLE);
            contactsRV.setVisibility(View.GONE);
        } else {
            noDataTV.setVisibility(View.GONE);
            contactsRV.setVisibility(View.VISIBLE);
            setupRecyclerview();
            setupAdapter();
        }
    }

    private void setupAdapter() {
        Collections.sort(contactsList, (text1, text2) -> text1.getName().compareToIgnoreCase(text2.getName()));
        contactsAdapter = new ContactsAdapter(this, contactsList);
        contactsRV.setAdapter(contactsAdapter);
        contactsAdapter.setClickListener(this);
    }

    private void setupRecyclerview() {
        contactsRV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void findViews() {
        addBT = findViewById(R.id.addBT);
        noDataTV = findViewById(R.id.noDataTV);
        contactsRV = findViewById(R.id.contactsRV);
    }

    private void gotoAddContactActivity() {
        Intent myIntent = new Intent(this, AddContactsActivity.class);
        startActivity(myIntent);
    }

    private void makeCall(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBT:
                gotoAddContactActivity();
                break;
        }
    }

    @Override
    public void onClick(View view, int position, String clickOn) {
        if (clickOn.equalsIgnoreCase("MENU")) {
            openMenuDialogBox(view, position);
        } else if (clickOn.equalsIgnoreCase("ITEM")) {
            makeCall(contactsList.get(position).getNumber());
        }
    }

    private void openMenuDialogBox(View view, int position) {
        PopupMenu popup = new PopupMenu(this, view);
        //inflating menu from xml resource
        popup.inflate(R.menu.options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete:
                    contactsList.remove(contactsList.get(position));
                    contactsAdapter.notifyDataSetChanged();
                    SharedPref.clear("contacts_list");
                    SharedPref.write(contactsList, "contacts_list");
                    setViewVisibility();
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }
}