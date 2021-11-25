package com.parwinder.contacttracing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton addBT;
    private ArrayList<ContactsData> contactsList = new ArrayList();
    private RecyclerView contactsRV;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setClickListeners();
        setupRecyclerview();
        initializeAdapter();
    }

    private void initializeAdapter() {
        contactsAdapter = new ContactsAdapter(this, contactsList);
    }

    private void setupRecyclerview() {
        contactsRV.setLayoutManager(new LinearLayoutManager(this));
        contactsRV.setAdapter(contactsAdapter);
    }

    private void setClickListeners() {
        addBT.setOnClickListener(this);
    }

    private void findViews() {
        addBT = findViewById(R.id.addBT);
        contactsRV = findViewById(R.id.contactsRV);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBT:
                gotoAddContactActivity();
                break;
        }
    }

    private void gotoAddContactActivity() {
        Intent myIntent = new Intent(this, AddContactsActivity.class);
        startActivity(myIntent);
    }
}