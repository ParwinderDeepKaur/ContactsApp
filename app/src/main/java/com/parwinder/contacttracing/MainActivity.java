package com.parwinder.contacttracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private Button addBT;
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
        contactsAdapter = new ContactsAdapter();
    }

    private void setupRecyclerview() {
        contactsRV.setLayoutManager(new LinearLayoutManager(this));
        contactsRV.setAdapter(contactsAdapter);
    }

    private void setClickListeners() {
        addBT.setOnClickListener(view -> startActivity(new Intent(this, AddContactsActivity.class)));
    }

    private void findViews() {
        addBT = findViewById(R.id.addBT);
        contactsRV = findViewById(R.id.contactsRV);

    }
}