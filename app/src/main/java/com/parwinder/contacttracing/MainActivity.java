package com.parwinder.contacttracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button addBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setClickListeners();
    }

    private void setClickListeners() {
        addBT.setOnClickListener(view -> startActivity(new Intent(this, AddContactsActivity.class)));
    }

    private void findViews() {
        addBT = findViewById(R.id.addBT);
    }
}