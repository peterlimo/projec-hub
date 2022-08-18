package com.example.projecthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button b1;
    private Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);

        b1.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,Supervisor_Login.class));
            finish();
        });
        b2.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,LoginStudent.class));
            finish();
        });



    }
}